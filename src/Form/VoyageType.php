<?php

namespace App\Form;

use App\Entity\Voyage;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\RangeType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;



class VoyageType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('destination',TextType::class)
            ->add('categorie',ChoiceType::class, [
                'choices'  => [
                    'Tunisie' => 'Tunisie',
                    'Etranger' => 'Etranger',
                ],])
            ->add('description',TextareaType::class)
            ->add('prix',IntegerType::class)
            ->add('lng')
            ->add('lat')
            ->add('dateDebut',DateType::class)
            ->add('dateFin',DateType::class)
                        ->add('image',FileType::class,array('data_class'=>null,'required'=>false))
            ->add('inclut',TextareaType::class)
            ->add('ninclut',TextareaType::class)
            ->add('programme',TextareaType::class)




        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Voyage::class,
        ]);
    }
}
