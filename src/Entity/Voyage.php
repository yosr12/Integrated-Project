<?php

namespace App\Entity;

use App\Repository\VoyageRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;


/**
 * @ORM\Entity(repositoryClass=VoyageRepository::class)
 */
class Voyage
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")
     * @Assert\Length(
     *     min=3,
     *     max=50,
     *     minMessage="la destination doit comporter au moins 3 caractértes ",
     *     maxMessage="la destination doit comporter au plus 50 caractértes ")
     *
     */
    private $destination;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Il faut insérer une image")

     */
    private $image;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")

     */
    private $description;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")

     * @Assert\Positive(

     *     message="le prix d'un voyage ne doit pas etre negatif ou égale à 0")


     */
    private $prix;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")
     * @Assert\Date ()
     * @Assert\GreaterThan ("today")

     */
    private $dateDebut;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")
     * @Assert\Expression(
     *     "this.getdateDebut() < this.getdateFin()",
     *     message="La date fin ne doit pas être antérieur à la date début")


     *
     */
    private $dateFin;

    /**
     * @ORM\Column(type="string", length=255)

     *
     */
    private $categorie;

    /**
     * @ORM\OneToOne(targetEntity=Promotion::class, inversedBy="voyage", cascade={"persist", "remove"})
     */
    private $promotion;

    /**
     * @ORM\Column(type="float", scale=4 , precision=7 )
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")
     */
    private $lng;

    /**
     * @ORM\Column(type="float", scale=4 , precision=6 )
     * @Assert\NotBlank(message="Cette valeur ne doit pas être vide")
     */
    private $lat;

    /**
     * @ORM\Column(type="text", nullable=true)
     */
    private $programme;

    /**
     * @ORM\Column(type="text", nullable=true)
     */
    private $inclut;

    /**
     * @ORM\Column(type="text", nullable=true)
     */
    private $ninclut;




    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDestination(): ?string
    {
        return $this->destination;
    }

    public function setDestination(string $destination): self
    {
        $this->destination = $destination;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image)
    {
        $this->image = $image;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    public function setCategorie(string $categorie): self
    {
        $this->categorie = $categorie;

        return $this;
    }

    public function getPromotion(): ?Promotion
    {
        return $this->promotion;
    }

    public function setPromotion(?Promotion $promotion): self
    {
        $this->promotion = $promotion;

        return $this;
    }

    public function getLng(): ?float
    {
        return $this->lng;
    }

    public function setLng(float $lng): self
    {
        $this->lng = $lng;

        return $this;
    }

    public function getLat(): ?float
    {
        return $this->lat;
    }

    public function setLat(float $lat): self
    {
        $this->lat = $lat;

        return $this;
    }

    public function getProgramme(): ?string
    {
        return $this->programme;
    }

    public function setProgramme(?string $programme): self
    {
        $this->programme = $programme;

        return $this;
    }

    public function getInclut(): ?string
    {
        return $this->inclut;
    }

    public function setInclut(?string $inclut): self
    {
        $this->inclut = $inclut;

        return $this;
    }

    public function getNinclut(): ?string
    {
        return $this->ninclut;
    }

    public function setNinclut(?string $ninclut): self
    {
        $this->ninclut = $ninclut;

        return $this;
    }


}
