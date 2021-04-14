<?php

namespace App\Controller;

use App\Entity\Admin;
use App\Form\AdminType;
use App\Repository\AdminRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

/**
 * @Route("/admin")
 */
class AdminController extends AbstractController
{
    /**
     * @Route("/", name="admin_index", methods={"GET"})
     */
    public function index(AdminRepository $adminRepository,SessionInterface $session): Response
    {
        return $this->render('admin/index.html.twig', [
            'admins' => $adminRepository->findAll(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/new", name="admin_new", methods={"GET","POST"})
     */
    public function new(Request $request,SessionInterface $session): Response
    {
        $admin = new Admin();
        $form = $this->createForm(AdminType::class, $admin);
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
            $admin=$form->getData();
            $file=$admin->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $admin->setImage($fileName);

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($admin);
            $entityManager->flush();

            return $this->redirectToRoute('admin_index');
        }

        return $this->render('admin/new.html.twig', [
            'admin' => $admin,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="admin_show", methods={"GET"})
     */
    public function show(Admin $admin,SessionInterface $session): Response
    {
        return $this->render('admin/show.html.twig', [
            'admin' => $admin,
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="admin_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Admin $admin,SessionInterface $session): Response
    {
        $form = $this->createForm(AdminType::class, $admin);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('admin_index');
        }

        return $this->render('admin/edit.html.twig', [
            'admin' => $admin,
            'form' => $form->createView(),
            "session"=>$session,
        ]);
    }

    /**
     * @Route("/{id}", name="admin_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Admin $admin,SessionInterface $session): Response
    {
        if ($this->isCsrfTokenValid('delete'.$admin->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($admin);
            $entityManager->flush();
        }

        return $this->redirectToRoute('admin_index');
    }
    /**
         * @Route("/logoutadmin/logout/logout2", name="logoutadmin")
     */
    public function logout(SessionInterface $session){
        $session->remove('admin');
        return $this->redirectToRoute('login', [
            'session'=>$session,
        ]);
    }
}
