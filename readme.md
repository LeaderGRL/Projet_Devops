# Projet Devops
Ce projet comporte 3 taches qui seront déployé à l'aide de pipeline Jenkins

## Informations
* Le port utilisé lors de ce projet et le port 8081
* La partie CI et IAC sont dans le même dossier (Jenkins) parceque j'avais ommis le fait qu'il été demandé de les séparer.

## Pipeline CI
Cette pipeline permet de : 
* Créer un jar à partir d'un repository git
* Lancer les tests unitaires et d'intégration
* Déplacer le jar dans le workspace jenkins

## Etapes de réalisation de la pipeline CI
* Installer maven et le configurer pour le rendre utilisable
* Créer un nouveau job
* Ajouter les variables
* Réaliser le script
    * utiliser le maven configuré
    * clone le repository
    * Compiler à l'aide de maven
    * Réaliser les tests unitaire à l'aide de maven si la variable "SKIP" est fausse
    * Build à l'aide de maven
    * Réalisé les test d'intégrations à l'aide de maven si la variable "SKIP" est fausse
    * Déplacer le jar dans le workspace jenkis en lui donnant un nom et une version correspondant aux variables fournis

## Installation
Vous pouvez installer le projet en clonant le repository et lançant celui-ci à l'aide de docker.   
Rendez-vous sur Jenkins, séléctionnez le job "CI" et effectuer un build de celui-ci.   
Vous retrouvez un fichier .jar dans votre workspace si tout c'est bien passé.

## Pipeline IAC
Cette pipeline permet de :
* Déclarer une instance aws
* Déclarer une clef ssh qui sera automatiquement rapatriée dans l’instance.
* Déclarer un security group permettant l’ouverture au protocol ssh et aux ports 22/8080 de
notre application en entrée et vers tout le monde en sortie.
* Déclarer un 'cloud init' pour la création d’un user deploy et l’installation du paquet python si
nécessaire
* Détruire l'infrastructure tout juste créer :)

## Etapes de réalisation de la pipeline IAC
* Ajout du plugin terraform dans jenkins
* Configuration de terraform
* Ajout de terraform dans le dockerfile
* Réalisation d'un script terraform
    * Création d'une instance aws à l'aide des accés fournis
    * Réalisation d'un cloud init pour installer des packages
    * Récupération de la clés ssh
    * Réalisation du security group
* Test en local du script
* push du script sur la branch features
* Configuration de la pipeline jenkins : 
    * clone du repository contenant le script
    * Build et run le script à l'aide de commande terraform

    





