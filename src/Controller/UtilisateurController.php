<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Form\LoginType;
use App\Repository\UtilisateurRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class UtilisateurController extends AbstractController
{
    /*
    /**
     * @Route("/utilisateur", name="utilisateur")
     */
 /*   public function index(): Response
    {
        return $this->render('utilisateur/index.html.twig', [
            'controller_name' => 'UtilisateurController',
        ]);
    }*/

    /**
     * @Route("/login", name="login")PH
     */
    public function login(UtilisateurRepository $userRepository , SessionInterface $session,Request $request): Response
    {

        $userlogin = new Utilisateur();
        $connexionform=$this->createForm(LoginType::class, $userlogin);

        $connexionform->add('Login in!',SubmitType::class);
        $connexionform->handleRequest($request);

        if ($connexionform->isSubmitted() && $connexionform->isValid()){
            $em=$this->getDoctrine()->getManager();
            $user = $em->getRepository(Utilisateur::class)->findOneBy(array('mail'=>$userlogin->getMail(),'password'=>$userlogin->getPassword()));
            //$user=$userRepository->findOneBy(array('email'=>$userlogin->getEmail(),'password'=>$userlogin->getPassword()));
            if(is_null($user)){
                return $this->redirectToRoute('login', [
                    'form' => $connexionform->createView(),
                ]);
            }
            else{
                $session->set('user',$user);
                return $this->redirectToRoute('sujet_index', [
                    'session'=>$session,
                ]);
            }
        }

        return $this->render('User/login.html.twig', [
            'form' => $connexionform->createView(),
        ]);

    }
    /**
     * @Route("/logout", name="logout")
     */
    public function logout(SessionInterface $session){
        $session->remove('user');
        return $this->redirectToRoute('sujet_index', [
            'session'=>$session,
        ]);
    }

}
