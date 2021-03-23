<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Utilisateur;
use App\Form\CommentaireType;
use App\Repository\CommentaireRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\JsonResponse;


/**
 * @Route("/sujet/commentaire")
 */
class CommentaireController extends AbstractController
{
    /**
     * @Route("/", name="commentaire_index", methods={"GET"})
     */
    public function index(CommentaireRepository $commentaireRepository): Response
    {
        return $this->render('commentaire/index.html.twig', [
            'commentaires' => $commentaireRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="commentaire_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $commentaire = new Commentaire();
        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);
        $idsujet = $commentaire->getSujet()->getId();

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($commentaire);
            $entityManager->flush();

            return $this->redirectToRoute('sujet_show', array('id' => $idsujet, 'iduser' => 1));
        }

        return $this->render('commentaire/new.html.twig', [
            'commentaire' => $commentaire,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/{id}", name="commentaire_show", methods={"GET"})
     */
    public function show(Commentaire $commentaire): Response
    {
        return $this->render('commentaire/show.html.twig', [
            'commentaire' => $commentaire,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="commentaire_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Commentaire $commentaire): Response
    {
        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('commentaire_index');
        }

        return $this->render('commentaire/edit.html.twig', [
            'commentaire' => $commentaire,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}/{idsujet}", name="commentaire_delete", methods={"DELETE","POST","GET"})
     */
    public function delete($idsujet, $id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $commentaire = $em->getRepository(Commentaire::class)->find($id);

        $em->remove($commentaire);
        $em->flush();

        return $this->redirectToRoute('sujet_show', array('id' => $idsujet, 'iduser' => 1));
    }

    /**
     * @Route("/{id}/{idsujet}/back", name="commentaire_deleteback", methods={"DELETE","POST","GET"})
     */
    public function deleteback($idsujet, $id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $commentaire = $em->getRepository(Commentaire::class)->find($id);

        $em->remove($commentaire);
        $em->flush();

        return $this->redirectToRoute('sujet_show2', array('id' => $idsujet, 'idadmin' => 1));
    }


    /**
     * @Route("/banuser/{userid}/{banlong}/{idsujet}", name="banuser", methods={"POST","GET"})
     */
    public function banuser($banlong, SessionInterface $session, $idsujet, $userid,\Swift_Mailer $mailer)
    {
        $bantime = date('Y-m-d H:i:s', (time() + $banlong));
        $em = $this->getDoctrine()->getManager();
        $utilisateur = $em->getRepository(Utilisateur::class)->find($userid);
        $utilisateur->setBan(\DateTime::createFromFormat('Y-m-d H:i:s', $bantime));
        $timetest=$banlong/60;
        $message = (new \Swift_Message('TABAANI SUPPORT'))
            ->setFrom('taabaniesprit@gmail.com')
            ->setContentType("text/html")
            ->setTo('ahmed.louhaichi@esprit.tn')
            ->setBody("<strong style='color: red;'> you have been banned for offensive chat  </strong> <span style='color:red;'>for ".$timetest." min</span> ");
        $mailer->send($message) ;

        $this->getDoctrine()->getManager()->flush();
        return $this->redirectToRoute('sujet_show2', array('id' => $idsujet, 'idadmin' => 1));
    }

    /**
     * @Route("/unbanuser/{userid}/{banlong}/{idsujet}", name="unbanuser", methods={"POST","GET"})
     */
    public function unbanuser($banlong, SessionInterface $session, $idsujet, $userid)
    {
        $bantime = date('Y-m-d H:i:s', (time() - 1));
        $em = $this->getDoctrine()->getManager();
        $utilisateur = $em->getRepository(Utilisateur::class)->find($userid);
        $utilisateur->setBan(\DateTime::createFromFormat('Y-m-d H:i:s', $bantime));
        $this->getDoctrine()->getManager()->flush();
        return $this->redirectToRoute('sujet_show2', array('id' => $idsujet, 'idadmin' => 1));
    }

    /**
     * @Route("/a/ajax")
     */
    public function recherche(Request $request)
    {
        $a[] = "Anna";
        $a[] = "Brittany";
        $a[] = "Cinderella";
        $a[] = "Diana";
        $a[] = "Eva";
        $a[] = "Fiona";

        if ($request->isXmlHttpRequest() || $request->query->get('showJson') == 1) {
            $jsonData = array();
            $idx = 0;

                $jsonData[0] = "test";
            $jsonData[1] = "test2";

            return new JsonResponse($jsonData);
        }else {
            return $this->render('sujet/index.html.twig');
        }


    }
}
