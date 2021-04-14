<?php

namespace App\Controller;
use phpDocumentor\Reflection\Type;
use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Entity;
use Knp\Component\Pager\PaginatorInterface;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\RechercheData;

use App\Form\RechercherDataType;
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
     * @Route("/", name="produit_index", methods={"GET"})
     */
    public function index(ProduitRepository $produitRepository,SessionInterface $session): Response
    {
        return $this->render('produit/index.html.twig',[
            'produits' => $produitRepository->orderbyprice(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/new", name="produit_new", methods={"GET","POST"})
     */
    public function new(Request $request,SessionInterface $session): Response
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
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/produit", name="produit_show2", methods={"GET"})
     */
    public function show(Produit $produit,SessionInterface $session): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="produit_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Produit $produit,SessionInterface $session): Response
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
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="produit_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Produit $produit,SessionInterface $session): Response
    {
        if ($this->isCsrfTokenValid('delete'.$produit->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('produit_index');
    }
    /**
     * @Route("/afficherproduit", name="produit_show", methods={"GET","POST"})
     */
    public function showPP(Request $request,PaginatorInterface $paginator,SessionInterface $session): Response
    {
        //$p=$this->getDoctrine()->getRepository(Produit::class)->findAll();
        $priceSearch = new RechercheData();
        $form = $this->createForm(RechercherDataType::class,$priceSearch);
        $form->handleRequest($request);
        $Produit= [];

        if($form->isSubmitted() && $form->isValid()) {

            $Min = $priceSearch->getMin();
            $Max = $priceSearch->getMax();

            $Produit= $this->getDoctrine()->getRepository(Produit::class)->MinMax($Min,$Max);
            $Produit = $paginator->paginate(
                $Produit,
                $request->query->getInt('page',1) ,
                6
            ) ;

        }    else {
            $Produit= $this->getDoctrine()->getRepository(Produit::class)->findAll();
            $Produit = $paginator->paginate(
                $Produit,
                $request->query->getInt('page',1) ,
                6
            ) ;
        }

        return $this->render('produit/produitfront.html.twig', [
            'form' =>$form->createView(),
            'produits' => $Produit,
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
        $priceSearch = new RechercheData();
        $form = $this->createForm(RechercherDataType::class,$priceSearch);
        $produit = $paginator->paginate(
            $produit,
            $request->query->getInt('page',1) ,
            6
        ) ;
        return $this->render('produit/produitfront.html.twig', [
            'form' =>$form->createView(),
            'produits' => $produit,
            'session' => $session,
        ]);
    }


    /**
     * @param Request $request
     * @return Response
     * @Route ("/produitajax",name="searchrdvproduit")
     */
    public function searchrdv(Request $request,SessionInterface $session)
    {
        $repository = $this->getDoctrine()->getRepository(Produit::class);
        $requestString=$request->get('searchValue');
        $rdv = $repository->findrdvBydate($requestString);
        return $this->render('produit/produitajax.html.twig' ,[
            "produits"=>$rdv, "session"=>$session,
        ]);
    }


    /**
     * @param Request $request
     * @return Response
     * @Route ("/produitajaxxx",name="searchrdvzzproduit")
     */
    public function searchrdvvv(Request $request,SessionInterface $session)
    {
        $repository = $this->getDoctrine()->getRepository(Produit::class);
        $requestString=$request->get('searchValue');
        $rdv = $repository->findrdvByname($requestString);
        return $this->render('produit/produitajaxfront.html.twig' ,[
            "produits"=>$rdv,
            "session"=>$session,
        ]);
    }

    /**
     * @param EntityManagerInterface $manager
     * @throws \Doctrine\ORM\NoResultException
     * @throws \Doctrine\ORM\NonUniqueResultException
     * @Route("/best",name="best")
     */
    public function back(EntityManagerInterface $manager,SessionInterface $session)
    {
        //   $user = $manager->createQuery('SELECT COUNT(u) FROM App\Entity\User u')->getSingleScalarResult();
        $produit= $manager->createQuery('SELECT COUNT(c) FROM App\Entity\Produit c')->getSingleScalarResult();
        $Commande = $manager->createQuery('SELECT COUNT(q) FROM App\Entity\Commande q')->getSingleScalarResult();
        $bestProduit = $manager->createQuery(
            'SELECT count(c) as Quantite ,a.Nom , a.Image
         FROM App\Entity\Commande c
         Join c.Produit a 
         GROUP BY a
         ORDER BY Quantite DESC'

        )
            ->setMaxResults(3)
            ->getResult();

        $worstProduit = $manager->createQuery(
            'SELECT count(c) as Quantite ,a.Nom , a.Image
         FROM App\Entity\Commande c
         Join c.Produit a 
         GROUP BY a
         ORDER BY Quantite ASC'

        )
            ->setMaxResults(3)
            ->getResult();
        return $this->render("produit/produitstat.html.twig",
            array(
                //    'user'=>$user,
                'produit'=>$produit,
                'commande'=>$Commande,
                'topproduit'=>$bestProduit,
                'worstproduit'=>$worstProduit,
                "session"=>$session,
            ));
    }

}
