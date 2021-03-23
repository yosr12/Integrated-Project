<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Utilisateur;
use App\Entity\Sujet;
use App\Form\CommentaireType;
use App\Form\SujetType;
use App\Repository\SujetRepository;
use App\Repository\CommentaireRepository;
use mysql_xdevapi\Session;
use phpDocumentor\Reflection\Types\Integer;
use PhpParser\Node\Scalar\String_;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Knp\Component\Pager\PaginatorInterface;

Use App\filter_profanity;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

/**
 * @Route("/sujet")
 */
class SujetController extends AbstractController
{
    /**
     * @Route("/test", name="sujet_index", methods={"GET"})
     */
    public function index(SujetRepository $sujetRepository,SessionInterface $session,PaginatorInterface $paginator,Request $request): Response
    {
        $sujet=$sujetRepository->findAll();
        $sujet=$paginator->paginate(
            $sujet,
            $request->query->getInt('page',1) ,
            3
        ) ;
        return $this->render('sujet/index.html.twig', [
            'sujets' => $sujet,
            'session'=>$session,
        ]);
    }

    /**
     * @Route("/new", name="sujet_new", methods={"GET","POST"})
     */
    public function new(Request $request,SessionInterface $session): Response
    {
        $filter= new filter_profanity();
        $sujet = new Sujet();
        $form = $this->createForm(SujetType::class, $sujet);
        $form->handleRequest($request);
        $sujet->setSujet($filter->filter_string($sujet->getSujet(),"*"));
        $sujet->setDescription($filter->filter_string($sujet->getDescription(),"*"));

        $em=$this->getDoctrine()->getManager() ;


        $utilisateur = $em->getRepository(Utilisateur::class)->find($session->get('user')->getID() ) ;
        $sujet->setUtilisateur($utilisateur);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $sujet->getImage();
            $filename = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'),$filename);
            $sujet->setImage($filename);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($sujet);
            $entityManager->flush();

            return $this->redirectToRoute('sujet_index');
        }

        return $this->render('sujet/new.html.twig', [
            'sujet' => $sujet,
            'form' => $form->createView(),
            'session'=>$session,
        ]);
    }

    /**
     * @Route("/{id}/{iduser}", name="sujet_show", methods={"GET","POST"}, requirements={"id":"\d+","iduser":"\d+"})
     * @ParamConverter("id", options={"id"= "id"})
     * @ParamConverter("iduser", options={"iduser"= "iduser"})
     */
    public function show(Sujet $sujet, Request $request, $id,$iduser,SessionInterface $session): Response
    {

        $filter= new filter_profanity();

        $commentaire = new Commentaire();


        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        $em=$this->getDoctrine()->getManager();
        $utilisateur = $em->getRepository( Utilisateur::class)->find($iduser) ;
        $sujet=$em->getRepository( Sujet::class)->find($id) ;

        $commentaire->setSujet($sujet);

        if($session->has('user') == 1){
            $utilisateur2 = $em->getRepository(Utilisateur::class)->find($session->get('user')->getID() ) ;
            $session->set('user',$utilisateur2);
        }


        if ($filter->verif2($commentaire->getCommentaire(),"*")){
            $commentaire->setFiltredcomment($filter->filter_string($commentaire->getCommentaire(),"*"));
        }


        if ($form->isSubmitted() && $form->isValid()) {
            $commentaire->setUtilisateur($utilisateur2);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($commentaire);
            $entityManager->flush();

            return $this->redirectToRoute('sujet_show', array('id' => $id,'iduser'=>1));
        }

        return $this->render('sujet/show.html.twig', [
            'sujet' => $sujet,
            'commentaire' => $commentaire,
            'form' => $form->createView(),
            'session'=>$session,

        ]);
    }

    /**
     * @Route("/{id}/edit/test", name="sujet_edit", methods={"GET","POST"}, )
     * @ParamConverter("id", options={"id"= "id"})
     */
    public function edit(Request $request, Sujet $sujet): Response
    {
        $filter= new filter_profanity();
        $sujet->setImage(null);
        $form = $this->createForm(SujetType::class, $sujet);
        $form->handleRequest($request);

        $sujet->setSujet($filter->filter_string($sujet->getSujet(),"*"));
        $sujet->setDescription($filter->filter_string($sujet->getDescription(),"*"));

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $sujet->getImage();
            $filename = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'),$filename);
            $sujet->setImage($filename);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('sujet_index');
        }

        return $this->render('sujet/edit.html.twig', [
            'sujet' => $sujet,
            'form' => $form->createView(),
        ]);
    }

 /*   /**
     * @Route("/{id}/delete", name="sujet_delete", methods={"DELETE"})
     */
   /* public function delete(Request $request, Sujet $sujet): Response
    {
        if ($this->isCsrfTokenValid('delete'.$sujet->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($sujet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('sujet_index');
    }*/

    /**
     * @Route("/{id}/delete", name="sujet_delete", methods={"DELETE","GET"})
     */
    public function delete($id): Response
    {
        $em=$this->getDoctrine()->getManager() ;
        $sujet = $em->getRepository(Sujet::class)->find($id) ;
       // $reaction=$commentaire->getReactions();
       // $em->remove($reaction) ;
        $em->remove($sujet) ;
        $em->flush();

        return $this->redirectToRoute('sujet_index');
    }
    /**
     * @Route("/{id}/delete/aa/yy/aa/aa", name="sujet_delete2", methods={"DELETE","GET"})
     */
    public function delete2($id): Response
    {
        $em=$this->getDoctrine()->getManager() ;
        $sujet = $em->getRepository(Sujet::class)->find($id) ;
        // $reaction=$commentaire->getReactions();
        // $em->remove($reaction) ;
        $em->remove($sujet) ;
        $em->flush();

        return $this->redirectToRoute('sujet_index2');
    }


    /**
     * @Route("/back/test", name="sujet_index2", methods={"GET"})
     */
    public function index2(SujetRepository $sujetRepository): Response
    {
        return $this->render('sujet/backindex.html.twig', [
            'sujets' => $sujetRepository->findAll(),
        ]);
    }
    /**
     * @Route("/back/{id}/{idadmin}", name="sujet_show2", methods={"GET","POST"}, requirements={"id":"\d+","iduser":"\d+"})
     * @ParamConverter("id", options={"id"= "id"})
     * @ParamConverter("iduser", options={"idadmin"= "idadmin"})
     */
    public function show2(Sujet $sujet, Request $request, $id,$idadmin,PaginatorInterface $paginator): Response
    {

        $filter= new filter_profanity();

        $commentaire = new Commentaire();


        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        $em=$this->getDoctrine()->getManager();
        $utilisateur = $em->getRepository( Utilisateur::class)->find(100) ;
        $sujet=$em->getRepository( Sujet::class)->find($id) ;

        $commentaire->setSujet($sujet);
        $commentaire->setUtilisateur($utilisateur);

        $commentaire->setCommentaire($filter->filter_string($commentaire->getCommentaire(),"*"));

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($commentaire);
            $entityManager->flush();

            return $this->redirectToRoute('sujet_show2', array('id' => $id,'idadmin'=>1));
        }


        return $this->render('sujet/backshow.html.twig', [
            'sujet' => $sujet,
            'commentaire' => $commentaire,
            'form' => $form->createView(),

        ]);


    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("/test/tset/setset/setset",name="searchrdv",methods={"GET","POST"})
     */
    public function searchrdv(Request $request,SessionInterface $session)
    {
        $repository = $this->getDoctrine()->getRepository(Sujet::class);
        $requestString=$request->get('searchValue');
        $rdv = $repository->findrdvBytags($requestString);
        return $this->render('sujet/sujetajax.html.twig' ,[
            "sujets"=>$rdv,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/test/payment/error", name="error", methods={"GET"})
     */
    public function error(SujetRepository $sujetRepository,SessionInterface $session,PaginatorInterface $paginator,Request $request): Response
    {

        return $this->render('sujet/error.html.twig', [

        ]);
    }
    /**
     * @Route("/test/payment/success", name="success", methods={"GET"})
     */
    public function success(): Response
    {

        return $this->render('sujet/success.html.twig', [

        ]);
    }

    /**
     * @Route("/test/payment", name="payment", methods={"GET","POST"})
     */
    public function payment(SujetRepository $sujetRepository,SessionInterface $session,PaginatorInterface $paginator,Request $request): Response
    {

        \Stripe\Stripe::setApiKey('sk_test_51IUVCeDOtKIVPgbZHfUUWuAj4QGDWhVHABQBdjk2BvteUm2rJqnzFMEHJfsemgLp1NygIkIHN8gMPELlYthE3eWC00H35Rjw12');
        header('Content-Type: application/json');
        $YOUR_DOMAIN = 'http://localhost:8000';
        $checkout_session = \Stripe\Checkout\Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [[
                'price_data' => [
                    'currency' => 'usd',
                    'unit_amount' => 2000,
                    'product_data' => [
                        'name' => 'Voyage Tabaani',
                        'images' => ["https://www.autourdesvoyages.com/wp-content/uploads/agence-voyage.jpg"],
                    ],
                ],
                'quantity' => 1,
            ]],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('success',[],UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('error',[],UrlGeneratorInterface::ABSOLUTE_URL),
        ]);
        return new JsonResponse(['id' => $checkout_session->id]);
    }

}
