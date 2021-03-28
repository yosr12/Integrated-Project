<?php

namespace App\Controller;
use phpDocumentor\Reflection\Type;
use App\Entity\Produit;
use App\Form\ProduitType;
use App\Entity\RechercheData;

use App\Form\RechercherDataType;

use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Entity;
use Knp\Component\Pager\PaginatorInterface;

use Symfony\Component\HttpFoundation\File\UploadedFile;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\Form\Extension\HttpFoundation\HttpFoundationRequestHandler;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\HttpFoundation\Session\SessionInterface;

/**
 * @Route("/produit")
 */
class ProduitController extends AbstractController
{
    /**
     * @Route("/", name="produit_index", methods={"GET","POST"})
     */
    public function index(ProduitRepository $produitRepository,Request  $request): Response
    {
        $priceSearch = new RechercheData();
        $form = $this->createForm(RechercherDataType::class,$priceSearch);
        $form->handleRequest($request);
        $Produits= [];


        if($form->isSubmitted() && $form->isValid()) {

            $Min = $priceSearch->getMin();
            $Max = $priceSearch->getMax();

            $Produits= $this->getDoctrine()->getRepository(Produit::class)->MinMax($Min,$Max);
        }

        else {
            $Produits= $this->getDoctrine()->getRepository(Produit::class)->findAll();
        }

        return $this->render('produit/index.html.twig',[
            'form' =>$form->createView(),
            'produits' => $Produits,
        ]);
    }

    /**
     * @Route("/new", name="produit_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $produit->getImage();
            $filename = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'),$filename);
            $produit->setImage($filename);

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($produit);
            $entityManager->flush();

            return $this->redirectToRoute('produit_index');
        }

        return $this->render('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}/produit", name="produit_show2", methods={"GET"})
     */
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="produit_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Produit $produit): Response
    {
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);
       // $produit->setImage(null);

        if ($form->isSubmitted() && $form->isValid()) {

            $file = $produit->getImage();
            $filename = md5(uniqid()).'.'.$file->getExtension();
            $file->move($this->getParameter('upload_directory'),$filename);
            $produit->setImage($filename);

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('produit_index');
        }

        return $this->render('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="produit_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Produit $produit): Response
    {
        if ($this->isCsrfTokenValid('delete'.$produit->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('produit_index');
    }

    /**
     * @Route("/afficherproduit", name="produit_show", methods={"GET"})
     */
    public function showPP(Request $request,PaginatorInterface $paginator,SessionInterface $session): Response
    {
        //$p=$this->getDoctrine()->getRepository(Produit::class)->findAll();
        $produit = $this->getDoctrine()->getRepository(Produit::class)->orderbyprice() ;
        $produit = $paginator->paginate(
            $produit,
            $request->query->getInt('page',1) ,
            6
        ) ;
        return $this->render('produit/produitfront.html.twig', [
            'produits' => $produit,
            'session' => $session,
        ]);
    }

    /**
     * @Route("/afficherproduitdown/p", name="produit_showww", methods={"GET"})
     */
    public function showPPP(Request $request,PaginatorInterface $paginator,SessionInterface $session): Response
    {
        //$p=$this->getDoctrine()->getRepository(Produit::class)->findAll();
        $produit = $this->getDoctrine()->getRepository(Produit::class)->orderbypricedown() ;
        $produit = $paginator->paginate(
            $produit,
            $request->query->getInt('page',1) ,
            6
        ) ;
        return $this->render('produit/produitfront.html.twig', [
            'produits' => $produit,
            'session' => $session,
        ]);
    }


    /**
     * @param Request $request
     * @return Response
     * @Route ("/produitajax",name="searchrdv")
     */
    public function searchrdv(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository(Produit::class);
        $requestString=$request->get('searchValue');
        $rdv = $repository->findrdvBydate($requestString);
        return $this->render('produit/produitajax.html.twig' ,[
            "produits"=>$rdv
        ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("/produitajaxxx",name="searchrdvzz")
     */
    public function searchrdvvv(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository(Produit::class);
        $requestString=$request->get('searchValue');
        $rdv = $repository->findrdvByname($requestString);
        return $this->render('produit/produitajaxfront.html.twig' ,[
            "produits"=>$rdv,
        ]);
    }

}
