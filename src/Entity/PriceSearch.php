<?php
namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
class PriceSearch{
    /**
     * @var int|null
     *  @Assert\Expression(
     *     "this.getPrixMin() < this.getPrixMax()",
     *     message="Le prix min ne doit pas être supérieur au prix max")
     */
    private $PrixMin ;

    /**
     * @var int|null
     */

    private $PrixMax ;

    /**
     * @return int|null
     */
    public function getPrixMin(): ?int
    {
        return $this->PrixMin;
    }

    /**
     * @param int|null $PrixMin
     * @return PriceSearch
     */
    public function setPrixMin(int $PrixMin): PriceSearch
    {
        $this->PrixMin = $PrixMin;
        return $this;
    }

    /**
     * @return int|null
     */
    public function getPrixMax(): ?int
    {
        return $this->PrixMax;
    }

    /**
     * @param int|null $PrixMax
     * @return PriceSearch
     */
    public function setPrixMax(int $PrixMax): PriceSearch
    {
        $this->PrixMax = $PrixMax;
        return $this;
    }

}

