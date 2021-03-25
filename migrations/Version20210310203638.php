<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210310203638 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE promotion DROP FOREIGN KEY FK_C11D7DD168C9E5AF');
        $this->addSql('DROP INDEX UNIQ_C11D7DD168C9E5AF ON promotion');
        $this->addSql('ALTER TABLE promotion DROP voyage_id');
        $this->addSql('ALTER TABLE voyage ADD promotion_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE voyage ADD CONSTRAINT FK_3F9D8955139DF194 FOREIGN KEY (promotion_id) REFERENCES promotion (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_3F9D8955139DF194 ON voyage (promotion_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE promotion ADD voyage_id INT NOT NULL');
        $this->addSql('ALTER TABLE promotion ADD CONSTRAINT FK_C11D7DD168C9E5AF FOREIGN KEY (voyage_id) REFERENCES voyage (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_C11D7DD168C9E5AF ON promotion (voyage_id)');
        $this->addSql('ALTER TABLE voyage DROP FOREIGN KEY FK_3F9D8955139DF194');
        $this->addSql('DROP INDEX UNIQ_3F9D8955139DF194 ON voyage');
        $this->addSql('ALTER TABLE voyage DROP promotion_id');
    }
}
