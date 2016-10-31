-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pickglobe
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pickglobe
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pickglobe` DEFAULT CHARACTER SET utf8 ;
USE `pickglobe` ;

-- -----------------------------------------------------
-- Table `pickglobe`.`Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Link` (
  `codLink` INT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NULL,
  `caminho` VARCHAR(45) NULL,
  PRIMARY KEY (`codLink`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Site` (
  `codSites` INT NOT NULL AUTO_INCREMENT,
  `tempoColeta` TIME NULL,
  `url` VARCHAR(255) NULL,
  `status` TINYINT(1) NULL,
  `descricao` VARCHAR(60) NULL,
  PRIMARY KEY (`codSites`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Coleta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Coleta` (
  `codColeta` INT NOT NULL AUTO_INCREMENT,
  `md5` VARCHAR(45) NULL,
  `Site_codSites` INT NOT NULL,
  PRIMARY KEY (`codColeta`),
  INDEX `fk_Coleta_Site1_idx` (`Site_codSites` ASC),
  CONSTRAINT `fk_Coleta_Site1`
    FOREIGN KEY (`Site_codSites`)
    REFERENCES `pickglobe`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Extensao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Extensao` (
  `codExtensoes` INT NOT NULL AUTO_INCREMENT,
  `tipoExtensao` VARCHAR(45) NULL,
  PRIMARY KEY (`codExtensoes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Palavra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Palavra` (
  `codPalavras` INT NOT NULL AUTO_INCREMENT,
  `palavra` VARCHAR(45) NULL,
  `tipo` VARCHAR(45) NULL,
  PRIMARY KEY (`codPalavras`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Nome`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Nome` (
  `codNomes` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`codNomes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ExtensaoSite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ExtensaoSite` (
  `Extensoes_codExtensoes` INT NOT NULL,
  `Sites_codSites` INT NOT NULL,
  PRIMARY KEY (`Extensoes_codExtensoes`, `Sites_codSites`),
  INDEX `fk_Extensoes_has_Sites_Sites1_idx` (`Sites_codSites` ASC),
  INDEX `fk_Extensoes_has_Sites_Extensoes1_idx` (`Extensoes_codExtensoes` ASC),
  CONSTRAINT `fk_Extensoes_has_Sites_Extensoes1`
    FOREIGN KEY (`Extensoes_codExtensoes`)
    REFERENCES `pickglobe`.`Extensao` (`codExtensoes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Extensoes_has_Sites_Sites1`
    FOREIGN KEY (`Sites_codSites`)
    REFERENCES `pickglobe`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaPalavras` (
  `Site_codSites` INT NOT NULL,
  PRIMARY KEY (`Site_codSites`),
  CONSTRAINT `fk_ListaPalavras_Site1`
    FOREIGN KEY (`Site_codSites`)
    REFERENCES `pickglobe`.`Site` (`codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaPalavrasNome`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaPalavrasNome` (
  `ListaPalavras_Site_codSites` INT NOT NULL,
  `Nome_codNomes` INT NOT NULL,
  PRIMARY KEY (`ListaPalavras_Site_codSites`, `Nome_codNomes`),
  INDEX `fk_ListaPalavras_has_Nome_Nome1_idx` (`Nome_codNomes` ASC),
  INDEX `fk_ListaPalavras_has_Nome_ListaPalavras1_idx` (`ListaPalavras_Site_codSites` ASC),
  CONSTRAINT `fk_ListaPalavras_has_Nome_ListaPalavras1`
    FOREIGN KEY (`ListaPalavras_Site_codSites`)
    REFERENCES `pickglobe`.`ListaPalavras` (`Site_codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ListaPalavras_has_Nome_Nome1`
    FOREIGN KEY (`Nome_codNomes`)
    REFERENCES `pickglobe`.`Nome` (`codNomes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaPalavrasPalavra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaPalavrasPalavra` (
  `ListaPalavras_Site_codSites` INT NOT NULL,
  `Palavra_codPalavras` INT NOT NULL,
  PRIMARY KEY (`ListaPalavras_Site_codSites`, `Palavra_codPalavras`),
  INDEX `fk_ListaPalavras_has_Palavra_Palavra1_idx` (`Palavra_codPalavras` ASC),
  INDEX `fk_ListaPalavras_has_Palavra_ListaPalavras1_idx` (`ListaPalavras_Site_codSites` ASC),
  CONSTRAINT `fk_ListaPalavras_has_Palavra_ListaPalavras1`
    FOREIGN KEY (`ListaPalavras_Site_codSites`)
    REFERENCES `pickglobe`.`ListaPalavras` (`Site_codSites`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ListaPalavras_has_Palavra_Palavra1`
    FOREIGN KEY (`Palavra_codPalavras`)
    REFERENCES `pickglobe`.`Palavra` (`codPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ColetaLink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ColetaLink` (
  `Coleta_codColeta` INT NOT NULL,
  `Link_codLink` INT NOT NULL,
  PRIMARY KEY (`Coleta_codColeta`, `Link_codLink`),
  INDEX `fk_Coleta_has_Link_Link1_idx` (`Link_codLink` ASC),
  INDEX `fk_Coleta_has_Link_Coleta1_idx` (`Coleta_codColeta` ASC),
  CONSTRAINT `fk_Coleta_has_Link_Coleta1`
    FOREIGN KEY (`Coleta_codColeta`)
    REFERENCES `pickglobe`.`Coleta` (`codColeta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Coleta_has_Link_Link1`
    FOREIGN KEY (`Link_codLink`)
    REFERENCES `pickglobe`.`Link` (`codLink`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`NomeLink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`NomeLink` (
  `Nome_codNomes` INT NOT NULL,
  `Link_codLink` INT NOT NULL,
  `qtd` INT NULL,
  PRIMARY KEY (`Nome_codNomes`, `Link_codLink`),
  INDEX `fk_Nome_has_Link_Link1_idx` (`Link_codLink` ASC),
  INDEX `fk_Nome_has_Link_Nome1_idx` (`Nome_codNomes` ASC),
  CONSTRAINT `fk_Nome_has_Link_Nome1`
    FOREIGN KEY (`Nome_codNomes`)
    REFERENCES `pickglobe`.`Nome` (`codNomes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Nome_has_Link_Link1`
    FOREIGN KEY (`Link_codLink`)
    REFERENCES `pickglobe`.`Link` (`codLink`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`PalavraLink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`PalavraLink` (
  `Palavra_codPalavras` INT NOT NULL,
  `Link_codLink` INT NOT NULL,
  `qtd` INT NULL,
  PRIMARY KEY (`Palavra_codPalavras`, `Link_codLink`),
  INDEX `fk_Palavra_has_Link_Link1_idx` (`Link_codLink` ASC),
  INDEX `fk_Palavra_has_Link_Palavra1_idx` (`Palavra_codPalavras` ASC),
  CONSTRAINT `fk_Palavra_has_Link_Palavra1`
    FOREIGN KEY (`Palavra_codPalavras`)
    REFERENCES `pickglobe`.`Palavra` (`codPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palavra_has_Link_Link1`
    FOREIGN KEY (`Link_codLink`)
    REFERENCES `pickglobe`.`Link` (`codLink`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
