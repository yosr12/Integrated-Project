<?php

namespace App\Controller;

use Dompdf\Dompdf;
use Dompdf\Options;

use App\Entity\Transport;
use App\Form\TransportType;
use App\Repository\TransportRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class TransportsController extends AbstractController

{
    
    /**
     * @Route("/transports", name="transports")
     */
    public function transports(Request $request , PaginatorInterface $paginator,SessionInterface $session ): Response
    {
        $repository=$this->getDoctrine()->getRepository(Transport::Class);
        $Transports=$repository->findAll();
        $transports = $paginator->paginate(
            $Transports,
            $request->query->getInt('page',1),
            3
        );
        return $this->render('transports/transports.html.twig', [
            'transports' => $transports,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/admin/gestion/transports", name="admintransports")
     */
    public function admintransports(SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Transport::Class);
        $Transports=$repository->findAll();
        return $this->render('transports/admintransport.html.twig', [
            'transports' => $Transports,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/transport/add", name="newTransport")
     */

    public function newTransport(Request $request,SessionInterface $session)
    {
        $Transport=new Transport();
        $form=$this->createForm(TransportType::class,$Transport);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $Transport=$form->getData();
            $em=$this->getDoctrine()->getManager();
            $em->persist($Transport);
            $em->flush();
            return $this->redirectToRoute('admintransports');
        }
        return $this->render('transports/add.html.twig',
         ['form' => $form->createView(), "session"=>$session,]);
  
    }
    /**
     * @Route("/updateTransport/{id}", name="updateTransport")
     */
    public function updateTransport(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Transport=$em->getRepository(Transport::class)->find($id);
        $form=$this->createForm(TransportType::class,$Transport);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            
            $em->flush();
            return $this->redirectToRoute('admintransports');

        }
        
        return $this->render('transports/add.html.twig',
         ['form' => $form->createView(), "session"=>$session]);
  
    }
    /**
     * @Route("/deleteTransport/{id}", name="deleteTransport")
     */
    public function deleteTransport(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Transport=$em->getRepository(Transport::class)->find($id);
        $em->remove($Transport,$id);
        $em->flush();
        
        return $this->redirectToRoute('admintransports');

      
    }
    /**
     * @Route("/transportdetails/{id}", name="transportdetails")
     */
    public function transportdetails(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Transport=$em->getRepository(Transport::class)->find($id);
        
        
        return $this->render('transports/transportdetails.html.twig',[
            'transport' => $Transport,
            "session"=>$session,
         ]);
  
    }
    
    /**
     * @Route("/searchTransport", name="searchTransport")
     */
    function searchTransport(TransportRepository $repository,Request $request,SessionInterface $session){
        $data=$request->get('find');
        $Transports=$repository->findBy(['type'=>$data]);
        return $this->render('transports/transports.html.twig', [
            'transports' => $Transports,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/admin/searchTransport", name="adminsearchTransport")
     */
    function adminsearchTransport(TransportRepository $repository,Request $request,SessionInterface $session){
        $data=$request->get('find');
        $Transports=$repository->findBy(['type'=>$data]);
        return $this->render('transports/admintransport.html.twig', [
            'transports' => $Transports,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/pdf", name="pdf", methods={"GET"})
     */
    public function pdf(TransportRepository $TransportRepository,SessionInterface $session): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('transports/pdf.html.twig', [
            'transports' => $TransportRepository->findAll(),
            "session"=>$session,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);
    }
     /**
     * @param Request $request
     * @return Response
     * @Route ("/search",name="search")
     */
    public function search(Request $request,SessionInterface $session)
    {
        $repository = $this->getDoctrine()->getRepository(Transport::class);
        $requestString=$request->get('searchValue');
        $transport = $repository->showAllByType($requestString);
        return $this->render('transports/searchtransport.html.twig' ,[
            "transports"=>$transport,
            "session"=>$session,
        ]);
    }




       
}
