<?php

namespace App\Controller;
use App\Entity\Admin;
use Dompdf\Dompdf;
use Dompdf\Options;
use App\Entity\User;
use App\Form\UserType;
use App\Form\LoginType;
use App\Repository\UserRepository;

use App\Form\RecuperermotdepasseType;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Form\Extension\HttpFoundation\HttpFoundationRequestHandler;

class UsersController extends AbstractController
{
    /**
     * @Route("/users", name="users")
     */
    public function index(SessionInterface $session): Response
    {
        return $this->render('users/index.html.twig', [
            'controller_name' => 'UsersController',
            'session'=>$session,

        ]);
    }

    /**
     * @Route("/register", name="register")
     */
    public function inscrit(Request $request ,\Swift_Mailer $mailer)
    {
        $User = new User();
        $form=$this->createForm(UserType::Class,$User);
        $form->add('Register', SubmitType::class);

        $form->handleRequest($request);

       if ($form->isSubmitted()&& $form->isValid()){
           //$User=$form->getData();
           $file=$User->getImage();
           $fileName=md5(uniqid()).'.'.$file->guessExtension();
           $file->move($this->getParameter('upload_directory'), $fileName);
           $User->setImage($fileName);
           $em=$this->getDoctrine()->getManager();
           $em->persist($User);
           $em->flush();
           $message = (new \Swift_Message('User'))
                ->setFrom('taabaniagency@gmail.com')
                ->setTo($User->getEmail())
                ->setBody("Bienvenue à Tabaani Travel Agency");
            $mailer->send($message) ;
           return $this->redirectToRoute('login');
        }
        return $this->render('users/register.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/pdfuser", name="PDFuser", methods={"GET"})
     */
    public function pdf(UserRepository $UserRepository,SessionInterface $session): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('users/pdfusers.html.twig', [
            'users' => $UserRepository->findAll(),
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
     * @Route("/listUser", name="listUser")
     */
    public function listUser(Request $request , PaginatorInterface $paginator,SessionInterface $session ): Response

   {
    $repository=$this->getDoctrine()->getRepository(User::Class);
    $Users=$repository->findAll();
    $users = $paginator->paginate(
        $Users,
        $request->query->getInt('page',1),
       2
    );
    $male=$this->getDoctrine()->getRepository(User::Class)->findMale();
    $female=$this->getDoctrine()->getRepository(User::Class)->findFemale();



    return $this->render('users/listusers.html.twig', [
        'Users' => $users,
        "session"=>$session,
        'male'=>$male,
        'female'=>$female,
    ]);
    
    }
    
    /**
     * @Route("/listadmin", name="listUser2")
     */
    public function listUser2(Request $request , PaginatorInterface $paginator,SessionInterface $session ): Response

   {
    $repository=$this->getDoctrine()->getRepository(User::Class);
    $Users=$repository->findAll();
    $users = $paginator->paginate(
        $Users,
        $request->query->getInt('page',1),
        5

    );

    return $this->render('users/listusers.html.twig', [
        
        'Users' => $users,
        "session"=>$session,
    ]);
    
    }
 /**
     * @Route("/Userlist", name="Userlist")
     */
    public function Userlist(Request $request , PaginatorInterface $paginator,SessionInterface $session ): Response

   {
    $repository=$this->getDoctrine()->getRepository(User::Class);
    $Users=$repository->findAll();
    $users = $paginator->paginate(
        $Users,
        $request->query->getInt('page',1),
       
    );

    return $this->render('users/userslist.html.twig', [
        
        'Users' => $users,
        "session"=>$session,
    ]);
    
    }


    /**
     * @Route("/updateUser/{id}", name="updateUser")
     */
    public function updateUser(Request $request, $id,SessionInterface $session)
    {
        $em=$this->getDoctrine()->getManager();
        $User = $em->getRepository(User::class)->find($id);
        $form = $this->createForm(UserType::class, $User);
        
        $form->add('Modifier',SubmitType::class);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $em->flush();

            return $this->redirectToRoute('listUser');
        }

        return $this->render('users/updateUser.html.twig', [
                        'form' => $form->createView(),
                    "session"=>$session,
        ]);
    }

    /**
     * @Route("/deleteUser/{id}", name="deleteUser")
     */
    public function deleteUser($id)
    {
      
        $em=$this->getDoctrine()->getManager();
        $User = $em->getRepository(User::class)->find($id);
        $em->Remove($User);
         $em->flush();

           return $this->redirectToRoute('listUser');

    }

 /**
     * @Route("/showUser/{id}", name="showUser")
     */

    public function showUser($id,SessionInterface $session): Response
    {
        $repository=$this->getDoctrine()->getRepository(User::Class);
        $User=$repository->find($id);

        return $this->render('users/showUser.html.twig', [
            'User' => $User,
            "session"=>$session,
        ]);
}

 /**
     * @Route("/Usershow", name="Usershow")
     */

    public function Usershow(SessionInterface $session)
    {
        if($session->has('user')){
         $repository=$this->getDoctrine()->getRepository(User::Class);
         $User=$repository->find($session->get('user')->getId());

        return $this->render('users/Usershow.html.twig', [
            'session' => $session,
            'User' => $User,
        ]);
}

else{
    return $this->redirectToRoute('login');
}

    }

/**
     * @Route("/TrierParDate", name="TrierParDate")
     */
    public function TrierParDate(Request $request , PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $repository = $this->getDoctrine()->getRepository(User::class);
        $Users = $repository->findByDate();
        $Users = $paginator->paginate(
            $Users,
            $request->query->getInt('page',1),4
        );
        return $this->render('users/listusers.html.twig', [
            'Users' => $Users,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/TrierParDate2", name="TrierParDate2")
     */
    public function TrierParDate2(Request $request , PaginatorInterface $paginator,SessionInterface $session): Response
    {
        $repository = $this->getDoctrine()->getRepository(User::class);
        $Users = $repository->findByDate2();
        $Users = $paginator->paginate(
            $Users,
            $request->query->getInt('page',1),4
        );
        return $this->render('users/listusers.html.twig', [
            'Users' => $Users,
            "session"=>$session,
        ]);
    }
    
  /**
     * @Route("/login", name="login")
     */
    public function login(UserRepository $userRepository , SessionInterface $session,Request $request, \Swift_Mailer $mailer): Response
    {

        $userlogin = new User();
        $connexionform=$this->createForm(LoginType::class, $userlogin);

        $connexionform->add('Login',SubmitType::class);
        $connexionform->handleRequest($request);

        $recupererform=$this->createForm(RecuperermotdepasseType::class, $userlogin);
        $recupererform->add('Reset',SubmitType::class);
        $recupererform->handleRequest($request);

        if ($recupererform->isSubmitted() && $recupererform->isValid()){
            $em=$this->getDoctrine()->getManager();
            $user = $em->getRepository(User::class)->findOneBy(array('email'=>$userlogin->getEmail()));

            if(is_null($user)){
                return $this->redirectToRoute('login', [
                    'form' => $connexionform->createView(),
                ]);
            }
            else{
                $message = (new \Swift_Message('User'))
                ->setFrom('taabaniesprit@gmail.com')
                ->setTo($user->getEmail())
                ->setBody("votre mot de passe :".$user->getPassword());
                    $mailer->send($message) ;

                return $this->redirectToRoute('login', [
                  
                ]);
            }

        }

        if ($connexionform->isSubmitted()){
            $em=$this->getDoctrine()->getManager();
            $user = $em->getRepository(User::class)->findOneBy(array('email'=>$userlogin->getEmail(),'password'=>$userlogin->getPassword()));
            $admin = $em->getRepository(Admin::class)->findOneBy(array('email'=>$userlogin->getEmail(),'password'=>$userlogin->getPassword()));
            //$user=$userRepository->findOneBy(array('email'=>$userlogin->getEmail(),'password'=>$userlogin->getPassword()));
            if(is_null($user) && is_null($admin)){
                return $this->redirectToRoute('login', [
                    'form' => $connexionform->createView(),
                    'formrec' => $recupererform->createView(),
                ]);
            }
            else if (! is_null($user) ){
                $session->set('user',$user);

                return $this->redirectToRoute('Usershow', [
                  'session'=>$session,
                ]);
            }
            else {
                $session->set('admin',$admin);
                return $this->redirectToRoute('admin_index', [
                    'session'=>$session,
                ]);
            }
        }
        else{
            return $this->render('users/login.html.twig', [
                'form' => $connexionform->createView(),
                'formrec' => $recupererform->createView(),
                "session"=>$session,
            ]);
        }
    }
   

     /**
     * @Route("/logout", name="logout")
     */
    public function logout(SessionInterface $session){
        $session->remove('user');
        
        return $this->redirectToRoute('voyages', [
          'session'=>$session,
        ]);
    }
    
}
