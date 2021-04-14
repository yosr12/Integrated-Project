<?php

namespace App\Repository;

use App\Entity\Voyage;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Voyage|null find($id, $lockMode = null, $lockVersion = null)
 * @method Voyage|null findOneBy(array $criteria, array $orderBy = null)
 * @method Voyage[]    findAll()
 * @method Voyage[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class VoyageRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Voyage::class);
    }

    // /**
    //  * @return Voyage[] Returns an array of Voyage objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('v')
            ->andWhere('v.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('v.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Voyage
    {
        return $this->createQueryBuilder('v')
            ->andWhere('v.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

    public function findByPrixAsc()
    {
        return $this->createQueryBuilder('c')
            ->orderBy('c.prix', 'ASC')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findByPrixDesc()
    {
        return $this->createQueryBuilder('c')
            ->orderBy('c.prix', 'Desc')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findByPriceRange($PrixMin,$PrixMax){
        return $this->createQueryBuilder('v')
            ->andWhere('v.prix >= :PrixMin')
            ->setParameter('PrixMin',$PrixMin)
            ->andWhere('v.prix <= :PrixMax')
            ->setParameter('PrixMax',$PrixMax)
            ->orderBy('v.prix','ASC')
            ->getQuery()
            ->getResult()
            ;
    }




    public function findrdvBydestination($voyage)
    {
        return $this->createQueryBuilder('v')
            ->where('v.destination Like :destination')
            ->setParameter('destination', '%'.$voyage.'%')
            ->getQuery()
            ->getResult()
            ;
    }


}