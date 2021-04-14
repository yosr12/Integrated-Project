<?php

namespace App\Entity;

use App\Repository\ReactionRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\JoinColumn;

/**
 * @ORM\Entity(repositoryClass=ReactionRepository::class)
 */
class Reaction
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     */
    private $reaction;

    /**
     * @ORM\ManyToOne(targetEntity=Commentaire::class, inversedBy="reactions")
     * @JoinColumn(onDelete="CASCADE")
     */
    private $commentaire;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="reactions")
     * @ORM\JoinColumn(nullable=false)
     */
    private $user;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getReaction(): ?int
    {
        return $this->reaction;
    }

    public function setReaction(int $reaction): self
    {
        $this->reaction = $reaction;

        return $this;
    }

    public function getCommentaire(): ?Commentaire
    {
        return $this->commentaire;
    }

    public function setCommentaire(?Commentaire $commentaire): self
    {
        $this->commentaire = $commentaire;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }
}
