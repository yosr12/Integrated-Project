<?php

namespace App\Controller;

use Dompdf\Dompdf;
use Dompdf\Options;
use App\Entity\Hotel;
use App\Entity\Villa;
use App\Form\HotelType;
use App\Form\VillaType;
use App\Entity\Maisondhote;
use App\Form\MaisondhoteType;
use App\Repository\HotelRepository;
use App\Repository\VillaRepository;
use App\Repository\MaisondhoteRepository;
use Knp\Component\Pager\PaginatorInterface ; 
use Symfony\Component\HttpFoundation\Request;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
class HebergementController extends AbstractController
{
    public static $y;

    /**
     * @Route("/hebergement/hotels", name="hotels")
     */
    public function hotels(Request $request ,FlashyNotifier $flashy, PaginatorInterface $paginator ,SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Hotel::Class);
        $Hotelssss=$repository->findAll();
        $Hotels = $paginator->paginate(
            $Hotelssss,
            $request->query->getInt('page',1),
            3
        );
        if (self::$y=1){
            $flashy->success('New Hotel is added!', 'lastHotel');
        }


        return $this->render('hebergement/hotels.html.twig', [
            'hotels' => $Hotels,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/hebergement/admin/hotels", name="adminhotels")
     */
    public function adminhotels(SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Hotel::Class);
        $Hotels=$repository->findAll();
        return $this->render('hebergement/adminhotels.html.twig', [
            'hotels' => $Hotels,
            "session"=>$session,
        ]);
    }
    
    /**
     * @Route("/hebergement/newHotel", name="newHotel")
     */

    public function newHotel(Request $request,FlashyNotifier $flashy,SessionInterface $session)
    {
        self::$y=0;
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
            self::$y=1;
            $flashy->success('New Hotel is created!', '/admin/hotels');
            return $this->redirectToRoute('adminhotels');
        }
        return $this->render('hebergement/ajouterOffre.html.twig',
         ['form' => $form->createView(), "session"=>$session]);
  
    }
    /**
     * @Route("/updateHotel/{id}", name="updateHotel")
     */
    public function updateHotel(Request $request,$id,SessionInterface $session)
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
    public function deleteHotel(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Hotel=$em->getRepository(Hotel::class)->find($id);
        $em->remove($Hotel,$id);
        $em->flush();
        
        return $this->redirectToRoute('adminhotels');

      
    }

    /**
     * @Route("/admin/HotelsOrderASC/trie" , name="HotelsOrderASC")
     */
    public function HotelsOrderASC(SessionInterface $session){
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $hotels = $repository->trierNomASC();
        return $this->render('hebergement/adminhotels.html.twig', [
            
            'hotels' => $hotels,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/admin/HotelsOrderDESC/trie" , name="HotelsOrderDESC")
     */
    public function HotelsOrderDESC(SessionInterface $session){
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $hotels = $repository->trierNomDESC();
        return $this->render('hebergement/adminhotels.html.twig', [
            
            'hotels' => $hotels,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/hebergement/HotelsRecent" , name="HotelsRecent")
     */
    public function HotelsRecent(Request $request , PaginatorInterface $paginator,SessionInterface $session ):Response
    {
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $hotels = $repository->trierParDate();
        $Hotels = $paginator->paginate(
            $hotels,
            $request->query->getInt('page',1),
            3
        );
        return $this->render('hebergement/hotels.html.twig', [
            'hotels' => $Hotels,
            "session"=>$session,
        ]);
    }






    /**
     * @Route("/hebergement/maisondhotes", name="maisondhotes")
     */
    public function maisondhotes(Request $request ,PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Maisondhote::Class);
        $Maisons=$repository->findAll();
        $maisons = $paginator->paginate(
            $Maisons,
            $request->query->getInt('page',1),
            3
        );
        
        return $this->render('hebergement/maisondhotes.html.twig', [
            'maisons' => $maisons,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/hebergement/admin/maisondhotes", name="adminmaisondhotes")
     */
    public function adminmaisondhotes(SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Maisondhote::Class);
        $Maisons=$repository->findAll();
        return $this->render('hebergement/adminmaisondhotes.html.twig', [
            'maisons' => $Maisons,
            "session"=>$session,
        ]);
    }
     /**
     * @Route("/hebergement/newMaison", name="newMaison")
     */
    public function newMaison(Request $request,SessionInterface $session)
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
         ['form' => $form->createView(), "session"=>$session,]);
  
    }/**
     * @Route("/updateMaison/{id}", name="updateMaison")
     */
    public function updateMaison(Request $request,$id,SessionInterface $session)
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
         ['form' => $form->createView(), "session"=>$session,]);
  
    }
    /**
     * @Route("/deleteMaison/{id}", name="deleteMaison")
     */
    public function deleteMaison(Request $request,$id,SessionInterface $session)
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
    public function villa(Request $request , PaginatorInterface $paginator,SessionInterface $session ): Response
    {
        $repository=$this->getDoctrine()->getRepository(Villa::Class);
        $Villas=$repository->findAll();
        $villas = $paginator->paginate(
            $Villas,
            $request->query->getInt('page',1),
            3
        );
        return $this->render('hebergement/villa.html.twig', [
            'villas' => $villas,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/hebergement/admin/villas", name="adminvillas")
     */
    public function adminvilla(SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Villa::Class);
        $Villas=$repository->findAll();
        return $this->render('hebergement/adminvilla.html.twig', [
            'villas' => $Villas,
            "session"=>$session,
        ]);
    }
     /**
     * @Route("/hebergement/newVilla", name="newVilla")
     */
    public function newVilla(Request $request,SessionInterface $session)
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
         ['form' => $form->createView(), "session"=>$session]);
  
    }
    /**
     * @Route("/updateVilla/{id}", name="updateVilla")
     */
    public function updateVilla(Request $request,$id,SessionInterface $session)
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
         ['form' => $form->createView(), "session"=>$session,]);
  
    }
    /**
     * @Route("/deleteVilla/{id}", name="deleteVilla")
     */
    public function deleteVilla(Request $request,$id,SessionInterface $session)
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
    public function villadetails(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Villa=$em->getRepository(Villa::class)->find($id);
        
        
        return $this->render('hebergement/villadetails.html.twig',[
            'villa' => $Villa,
            "session"=>$session,
         ]);
  
    }
    /**
     * @Route("/Hoteldetails/{id}", name="hoteldetails")
     */
    public function hoteldetails(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Hotel=$em->getRepository(Hotel::class)->find($id);
        
        
        return $this->render('hebergement/hoteldetails.html.twig',[
            'hotel' => $Hotel,
            "session"=>$session,
         ]);
  
    }
    /**
     * @Route("/hebergement/lastHotel" , name="lastHotel")
     */
    public function lastHotel(SessionInterface $session){
        $em=$this->getDoctrine()->getManager();
        $q=$em->createQuery(
            "select h 
            FROM App\Entity\Hotel h
            ORDER BY h.id DESC"
        );
        $q->setFirstResult(0);
        $q->setMaxResults(1);
        $r=$q->getResult();

        return $this->render('hebergement/last.html.twig', [
            
            'hotel' => $r,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/Maisondetails/{id}", name="maisondetails")
     */
    public function maisondetails(Request $request,$id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $Maisondhote=$em->getRepository(Maisondhote::class)->find($id);
        
        
        return $this->render('hebergement/maisondetails.html.twig',[
            'maison' => $Maisondhote,
            "session"=>$session,
         ]);
  
    }

    /**
     * @Route("/hebergement/admin/stat", name="adminstat")
     */
    public function adminstat(SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(Hotel::Class);
        $Hotels=$repository->findAll();
        $repository=$this->getDoctrine()->getRepository(Villa::Class);
        $villa=$repository->findAll();
        $repository=$this->getDoctrine()->getRepository(Maisondhote::Class);
        $maisondhotes=$repository->findAll();
        return $this->render('hebergement/stat.html.twig', [
            'hotels' => $Hotels,
            'villa' =>  $villa,
            'maisondhotes' => $maisondhotes,
            "session"=>$session,
        ]);
    }
    /**
     * @Route("/hotelspdf", name="hotelspdf", methods={"GET"})
     */
    public function hotelpdf(HotelRepository $HotelRepository,SessionInterface $session): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('hebergement/hotelspdf.html.twig', [
            'hotels' => $HotelRepository->findAll(),
            "session"=>$session,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("hotels.pdf", [
            "Attachment" => false
        ]);
    }
        /**
     * @Route("/maisonpdf", name="maisonpdf", methods={"GET"})
     */
    public function maisonpdf(MaisondhoteRepository $MaisondhoteRepository,SessionInterface $session): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('hebergement/maisonpdf.html.twig', [
            'maisondhotes' => $MaisondhoteRepository->findAll(),
            "session"=>$session,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("MaisonDhotes.pdf", [
            "Attachment" => false
        ]);
    }
    /**
     * @Route("/villapdf", name="villapdf", methods={"GET"})
     */
    public function villapdf(VillaRepository $VillaRepository,SessionInterface $session): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('hebergement/villapdf.html.twig', [
            'villas' => $VillaRepository->findAll(),
            "session"=>$session,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("Villas.pdf", [
            "Attachment" => false,
        ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("/searchhotel",name="searchhotel")
     */
    public function search(Request $request,SessionInterface $session)
    {
        $repository = $this->getDoctrine()->getRepository(Hotel::class);
        $requestString=$request->get('searchValue');
        $hotel = $repository->findHotelbyNom($requestString);
        return $this->render('hebergement/searchhotel.html.twig' ,[
            "hotels"=>$hotel,
            "session"=>$session,
        ]);
    }
}
