<?php

namespace App\Repository;

use App\Entity\Hotel;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Hotel|null find($id, $lockMode = null, $lockVersion = null)
 * @method Hotel|null findOneBy(array $criteria, array $orderBy = null)
 * @method Hotel[]    findAll()
 * @method Hotel[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class HotelRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Hotel::class);
    }
    public function findHotelbyNom($nom){
        return $this->createQueryBuilder('hotel')
            ->where('hotel.nom LIKE :nom')
            ->setParameter('nom', '%'.$nom.'%')
            ->getQuery()
            ->getResult();
    }
    public function trierNomASC()
    {
        return $this->createQueryBuilder('hotel')
            ->orderBy('hotel.nom','ASC')
            ->getQuery()
            ->getResult()
        ;
    }
    public function trierNomDESC()
    {
        return $this->createQueryBuilder('hotel')
            ->orderBy('hotel.nom','DESC')
            ->getQuery()
            ->getResult()
        ;
    }
    public function trierParDate()
    {
        return $this->createQueryBuilder('hotel')
            ->orderBy('hotel.datedebut','DESC')
            ->getQuery()
            ->getResult()
        ;
    }
    public function findEntitiesByString($str){
        return $this->getEntityManager()
            ->createQuery(
                'SELECT p
                FROM hotel p
                WHERE p.nom LIKE :str'
            )
            ->setParameter('str', '%'.$str.'%')
            ->getResult();
    }
    public function findlastone()
    {
        return $this->createQueryBuilder('h')
            ->orderBy('h.id', 'DESC')
            ->setMaxResults(1)
            ->setFirstResult(5)
            ->getQuery()
            ->getResult()
        ;
    }


    // /**
    //  * @return Hotel[] Returns an array of Hotel objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('h')
            ->andWhere('h.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('h.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Hotel
    {
        return $this->createQueryBuilder('h')
            ->andWhere('h.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
