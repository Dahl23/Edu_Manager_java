DROP DATABASE IF EXISTS EMS;

CREATE DATABASE IF NOT EXISTS EMS;
USE EMS;
# -----------------------------------------------------------------------------
#       TABLE : ETUDIANTS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ETUDIANTS
 (
   ID_ETUDIANT BIGINT(4) NOT NULL  ,
   ID_UTILISATEUR BIGINT(4) NOT NULL  ,
   NOM VARCHAR(128) NOT NULL  ,
   MATRICULE VARCHAR(128) NOT NULL  ,
   EMAIL VARCHAR(128) NOT NULL  ,
   NIVEAU VARCHAR(128) NOT NULL  ,
   FILIERE VARCHAR(128) NOT NULL  
   , PRIMARY KEY (ID_ETUDIANT) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ETUDIANTS
# -----------------------------------------------------------------------------


CREATE UNIQUE INDEX I_FK_ETUDIANTS_UTILISATEURS
     ON ETUDIANTS (ID_UTILISATEUR ASC);

# -----------------------------------------------------------------------------
#       TABLE : ENSEIGNANTS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ENSEIGNANTS
 (
   ID_ENSEIGNANT BIGINT(4) NOT NULL  ,
   ID_UTILISATEUR BIGINT(4) NOT NULL  ,
   NOM VARCHAR(128) NOT NULL  ,
   EMAIL VARCHAR(128) NOT NULL  ,
   SPECIALITE VARCHAR(128) NOT NULL  
   , PRIMARY KEY (ID_ENSEIGNANT) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ENSEIGNANTS
# -----------------------------------------------------------------------------


CREATE UNIQUE INDEX I_FK_ENSEIGNANTS_UTILISATEURS
     ON ENSEIGNANTS (ID_UTILISATEUR ASC);

# -----------------------------------------------------------------------------
#       TABLE : EVALUATIONS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS EVALUATIONS
 (
   ID_EVALUATION BIGINT(4) NOT NULL  ,
   ID_ETUDIANT BIGINT(4) NOT NULL  ,
   ID_COURS BIGINT(4) NOT NULL  ,
   TYPE_EVALUATION VARCHAR(128) NOT NULL  ,
   NOTE DECIMAL(10,2) NOT NULL  
   , PRIMARY KEY (ID_EVALUATION) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE EVALUATIONS
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_EVALUATIONS_ETUDIANTS
     ON EVALUATIONS (ID_ETUDIANT ASC);

CREATE  INDEX I_FK_EVALUATIONS_COURS
     ON EVALUATIONS (ID_COURS ASC);

# -----------------------------------------------------------------------------
#       TABLE : COURS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS COURS
 (
   ID_COURS BIGINT(4) NOT NULL  ,
   LIBELLE VARCHAR(128) NOT NULL  ,
   SEMESTRE VARCHAR(128) NOT NULL  
   , PRIMARY KEY (ID_COURS) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : UTILISATEURS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS UTILISATEURS
 (
   ID_UTILISATEUR BIGINT(4) NOT NULL  ,
   NOM_UTILISATEUR VARCHAR(128) NOT NULL  ,
   MOT_DE_PASSE VARCHAR(128) NOT NULL  ,
   ROLE VARCHAR(128) NOT NULL  
   , PRIMARY KEY (ID_UTILISATEUR) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : APPRENDRE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS APPRENDRE
 (
   ID_ETUDIANT BIGINT(4) NOT NULL  ,
   ID_COURS BIGINT(4) NOT NULL  
   , PRIMARY KEY (ID_ETUDIANT,ID_COURS) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE APPRENDRE
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_APPRENDRE_ETUDIANTS
     ON APPRENDRE (ID_ETUDIANT ASC);

CREATE  INDEX I_FK_APPRENDRE_COURS
     ON APPRENDRE (ID_COURS ASC);

# -----------------------------------------------------------------------------
#       TABLE : ENSEIGNER
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ENSEIGNER
 (
   ID_COURS BIGINT(4) NOT NULL  ,
   ID_ENSEIGNANT BIGINT(4) NOT NULL  
   , PRIMARY KEY (ID_COURS,ID_ENSEIGNANT) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE ENSEIGNER
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_ENSEIGNER_COURS
     ON ENSEIGNER (ID_COURS ASC);

CREATE  INDEX I_FK_ENSEIGNER_ENSEIGNANTS
     ON ENSEIGNER (ID_ENSEIGNANT ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE ETUDIANTS 
  ADD FOREIGN KEY FK_ETUDIANTS_UTILISATEURS (ID_UTILISATEUR)
      REFERENCES UTILISATEURS (ID_UTILISATEUR) ;


ALTER TABLE ENSEIGNANTS 
  ADD FOREIGN KEY FK_ENSEIGNANTS_UTILISATEURS (ID_UTILISATEUR)
      REFERENCES UTILISATEURS (ID_UTILISATEUR) ;


ALTER TABLE EVALUATIONS 
  ADD FOREIGN KEY FK_EVALUATIONS_ETUDIANTS (ID_ETUDIANT)
      REFERENCES ETUDIANTS (ID_ETUDIANT) ;


ALTER TABLE EVALUATIONS 
  ADD FOREIGN KEY FK_EVALUATIONS_COURS (ID_COURS)
      REFERENCES COURS (ID_COURS) ;


ALTER TABLE APPRENDRE 
  ADD FOREIGN KEY FK_APPRENDRE_ETUDIANTS (ID_ETUDIANT)
      REFERENCES ETUDIANTS (ID_ETUDIANT) ;


ALTER TABLE APPRENDRE 
  ADD FOREIGN KEY FK_APPRENDRE_COURS (ID_COURS)
      REFERENCES COURS (ID_COURS) ;


ALTER TABLE ENSEIGNER 
  ADD FOREIGN KEY FK_ENSEIGNER_COURS (ID_COURS)
      REFERENCES COURS (ID_COURS) ;


ALTER TABLE ENSEIGNER 
  ADD FOREIGN KEY FK_ENSEIGNER_ENSEIGNANTS (ID_ENSEIGNANT)
      REFERENCES ENSEIGNANTS (ID_ENSEIGNANT) ;

