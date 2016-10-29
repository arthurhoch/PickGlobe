-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dbPGBD
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbPGBD
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbPGBD` DEFAULT CHARACTER SET utf8 ;
USE `dbPGBD` ;

-- -----------------------------------------------------
-- Table `dbPGBD`.`Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Link` (
  `url` VARCHAR(255) NOT NULL,
  `caminho` VARCHAR(45) NULL,
  PRIMARY KEY (`url`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Site` (
  `codSites` INT NOT NULL AUTO_INCREMENT,
  `tempoColeta` TIME NULL,
  `url` VARCHAR(255) NULL,
  `status` TINYINT(1) NULL,
  `descricao` VARCHAR(60) NULL,
  PRIMARY KEY (`codSites`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Coleta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Coleta` (
  `codColeta` INT NOT NULL AUTO_INCREMENT,
  `md5` VARCHAR(45) NULL,
  `Site_codSites` INT NOT NULL,
  PRIMARY KEY (`codColeta`),
  INDEX `fk_Coleta_Site1_idx` (`Site_codSites` ASC),
  CONSTRAINT `fk_Coleta_Site1`
    FOREIGN KEY (`Site_codSites`)
    REFERENCES `dbPGBD`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Extensao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Extensao` (
  `codExtensoes` INT NOT NULL AUTO_INCREMENT,
  `tipoExtensao` VARCHAR(45) NULL,
  PRIMARY KEY (`codExtensoes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Palavra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Palavra` (
  `codPalavras` INT NOT NULL AUTO_INCREMENT,
  `palavra` VARCHAR(45) NULL,
  `tipo` VARCHAR(45) NULL,
  PRIMARY KEY (`codPalavras`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Nome`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Nome` (
  `codNomes` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`codNomes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`ExtensaoSite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`ExtensaoSite` (
  `Extensoes_codExtensoes` INT NOT NULL,
  `Sites_codSites` INT NOT NULL,
  PRIMARY KEY (`Extensoes_codExtensoes`, `Sites_codSites`),
  INDEX `fk_Extensoes_has_Sites_Sites1_idx` (`Sites_codSites` ASC),
  INDEX `fk_Extensoes_has_Sites_Extensoes1_idx` (`Extensoes_codExtensoes` ASC),
  CONSTRAINT `fk_Extensoes_has_Sites_Extensoes1`
    FOREIGN KEY (`Extensoes_codExtensoes`)
    REFERENCES `dbPGBD`.`Extensao` (`codExtensoes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Extensoes_has_Sites_Sites1`
    FOREIGN KEY (`Sites_codSites`)
    REFERENCES `dbPGBD`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`LinkColeta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`LinkColeta` (
  `Coleta_codColeta` INT NOT NULL,
  `Coleta_md5` VARCHAR(45) NOT NULL,
  `Link_url` VARCHAR(255) NOT NULL,
  `data` DATE NULL,
  `hora` TIME NULL,
  PRIMARY KEY (`Coleta_codColeta`, `Coleta_md5`, `Link_url`),
  INDEX `fk_Links_has_Coleta_Coleta1_idx` (`Coleta_codColeta` ASC, `Coleta_md5` ASC),
  INDEX `fk_LinkColeta_Link1_idx` (`Link_url` ASC),
  CONSTRAINT `fk_Links_has_Coleta_Coleta1`
    FOREIGN KEY (`Coleta_codColeta` , `Coleta_md5`)
    REFERENCES `dbPGBD`.`Coleta` (`codColeta` , `md5`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LinkColeta_Link1`
    FOREIGN KEY (`Link_url`)
    REFERENCES `dbPGBD`.`Link` (`url`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`ListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`ListaPalavras` (
  `codListaPalavras` INT NOT NULL AUTO_INCREMENT,
  `Site_codSites` INT NOT NULL,
  PRIMARY KEY (`Site_codSites`),
  CONSTRAINT `fk_ListaPalavras_Site1`
    FOREIGN KEY (`Site_codSites`)
    REFERENCES `dbPGBD`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Nome_has_Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Nome_has_Link` (
  `Nome_codNomes` INT NOT NULL,
  `Link_url` VARCHAR(255) NOT NULL,
  `qtd` INT NULL,
  PRIMARY KEY (`Nome_codNomes`, `Link_url`),
  INDEX `fk_Nome_has_Link_Link1_idx` (`Link_url` ASC),
  INDEX `fk_Nome_has_Link_Nome1_idx` (`Nome_codNomes` ASC),
  CONSTRAINT `fk_Nome_has_Link_Nome1`
    FOREIGN KEY (`Nome_codNomes`)
    REFERENCES `dbPGBD`.`Nome` (`codNomes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Nome_has_Link_Link1`
    FOREIGN KEY (`Link_url`)
    REFERENCES `dbPGBD`.`Link` (`url`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Palavra_has_Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Palavra_has_Link` (
  `Palavra_codPalavras` INT NOT NULL,
  `Link_url` VARCHAR(255) NOT NULL,
  `qtd` INT NULL,
  PRIMARY KEY (`Palavra_codPalavras`, `Link_url`),
  INDEX `fk_Palavra_has_Link_Link1_idx` (`Link_url` ASC),
  INDEX `fk_Palavra_has_Link_Palavra1_idx` (`Palavra_codPalavras` ASC),
  CONSTRAINT `fk_Palavra_has_Link_Palavra1`
    FOREIGN KEY (`Palavra_codPalavras`)
    REFERENCES `dbPGBD`.`Palavra` (`codPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palavra_has_Link_Link1`
    FOREIGN KEY (`Link_url`)
    REFERENCES `dbPGBD`.`Link` (`url`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Nome_has_ListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Nome_has_ListaPalavras` (
  `Nome_codNomes` INT NOT NULL,
  `ListaPalavras_Site_codSites` INT NOT NULL,
  PRIMARY KEY (`Nome_codNomes`, `ListaPalavras_Site_codSites`),
  INDEX `fk_Nome_has_ListaPalavras_ListaPalavras1_idx` (`ListaPalavras_Site_codSites` ASC),
  INDEX `fk_Nome_has_ListaPalavras_Nome1_idx` (`Nome_codNomes` ASC),
  CONSTRAINT `fk_Nome_has_ListaPalavras_Nome1`
    FOREIGN KEY (`Nome_codNomes`)
    REFERENCES `dbPGBD`.`Nome` (`codNomes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Nome_has_ListaPalavras_ListaPalavras1`
    FOREIGN KEY (`ListaPalavras_Site_codSites`)
    REFERENCES `dbPGBD`.`ListaPalavras` (`Site_codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbPGBD`.`Palavra_has_ListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbPGBD`.`Palavra_has_ListaPalavras` (
  `Palavra_codPalavras` INT NOT NULL,
  `ListaPalavras_Site_codSites` INT NOT NULL,
  PRIMARY KEY (`Palavra_codPalavras`, `ListaPalavras_Site_codSites`),
  INDEX `fk_Palavra_has_ListaPalavras_ListaPalavras1_idx` (`ListaPalavras_Site_codSites` ASC),
  INDEX `fk_Palavra_has_ListaPalavras_Palavra1_idx` (`Palavra_codPalavras` ASC),
  CONSTRAINT `fk_Palavra_has_ListaPalavras_Palavra1`
    FOREIGN KEY (`Palavra_codPalavras`)
    REFERENCES `dbPGBD`.`Palavra` (`codPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palavra_has_ListaPalavras_ListaPalavras1`
    FOREIGN KEY (`ListaPalavras_Site_codSites`)
    REFERENCES `dbPGBD`.`ListaPalavras` (`Site_codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
