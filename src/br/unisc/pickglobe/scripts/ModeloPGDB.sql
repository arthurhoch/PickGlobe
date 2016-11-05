-- MySQL Script generated by MySQL Workbench
-- Sáb 05 Nov 2016 19:29:05 BRST
-- Model: New Model    Version: 1.0
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
  `md5` VARCHAR(45) NOT NULL,
  `caminho` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`codLink`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`TipoLista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`TipoLista` (
  `codTipoLista` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codTipoLista`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaPalavras` (
  `codListaPalavras` INT NOT NULL AUTO_INCREMENT,
  `codTipoLista` INT NULL,
  `nomeLista` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codListaPalavras`),
  INDEX `fk_ListaPalavras_TipoLista1_idx` (`codTipoLista` ASC),
  CONSTRAINT `fk_ListaPalavras_TipoLista1`
    FOREIGN KEY (`codTipoLista`)
    REFERENCES `pickglobe`.`TipoLista` (`codTipoLista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaExtensoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaExtensoes` (
  `codListaExtensoes` INT NOT NULL AUTO_INCREMENT,
  `nomeListaExtensoes` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codListaExtensoes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Site` (
  `codSite` INT NOT NULL AUTO_INCREMENT,
  `codListaPalavras` INT NULL,
  `codListaExtensoes` INT NULL,
  `intervaloColeta` INT NOT NULL,
  `status` TINYINT(1) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`codSite`),
  INDEX `fk_Site_ListaPalavras1_idx` (`codListaPalavras` ASC),
  INDEX `fk_Site_ListaExtensoes1_idx` (`codListaExtensoes` ASC),
  CONSTRAINT `fk_Site_ListaPalavras1`
    FOREIGN KEY (`codListaPalavras`)
    REFERENCES `pickglobe`.`ListaPalavras` (`codListaPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Site_ListaExtensoes1`
    FOREIGN KEY (`codListaExtensoes`)
    REFERENCES `pickglobe`.`ListaExtensoes` (`codListaExtensoes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Coleta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Coleta` (
  `codColeta` INT NOT NULL AUTO_INCREMENT,
  `codSite` INT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  PRIMARY KEY (`codColeta`),
  INDEX `fk_Coleta_Site1_idx` (`codSite` ASC),
  CONSTRAINT `fk_Coleta_Site1`
    FOREIGN KEY (`codSite`)
    REFERENCES `pickglobe`.`Site` (`codSite`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Extensao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Extensao` (
  `codExtensao` INT NOT NULL AUTO_INCREMENT,
  `nomeExtensao` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`codExtensao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`Palavra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`Palavra` (
  `codPalavra` INT NOT NULL AUTO_INCREMENT,
  `palavra` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codPalavra`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ColetaLink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ColetaLink` (
  `codColeta` INT NOT NULL,
  `codLink` INT NOT NULL,
  PRIMARY KEY (`codColeta`, `codLink`),
  INDEX `fk_Coleta_has_Link_Link1_idx` (`codLink` ASC),
  INDEX `fk_Coleta_has_Link_Coleta1_idx` (`codColeta` ASC),
  CONSTRAINT `fk_Coleta_has_Link_Coleta1`
    FOREIGN KEY (`codColeta`)
    REFERENCES `pickglobe`.`Coleta` (`codColeta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Coleta_has_Link_Link1`
    FOREIGN KEY (`codLink`)
    REFERENCES `pickglobe`.`Link` (`codLink`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`PalavraLink`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`PalavraLink` (
  `codPalavra` INT NOT NULL,
  `codLink` INT NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`codPalavra`, `codLink`),
  INDEX `fk_Palavra_has_Link_Link1_idx` (`codLink` ASC),
  INDEX `fk_Palavra_has_Link_Palavra1_idx` (`codPalavra` ASC),
  CONSTRAINT `fk_Palavra_has_Link_Palavra1`
    FOREIGN KEY (`codPalavra`)
    REFERENCES `pickglobe`.`Palavra` (`codPalavra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palavra_has_Link_Link1`
    FOREIGN KEY (`codLink`)
    REFERENCES `pickglobe`.`Link` (`codLink`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`ListaExtensoesExtensao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`ListaExtensoesExtensao` (
  `codListaExtensoes` INT NOT NULL,
  `codExtensoes` INT NOT NULL,
  PRIMARY KEY (`codListaExtensoes`, `codExtensoes`),
  INDEX `fk_ListaExtesoes_has_Extensao_Extensao1_idx` (`codExtensoes` ASC),
  INDEX `fk_ListaExtesoes_has_Extensao_ListaExtesoes1_idx` (`codListaExtensoes` ASC),
  CONSTRAINT `fk_ListaExtesoes_has_Extensao_ListaExtesoes1`
    FOREIGN KEY (`codListaExtensoes`)
    REFERENCES `pickglobe`.`ListaExtensoes` (`codListaExtensoes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ListaExtesoes_has_Extensao_Extensao1`
    FOREIGN KEY (`codExtensoes`)
    REFERENCES `pickglobe`.`Extensao` (`codExtensao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pickglobe`.`PalavraListaPalavras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pickglobe`.`PalavraListaPalavras` (
  `codPalavra` INT NOT NULL,
  `codListaPalavras` INT NOT NULL,
  PRIMARY KEY (`codPalavra`, `codListaPalavras`),
  INDEX `fk_Palavra_has_ListaPalavras_ListaPalavras1_idx` (`codListaPalavras` ASC),
  INDEX `fk_Palavra_has_ListaPalavras_Palavra1_idx` (`codPalavra` ASC),
  CONSTRAINT `fk_Palavra_has_ListaPalavras_Palavra1`
    FOREIGN KEY (`codPalavra`)
    REFERENCES `pickglobe`.`Palavra` (`codPalavra`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palavra_has_ListaPalavras_ListaPalavras1`
    FOREIGN KEY (`codListaPalavras`)
    REFERENCES `pickglobe`.`ListaPalavras` (`codListaPalavras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


insert into TipoLista values(1, 'Nomes');
insert into TipoLista values(2, 'Palavras');
insert into TipoLista values(3, 'Palavras capitalizadas');
insert into TipoLista values(4, 'Palavras não capitalizadas');
