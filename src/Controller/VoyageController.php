<?php

namespace App\Controller;

use App\Entity\PriceSearch;
use App\Entity\Voyage;
use App\Entity\VoyageSearch;
use App\Form\PriceSearchType;
use App\Form\VoyageSearchType;
use App\Form\VoyageType;
use App\Repository\VoyageRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\Request; // Nous avons besoin d'accéder à la requête pour obtenir le numéro de page
use Knp\Component\Pager\PaginatorInterface; // Nous appelons le bundle KNP Paginator

/**
 * @Route("/voyage")
 */
class VoyageController extends AbstractController
{

    /**
     * @Route("/", name="voyage_index", methods={"GET"})

     */
    public function index(VoyageRepository $voyageRepository,Request $request ): Response
    {

        $priceSearch = new PriceSearch();
        $form = $this->createForm(PriceSearchType::class,$priceSearch);
        $form->handleRequest($request);
        $voyages= [];


        if($form->isSubmitted() && $form->isValid()) {

            $PrixMin = $priceSearch->getPrixMin();
            $PrixMax = $priceSearch->getPrixMax();

            $voyages= $this->getDoctrine()->getRepository(Voyage::class)->findByPriceRange($PrixMin,$PrixMax);
        }

        else {

            $voyages= $this->getDoctrine()->getRepository(Voyage::class)->findAll();
        }
        return $this->render('voyage/index.html.twig',[ 'form' =>$form->createView(), 'voyages' =>$voyages]);

    }


    /**
     * @Route("/affiche", name="voyages")
     */
    public function voyages(Request $request ,paginatorInterface $paginator): Response
    {

        $allvoyage=$this->getDoctrine()->getRepository(Voyage::Class)->findAll();

$voyages = $paginator->paginate(
    $allvoyage, // Requête contenant les données à paginer (ici nos articles)
    $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
    3// Nombre de résultats par page
);


        return $this->render('front/affiche.html.twig', [
            'voyages' => $voyages,
        ]);

    }



    /**
     * @Route("/vtunisie", name="vtunisie")
     */
    public function vtunisie(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Voyage::Class);
        $voyages=$repository->findAll();
        return $this->render('front/vtunisie.html.twig', [
            'voyages' => $voyages,
        ]);

    }

    /**
     * @Route("/TrierParPrixAsc", name="TrierParPrixAsc")
     */
    public function TrierParPrix(Request $request ,paginatorInterface $paginator): Response
    {$allvoyage=$this->getDoctrine()->getRepository(Voyage::Class)->findByPrixAsc();
        $voyages=$paginator->paginate(
            $allvoyage,
            $request->query->getInt('page',1),
            4);
        return $this->render('front/affiche.html.twig', [
            'voyages' => $voyages,
        ]);
    }
    /**
     * @Route("/TrierParPrixDesc", name="TrierParPrixDesc")
     */
    public function TrierParPrixDsc(Request $request ,paginatorInterface $paginator): Response
    {
        $allvoyage=$this->getDoctrine()->getRepository(Voyage::Class)->findByPrixDesc();
        $voyages=$paginator->paginate(
            $allvoyage,
            $request->query->getInt('page',1),
            3);
        return $this->render('front/affiche.html.twig', [
            'voyages' => $voyages,
        ]);
    }


    /**
     * @Route("/detail/{id}", name="detail", methods={"GET"})
     */
    public function detail(Voyage $voyage): Response
    {

        return $this->render('front/detail.html.twig', [
            'voyage' => $voyage,
        ]);
    }







    /**
     * @Route("/promotion.html", name="promotion")
     */
    public function promotion(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Voyage::Class);
        $voyages=$repository->findAll();
        return $this->render('front/promotion.html.twig', [
            'voyages' => $voyages,
        ]);
    }


    /**
     * @Route("/new", name="voyage_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $voyage = new Voyage();
        $form = $this->createForm(VoyageType::class, $voyage);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file=$voyage->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $entityManager = $this->getDoctrine()->getManager();
            $voyage->setImage($fileName);
            $entityManager->persist($voyage);
            $entityManager->flush();
            $this->addFlash(
                'info',
                'Ajouté avec succès !'

            );

            return $this->redirectToRoute('voyage_index');
        }

        return $this->render('voyage/new.html.twig', [
            'voyage' => $voyage,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="voyage_show", methods={"GET"})
     */
    public function show(Voyage $voyage): Response
    {

        return $this->render('voyage/show.html.twig', [
            'voyage' => $voyage,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="voyage_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Voyage $voyage): Response
    {
        $form = $this->createForm(VoyageType::class, $voyage);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file=$voyage->getImage();
            $fileName=md5(uniqid()).'.'.$file->getExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $voyage->setImage($fileName);
            $this->getDoctrine()->getManager()->flush();
            $this->addFlash(
                'info',
                'Modifié avec succès !'

            );

            return $this->redirectToRoute('voyage_index');
        }

        return $this->render('voyage/edit.html.twig', [
            'voyage' => $voyage,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="voyage_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Voyage $voyage): Response
    {
        if ($this->isCsrfTokenValid('delete'.$voyage->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($voyage);
            $entityManager->flush();
            $this->addFlash(
                'info',
                'Supprimé avec succès !'

            );
        }

        return $this->redirectToRoute('voyage_index');
    }





    }
