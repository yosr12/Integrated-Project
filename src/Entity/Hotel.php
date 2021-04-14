<?php

namespace App\Entity;

use App\Repository\HotelRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=HotelRepository::class)
 * @ORM\Table(name="`hotel`")
 */

class Hotel
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Vérifier le nom! ")
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Vérifier l'adresse! ")
     */
    private $adresse;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Vérifier le prix! ")

     */
    private $price;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Vérifier l'image! ")

     */
    private $image;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotBlank(message="Vérifier la description! ")
     */
    private $description;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank(message="Vérifier la date! ")
     * @Assert\Expression("this.getDatedebut() < this.getDatefin()",message="Date début ne doit pas être dépassée la date fin")
     */
    private $datedebut;

    /**
     * @ORM\Column(type="date")
     * @Assert\Expression("this.getDateFin() > this.getDatedebut()",message="Date fin ne doit pas être antérieur à la date debut")
     */
    private $datefin;

    /**
     * @ORM\OneToMany(targetEntity=Reservation::class, mappedBy="hotel")
     */
    private $reservations;

    public function __construct()
    {
        $this->reservations = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getPrice(): ?float
    {
        return $this->price;
    }

    public function setPrice(float $price): self
    {
        $this->price = $price;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image): self
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

    public function getDatedebut(): ?\DateTimeInterface
    {
        return $this->datedebut;
    }

    public function setDatedebut(\DateTimeInterface $date): self
    {
        $this->datedebut = $date;

        return $this;
    }

    public function getDatefin(): ?\DateTimeInterface
    {
        return $this->datefin;
    }

    public function setDatefin(\DateTimeInterface $datefin): self
    {
        $this->datefin = $datefin;

        return $this;
    }

    /**
     * @return Collection|Reservation[]
     */
    public function getReservations(): Collection
    {
        return $this->reservations;
    }

    public function addReservation(Reservation $reservation): self
    {
        if (!$this->reservations->contains($reservation)) {
            $this->reservations[] = $reservation;
            $reservation->setHotel($this);
        }

        return $this;
    }

    public function removeReservation(Reservation $reservation): self
    {
        if ($this->reservations->removeElement($reservation)) {
            // set the owning side to null (unless already changed)
            if ($reservation->getHotel() === $this) {
                $reservation->setHotel(null);
            }
        }

        return $this;
    }
}
