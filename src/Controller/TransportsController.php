<?php

namespace App\Controller;

use App\Entity\Transport;
use App\Form\TransportType;
use App\Repository\TransportRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class TransportsController extends AbstractController

{
    
    /**
     * @Route("/transports", name="transports")
     */
    public function transports(Request $request , PaginatorInterface $paginator ): Response
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
        ]);
    }
    /**
     * @Route("/admin/transports", name="admintransports")
     */
    public function admintransports(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Transport::Class);
        $Transports=$repository->findAll();
        return $this->render('transports/admintransport.html.twig', [
            'transports' => $Transports,
        ]);
    }
    /**
     * @Route("/transport/add", name="newTransport")
     */

    public function newTransport(Request $request)
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
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/updateTransport/{id}", name="updateTransport")
     */
    public function updateTransport(Request $request,$id)
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
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/deleteTransport/{id}", name="deleteTransport")
     */
    public function deleteTransport(Request $request,$id)
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
    public function transportdetails(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Transport=$em->getRepository(Transport::class)->find($id);
        
        
        return $this->render('transports/transportdetails.html.twig',[
            'transport' => $Transport,
         ]);
  
    }
    
    /**
     * @Route("/searchTransport", name="searchTransport")
     */
    function searchTransport(TransportRepository $repository,Request $request){
        $data=$request->get('find');
        $Transports=$repository->findBy(['type'=>$data]);
        return $this->render('transports/transports.html.twig', [
            'transports' => $Transports,
        ]);
    }
    /**
     * @Route("/admin/searchTransport", name="adminsearchTransport")
     */
    function adminsearchTransport(TransportRepository $repository,Request $request){
        $data=$request->get('find');
        $Transports=$repository->findBy(['type'=>$data]);
        return $this->render('transports/admintransport.html.twig', [
            'transports' => $Transports,
        ]);
    }

       
}
