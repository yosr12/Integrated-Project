<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\JoinColumn;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=CommentaireRepository::class)
 */
class Commentaire
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotBlank(message="le commentaire est obligatoire")
     */
    private $commentaire;

    /**
     * @ORM\ManyToOne(targetEntity=Sujet::class, inversedBy="commentaires")
     * @JoinColumn(onDelete="CASCADE")
     */
    private $sujet;

    /**
     * @ORM\ManyToOne(targetEntity=Utilisateur::class, inversedBy="commentaires")
     */
    private $utilisateur;

    /**
     * @ORM\OneToMany(targetEntity=Reaction::class, mappedBy="commentaire")
     * @JoinColumn(onDelete="CASCADE")
     */
    private $reactions;

    /**
     * @ORM\Column(type="text", nullable=true)
     */
    private $filtredcomment;

    public function __construct()
    {
        $this->reactions = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCommentaire(): ?string
    {
        return $this->commentaire;
    }

    public function setCommentaire(string $commentaire): self
    {
        $this->commentaire = $commentaire;

        return $this;
    }

    public function getSujet(): ?Sujet
    {
        return $this->sujet;
    }

    public function setSujet(?Sujet $sujet): self
    {
        $this->sujet = $sujet;

        return $this;
    }

    public function getUtilisateur(): ?Utilisateur
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(?Utilisateur $utilisateur): self
    {
        $this->utilisateur = $utilisateur;

        return $this;
    }

    /**
     * @return Collection|reaction[]
     */
    public function getReactions(): Collection
    {
        return $this->reactions;
    }

    public function addReaction(reaction $reaction): self
    {
        if (!$this->reactions->contains($reaction)) {
            $this->reactions[] = $reaction;
            $reaction->setCommentaire($this);
        }

        return $this;
    }

    public function removeReaction(reaction $reaction): self
    {
        if ($this->reactions->removeElement($reaction)) {
            // set the owning side to null (unless already changed)
            if ($reaction->getCommentaire() === $this) {
                $reaction->setCommentaire(null);
            }
        }

        return $this;
    }

    public function getFiltredcomment(): ?string
    {
        return $this->filtredcomment;
    }

    public function setFiltredcomment(?string $filtredcomment): self
    {
        $this->filtredcomment = $filtredcomment;

        return $this;
    }
}
