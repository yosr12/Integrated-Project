<?php

namespace App\Controller;

use App\Entity\Hotel;
use App\Entity\Villa;
use App\Form\HotelType;
use App\Form\VillaType;
use App\Entity\Maisondhote;
use App\Form\MaisondhoteType;
use App\Repository\HotelRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Knp\Component\Pager\PaginatorInterface ; 

class HebergementController extends AbstractController
{
    /**
     * @Route("/hebergement/hotels", name="hotels")
     */
    public function hotels(Request $request , PaginatorInterface $paginator ): Response
    {
        $repository=$this->getDoctrine()->getRepository(Hotel::Class);
        $Hotelssss=$repository->findAll();
        $Hotels = $paginator->paginate(
            $Hotelssss,
            $request->query->getInt('page',1),
            3
        );
        return $this->render('hebergement/hotels.html.twig', [
            'hotels' => $Hotels,
        ]);
    }
    /**
     * @Route("/admin/hotels", name="adminhotels")
     */
    public function adminhotels(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Hotel::Class);
        $Hotels=$repository->findAll();
        return $this->render('hebergement/adminhotels.html.twig', [
            'hotels' => $Hotels,
        ]);
    }
    
    /**
     * @Route("/hebergement/newHotel", name="newHotel")
     */

    public function newHotel(Request $request)
    {
        $Hotel=new Hotel();
        $form=$this->createForm(HotelType::class,$Hotel);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $Hotel=$form->getData();
            $file=$Hotel->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $Hotel->setImage($fileName);
            $em=$this->getDoctrine()->getManager();
            $em->persist($Hotel);
            $em->flush();
            return $this->redirectToRoute('adminhotels');
        }
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/updateHotel/{id}", name="updateHotel")
     */
    public function updateHotel(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Hotel=$em->getRepository(Hotel::class)->find($id);
        $form=$this->createForm(HotelType::class,$Hotel);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            
            $em->flush();
            return $this->redirectToRoute('adminhotels');

        }
        
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/deleteHotel/{id}", name="deleteHotel")
     */
    public function deleteHotel(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Hotel=$em->getRepository(Hotel::class)->find($id);
        $em->remove($Hotel,$id);
        $em->flush();
        
        return $this->redirectToRoute('adminhotels');

      
    }
    /**
     * @Route("/admin/searchHotel ", name="search")
     */
    /*function search(HotelRepository $repository,Request $request){
        $data=$request->get('search');
        $Hotels=$repository->findBy(['nom'=>$data]);
        return $this->render('hebergement/adminhotels.html.twig', [
            'hotels' => $Hotels,
        ]);
    }*/
    public function searchHotel(Request $request,NormalizerInterface $Normalizer)
    {
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $requestString=$request->get('searchValue');
        $Hotels = $repository->findHotelbyNom($requestString);

        
      
        if($request->isXmlHttpRequest()){
            $serializer = new Serializer(array(new ObjectNormalizer()));
            $Hotels=$em->getRepository(Hotel::class)
            ->findBy(array('nom'=>$request->get('nom')));
            $jsonContent = $Normalizer->normalize($Hotels, 'json',['groups'=>'hotels']);
            $retour=json_encode($jsonContent);

            return new JsonResponse($retour);
        }
        return $this->render('hebergement/adminhotels.html.twig',[
            'hotels' => $Hotels
         ]);

    }








    /**
     * @Route("/admin/HotelsOrderASC" , name="HotelsOrderASC")
     */
    public function HotelsOrderASC(){
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $hotels = $repository->trierNomASC();
        return $this->render('hebergement/adminhotels.html.twig', [
            
            'hotels' => $hotels,
        ]);
    }
    /**
     * @Route("/admin/HotelsOrderDESC" , name="HotelsOrderDESC")
     */
    public function HotelsOrderDESC(){
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $hotels = $repository->trierNomDESC();
        return $this->render('hebergement/adminhotels.html.twig', [
            
            'hotels' => $hotels,
        ]);
    }
    






    /**
     * @Route("/hebergement/maisondhotes", name="maisondhotes")
     */
    public function maisondhotes(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Maisondhote::Class);
        $Maisons=$repository->findAll();
        return $this->render('hebergement/maisondhotes.html.twig', [
            'maisons' => $Maisons,
        ]);
    }
    /**
     * @Route("/admin/maisondhotes", name="adminmaisondhotes")
     */
    public function adminmaisondhotes(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Maisondhote::Class);
        $Maisons=$repository->findAll();
        return $this->render('hebergement/adminmaisondhotes.html.twig', [
            'maisons' => $Maisons,
        ]);
    }
     /**
     * @Route("/hebergement/newMaison", name="newMaison")
     */
    public function newMaison(Request $request)
    {
        $Maisondhote=new Maisondhote();
        $form=$this->createForm(MaisondhoteType::class,$Maisondhote);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $Maisondhote=$form->getData();
            $file=$Maisondhote->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $Maisondhote->setImage($fileName);
            $em=$this->getDoctrine()->getManager();
            $em->persist($Maisondhote);
            $em->flush();
            return $this->redirectToRoute('adminmaisondhotes');

        }
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }/**
     * @Route("/updateMaison/{id}", name="updateMaison")
     */
    public function updateMaison(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Maisondhote=$em->getRepository(Maisondhote::class)->find($id);
        $form=$this->createForm(MaisondhoteType::class,$Maisondhote);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            
            $em->flush();
            return $this->redirectToRoute('adminmaisondhotes');

        }
        
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/deleteMaison/{id}", name="deleteMaison")
     */
    public function deleteMaison(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Maisondhote=$em->getRepository(Maisondhote::class)->find($id);
        $em->remove($Maisondhote,$id);
        $em->flush();
        
        return $this->redirectToRoute('adminmaisondhotes');

      
    }








    /**
     * @Route("/hebergement/villas", name="villas")
     */
    public function villa(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Villa::Class);
        $Villas=$repository->findAll();
        return $this->render('hebergement/villa.html.twig', [
            'villas' => $Villas,
        ]);
    }
    /**
     * @Route("/admin/villas", name="adminvillas")
     */
    public function adminvilla(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Villa::Class);
        $Villas=$repository->findAll();
        return $this->render('hebergement/adminvilla.html.twig', [
            'villas' => $Villas,
        ]);
    }
     /**
     * @Route("/hebergement/newVilla", name="newVilla")
     */
    public function newVilla(Request $request)
    {
        $Villa=new Villa();
        $form=$this->createForm(VillaType::class,$Villa);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            
            $Villa=$form->getData();
            $file=$Villa->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $Villa->setImage($fileName);

            $em=$this->getDoctrine()->getManager();
            $em->persist($Villa);
            $em->flush();
            return $this->redirectToRoute('adminvillas');

        }
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/updateVilla/{id}", name="updateVilla")
     */
    public function updateVilla(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Villa=$em->getRepository(Villa::class)->find($id);
        $form=$this->createForm(VillaType::class,$Villa);
        
        $form->add('Envoyer', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            
            $em->flush();
            return $this->redirectToRoute('adminvillas');

        }
        
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView()]);
  
    }
    /**
     * @Route("/deleteVilla/{id}", name="deleteVilla")
     */
    public function deleteVilla(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Villa=$em->getRepository(Villa::class)->find($id);
        $em->remove($Villa,$id);
        $em->flush();
        
        return $this->redirectToRoute('adminvillas');

      
    }






    /**
     * @Route("/details/{id}", name="villadetails")
     */
    public function villadetails(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Villa=$em->getRepository(Villa::class)->find($id);
        
        
        return $this->render('hebergement/villadetails.html.twig',[
            'villa' => $Villa,
         ]);
  
    }
    /**
     * @Route("/Hoteldetails/{id}", name="hoteldetails")
     */
    public function hoteldetails(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Hotel=$em->getRepository(Hotel::class)->find($id);
        
        
        return $this->render('hebergement/hoteldetails.html.twig',[
            'hotel' => $Hotel,
         ]);
  
    }
    /**
     * @Route("/Maisondetails/{id}", name="maisondetails")
     */
    public function maisondetails(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Maisondhote=$em->getRepository(Maisondhote::class)->find($id);
        
        
        return $this->render('hebergement/maisondetails.html.twig',[
            'maison' => $Maisondhote,
         ]);
  
    }
    
    
}