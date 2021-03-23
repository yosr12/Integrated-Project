<?php

namespace App\Repository;

use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Produit|null find($id, $lockMode = null, $lockVersion = null)
 * @method Produit|null findOneBy(array $criteria, array $orderBy = null)
 * @method Produit[]    findAll()
 * @method Produit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }

    // /**
    //  * @return Produit[] Returns an array of Produit objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('p.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */
     public function orderbyprice(){
    $em=$this->getEntityManager();
    $query=$em->createQuery('select r from App\Entity\Produit r order by r.Prix ASC ');
    return $query->getResult();
    }
    public function orderbypricedown(){
        $em=$this->getEntityManager();
        $query=$em->createQuery('select r from App\Entity\Produit r order by r.Prix DESC ');
        return $query->getResult();
    }
    public function findProductbyname($Nom){
        return $this->createQueryBuilder('Produit')
            ->where('Produit.Nom LIKE :Nom')
            ->setParameter('Nom', '%'.$Nom.'%')
            ->getQuery()
            ->getResult();
    }

    public function findrdvBydate($produit)
    {
        return $this->createQueryBuilder('r')
            ->where('r.Nom Like :Nom')
            ->setParameter('Nom', '%'.$produit.'%')
            ->getQuery()
            ->getResult()
            ;
    }
    /*
    public function findOneBySomeField($value): ?Produit
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
