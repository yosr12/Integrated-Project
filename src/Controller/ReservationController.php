<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Entity\User;
use App\Entity\Voyage;
use App\Entity\Hotel;
use App\Form\ReservationType;
use App\Repository\ReservationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

/**
 * @Route("/reservation")
 */
class ReservationController extends AbstractController
{
    /**
     * @Route("/", name="reservation_index", methods={"GET"})
     */
    public function index(ReservationRepository $reservationRepository,SessionInterface $session): Response
    {
        return $this->render('reservation/index.html.twig', [
            'reservations' => $reservationRepository->findAll(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/new/{idvoyage}", name="reservation_new", methods={"GET","POST"})
     */
    public function new(Request $request , $idvoyage,\Swift_Mailer $mailer,SessionInterface $session): Response
    {
        $reservation = new Reservation();

        $em=$this->getDoctrine()->getManager();
        $voyage=$em->getRepository(Voyage::class)->find($idvoyage);
        $reservation->setVoyage($voyage);
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);
        $user = $em->getRepository(User::class)->find($session->get('user')->getID() ) ;

        $reservation->setUser($user);
        if ($form->isSubmitted() && $form->isValid()) {
            $reservation->setPrix($voyage->getPrix());
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reservation);
            $entityManager->flush();
            $message = (new \Swift_Message('Reservation'))
                ->setFrom('taabaniesprit@gmail.com')
                ->setTo('yosr.aroui@esprit.tn')
                ->setBody("votre reservation a ete confirme");
            $mailer->send($message) ;

            return $this->redirectToRoute('reservation_index');
        }

        return $this->render('reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/newreservation/hotel/{idhotel}", name="reservation_new_hotel", methods={"GET","POST"})
     */
    public function newhotel(Request $request , $idhotel,\Swift_Mailer $mailer,SessionInterface $session): Response
    {
        $reservation = new Reservation();

        $em=$this->getDoctrine()->getManager();
        $hotel=$em->getRepository(Hotel::class)->find($idhotel);
        $reservation->setHotel($hotel);
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        $user = $em->getRepository(User::class)->find($session->get('user')->getID());
        $reservation->setUser($user);

        if ($form->isSubmitted() && $form->isValid()) {
            $reservation->setPrix($hotel->getPrice());
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reservation);
            $entityManager->flush();
            $message = (new \Swift_Message('Reservation'))
                ->setFrom('taabaniesprit@gmail.com')
                ->setTo('yosr.aroui@esprit.tn')
                ->setBody("votre reservation a ete confirme");
            $mailer->send($message) ;

            return $this->redirectToRoute('reservation_index');
        }

        return $this->render('reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form->createView(),
            "session"=>$session,

        ]);
    }

    /**
     * @Route("/{id}", name="reservation_show", methods={"GET"})
     */
    public function show(Reservation $reservation,SessionInterface $session): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="reservation_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Reservation $reservation,SessionInterface $session): Response
    {
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('reservation_index');
        }

        return $this->render('reservation/edit.html.twig', [
            'reservation' => $reservation,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="reservation_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Reservation $reservation,SessionInterface $session): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservation->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reservation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('reservation_index');
    }
    /**
     * @Route("/afficherback", name="reservation_show", methods={"GET"})
     */
    public function ShowRR(Request $request, PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $reservations = $this->getDoctrine()->getRepository(Reservation::class)->orderbyprix() ;
        $reservation = $paginator->paginate(
            $reservations,

            $request->query->getInt('page', 1),
            3
            // Items per page

        );
        return $this->render('reservation/reservationback.html.twig', [
            'reservations' => $reservation,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/afficherbackk", name="reservation_showw", methods={"GET"})
     */
    public function ShowRRr(Request $request, PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $reservations = $this->getDoctrine()->getRepository(Reservation::class)->orderbyprixx() ;
        $reservation = $paginator->paginate(
            $reservations,

            $request->query->getInt('page', 1),
            3
        // Items per page

        );
        return $this->render('reservation/reservationback.html.twig', [
            'reservations' => $reservation,
            "session"=>$session,
        ]);
    }
}