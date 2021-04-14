<?php

namespace App\Entity;

use App\Repository\RechercheDataRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=RechercheDataRepository::class)
 */
class RechercheData
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="float")
     */
    private $Min;

    /**
     * @ORM\Column(type="float")
     */
    private $Max;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMin(): ?float
    {
        return $this->Min;
    }

    public function setMin(float $Min): self
    {
        $this->Min = $Min;

        return $this;
    }

    public function getMax(): ?float
    {
        return $this->Max;
    }

    public function setMax(float $Max): self
    {
        $this->Max = $Max;

        return $this;
    }
}
