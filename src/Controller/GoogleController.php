<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use League\OAuth2\Client\Provider\Exception\IdentityProviderException;
use Symfony\Component\HttpFoundation\Request;

class GoogleController extends AbstractController
{
    /**
     * @Route("/google", name="google")
     */
    public function index(): Response
    {
        return $this->render('google/index.html.twig', [
            'controller_name' => 'GoogleController',
        ]);
    }

     /**
     * Link to this controller to start the "connect" process
     * @param ClientRegistry $clientRegistry
     *
     * @Route("/connect/google", name="connect_google_start")
     *
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     */
    public function connectAction(ClientRegistry $clientRegistry)
    {
        return $clientRegistry
            ->getClient('google')
            ->redirect([
                'profile', 'email' // the scopes you want to access
            ])
        ;
    }
     /**
     * After going to Google, you're redirected back here
     * because this is the "redirect_route" you configured
     * in config/packages/knpu_oauth2_client.yaml
     *
     * @param Request $request
     * @param ClientRegistry $clientRegistry
     *
     * @Route("/connect/google/check", name="connect_google_check")
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     */
    public function connectCheckAction(Request $request, ClientRegistry $clientRegistry)
    {
        return $this->redirectToRoute('users');
    }
}
