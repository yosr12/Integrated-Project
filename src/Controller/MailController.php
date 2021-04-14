<?php

namespace App\Controller;

use App\Entity\Mail;
use App\Entity\Reservation;
use App\Form\MailType;
use App\Repository\MailRepository;
use Knp\Component\Pager\PaginatorInterface;
use Swift_Attachment;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

/**
 * @Route("/mail")
 */
class MailController extends AbstractController
{
    /**
     * @Route("/", name="mail_index", methods={"GET"})
     */
    public function index(Request $request, PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $mails = $this->getDoctrine()->getRepository(Mail::class)->findAll();
        $mail = $paginator->paginate(
            $mails,

            $request->query->getInt('page', 1),
            5
        // Items per page

        );
        return $this->render('mail/index.html.twig', [
            'mails' => $mail,
            "session"=>$session,
        ]);


    }

    /**
     * @Route("/new", name="mail_new", methods={"GET","POST"})
     */
    public function new(Request $request, \Swift_Mailer $mailer,SessionInterface $session): Response
    {
        $mail = new Mail();
        $form = $this->createForm(MailType::class, $mail);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $mail->getImage();
            $filename = md5(uniqid()) . '.' . $file->guessExtension();
            $mail->setImage($filename);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($mail);
            $entityManager->flush();
            $x = $mail->getSubject();
            $y = $mail->getDestinataire();
            $z = $mail->getBody();
            $a = $mail->getImage();
            $message = (new \Swift_Message($x))
                ->setFrom('taabaniesprit@gmail.com')
                ->setTo($y)
                ->setBody($z)
                ->attach(Swift_Attachment::fromPath('C:\Users\BJI\Downloads\testpdf.pdf'));
            $mailer->send($message);

            return $this->redirectToRoute('mail_index');
        }

        return $this->render('mail/new.html.twig', [
            'mail' => $mail,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="mail_show", methods={"GET"})
     */
    public function show(Mail $mail,SessionInterface $session): Response
    {
        return $this->render('mail/show.html.twig', [
            'mail' => $mail,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="mail_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Mail $mail,SessionInterface $session): Response
    {
        $form = $this->createForm(MailType::class, $mail);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('mail_index');
        }

        return $this->render('mail/edit.html.twig', [
            'mail' => $mail,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="mail_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Mail $mail,SessionInterface $session): Response
    {
        if ($this->isCsrfTokenValid('delete' . $mail->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($mail);
            $entityManager->flush();
        }

        return $this->redirectToRoute('mail_index');
    }
}

