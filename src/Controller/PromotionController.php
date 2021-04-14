<?php

namespace App\Controller;

use App\Entity\Promotion;
use App\Entity\Voyage;
use App\Form\PromotionType;
use App\Repository\PromotionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

/**
 * @Route("/promotion")
 */
class PromotionController extends AbstractController
{
    /**
     * @Route("/", name="promotion_index", methods={"GET"})
     */
    public function index(PromotionRepository $promotionRepository,SessionInterface $session): Response
    {
        return $this->render('promotion/index.html.twig', [
            'promotions' => $promotionRepository->findAll(),
            "session"=>$session,

        ]);
    }

    /**
     * @Route("/new{idvoyage}", name="promotion_new", methods={"GET","POST"})
     */
    public function new(Request $request , $idvoyage,SessionInterface $session): Response
    {
        $promotion = new Promotion();
        //$voyage = new Voyage();
        $voyage=$this->getDoctrine()->getManager()->getRepository(Voyage::class)->find($idvoyage);
        $form = $this->createForm(PromotionType::class, $promotion);
        $form->handleRequest($request);
        $promotion->setVoyage($voyage);

        if ($form->isSubmitted() && $form->isValid()) {
           $prix= $promotion->getVoyage()->getPrix()-(($promotion->getVoyage()->getPrix()*$promotion->getPourcentage())/100);
            $promotion->setPrix($prix);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($promotion);
            $entityManager->flush();

            return $this->redirectToRoute('promotion_index');
        }

        return $this->render('promotion/new.html.twig', [
            'promotion' => $promotion,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }


    /**
     * @Route("/{id}", name="promotion_show", methods={"GET"})
     */
    public function show(Promotion $promotion,SessionInterface $session): Response
    {
        return $this->render('promotion/show.html.twig', [
            'promotion' => $promotion,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="promotion_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Promotion $promotion,SessionInterface $session): Response
    {
        $form = $this->createForm(PromotionType::class, $promotion);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('promotion_index');
        }

        return $this->render('promotion/edit.html.twig', [
            'promotion' => $promotion,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="promotion_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Promotion $promotion,SessionInterface $session): Response
    {
        if ($this->isCsrfTokenValid('delete'.$promotion->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($promotion);
            $entityManager->flush();
        }

        return $this->redirectToRoute('promotion_index');
    }

    /**
     * @Route("/pdf/fatma", name="imprimer", methods={"GET"})
     */
    public function pdf(PromotionRepository $promotionRepository,SessionInterface $session): Response
    {

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        $dompdf = new Dompdf($pdfOptions);

        $html = $this->renderView('promotion/pdf.html.twig', [
            'promotions' => $promotionRepository->findAll(),
        ]);

        $dompdf->loadHtml($html);


        $dompdf->setPaper('A4', 'portrait');

        $dompdf->render();


        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);
    }

}
