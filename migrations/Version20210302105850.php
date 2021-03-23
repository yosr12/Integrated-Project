<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210302105850 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE sujet (id INT AUTO_INCREMENT NOT NULL, utilisateur_id INT NOT NULL, sujet VARCHAR(255) NOT NULL, description LONGTEXT DEFAULT NULL, image VARCHAR(200) DEFAULT NULL, INDEX IDX_2E13599DFB88E14F (utilisateur_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE sujet_tags (sujet_id INT NOT NULL, tags_id INT NOT NULL, INDEX IDX_B86D53B17C4D497E (sujet_id), INDEX IDX_B86D53B18D7B4FB4 (tags_id), PRIMARY KEY(sujet_id, tags_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE tags (id INT AUTO_INCREMENT NOT NULL, tag VARCHAR(20) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE utilisateur (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(15) NOT NULL, prenom VARCHAR(15) NOT NULL, datenaissaince DATE DEFAULT NULL, telephone VARCHAR(15) DEFAULT NULL, mail VARCHAR(50) NOT NULL, password VARCHAR(255) NOT NULL, image VARCHAR(200) DEFAULT NULL, codepromo INT DEFAULT NULL, nbpoints INT DEFAULT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE sujet ADD CONSTRAINT FK_2E13599DFB88E14F FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE sujet_tags ADD CONSTRAINT FK_B86D53B17C4D497E FOREIGN KEY (sujet_id) REFERENCES sujet (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE sujet_tags ADD CONSTRAINT FK_B86D53B18D7B4FB4 FOREIGN KEY (tags_id) REFERENCES tags (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE sujet_tags DROP FOREIGN KEY FK_B86D53B17C4D497E');
        $this->addSql('ALTER TABLE sujet_tags DROP FOREIGN KEY FK_B86D53B18D7B4FB4');
        $this->addSql('ALTER TABLE sujet DROP FOREIGN KEY FK_2E13599DFB88E14F');
        $this->addSql('DROP TABLE sujet');
        $this->addSql('DROP TABLE sujet_tags');
        $this->addSql('DROP TABLE tags');
        $this->addSql('DROP TABLE utilisateur');
    }
}
