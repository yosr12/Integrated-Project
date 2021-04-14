<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Reaction;
use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class ReactionController extends AbstractController
{
    /**
     * @Route("/reaction", name="reaction")
     */
    public function index(): Response
    {
        return $this->render('reaction/index.html.twig', [
            'controller_name' => 'ReactionController',
        ]);
    }

    /**
     * @Route("/reaction/{id}/{idcommentaire}{reactionb}", name="addreaction")
     */
    public function ajuterreac($idcommentaire,$reactionb,$id,SessionInterface $session)
    {
        $reaction=new Reaction();
        $em=$this->getDoctrine()->getManager() ;
        $commentaire=$em->getRepository( Commentaire::class)->find($idcommentaire);
        $reaction->setCommentaire($commentaire);
        $reaction->setReaction($reactionb);
        $user = $em->getRepository(User::class)->find($session->get('user')->getID() ) ;

        $reaction->setUser($user);
        $em->persist($reaction);
        $em->flush();

        return $this->redirectToRoute('sujet_show', array('id' => $id,'iduser'=>1));
    }

    /**
     * @Route("/updatereaction/{id}/{idreaction}{reactionb}", name="updatereaction")
     */
    public function updatereac($id,$idreaction,$reactionb,SessionInterface $session)
    {
        $reaction=new Reaction();
        $em=$this->getDoctrine()->getManager() ;
        $reaction=$em->getRepository( Reaction::class)->find($idreaction);
        $reaction->setReaction($reactionb);
        $em->flush();

        return $this->redirectToRoute('sujet_show', array('id' => $id,'iduser'=>1));
    }
}
