
# Projet: Testez vos développements Java

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up

## Ajouts
*   Dans la classe `ComptabiliteManagerImpl` de la couche business, implémentation de la méthode `addReference()`
*   Dans l'interface `ComptabiliteManager`, ajout des méthodes `insertSequenceEcritureComptable()` et `updateSequenceEcritureComptable()`, implémentation de ces dernières dans `ComptabiliteManagerImpl`
*   Dans l'entité `SequenceEcritureComptable`, ajout de l'attribut `journalCode` avec son getter et son setter
*   Dans la couche consumer, ajout du RowMapper `SequenceEcritureComptableRM`
*   Dans l'interface `ComptabiliteDao`, ajout de la méthode `getSequenceByCodeAndAnneeCourante()`, implémentation de cette dernière dans `ComptabiliteDaoImpl` et ajout de la requête correspondante `SQLgetSequenceParCodeEtAnneeCourante` dans le fichier `sqlContext.xml`
*   Dans l'interface `ComptabiliteDao`, ajout des méthodes `insertSequenceEcritureComptable()` et `updateSequenceEcritureComptable()`, implémentation de ces dernières dans `ComptabiliteDaoImpl` et ajout de la requête correspondante `SQLinsertSequenceEcritureComptable` et `SQLupdateSequenceEcritureComptable` dans le fichier `sqlContext.xml`
*   Configuration des tests d'intégration de la couche consumer à l'aide des profils maven dans le dossier `test-consumer`
*   Mise à jour du POM parent pour l'analyse de la couverture du code dans SonarQube et le reporting des tests.




## Correctifs

*   Dans l'entité `EcritureComptable`, correction de la méthode `getTotalCredit()` qui utilisait la méthode `getDebit()` au lieu de `getCredit()` dans le calcul du total
*   Dans l'entité `EcritureComptable`, correction de la méthode `isEquilibree()` en utilisant `compareTo()` au lieu d`equals()`
*   Dans l'entité `EcritureComptable`, correction de l'annotation @Pattern sur l'attribut `reference` pour respecter le bon format (exemple: AC-2016/00001`)
*   Dans le fichier `sqlContext.xml`, correction  de la requête `SQLinsertListLigneEcritureComptable`. Une erreur de syntaxe à cause de l'absence d'une virgule corrompait l'execution.
*   Dans la classe `ComptabiliteManagerImpl`, correction de la méthode `updateEcritureComptable()`. Ajout de la ligne `this.checkEcritureComptable(pEcritureComptable)` dans le but de verifier les règles 5 et 6 lors de l'update.
*   Dans la classe `SpringRegistry` des couches business et consumer, adaptation du chemin de la variable `CONTEXT_APPLI_LOCATION` vers `testContext.xml` figurant dans le dossier ressources et contenant l'ensemble des beans nécessaires au chargement du contexte Spring
*   Correction de divers code smells relevés par SonarQube Scanner


## Jenkins
La documentation de la configuration du serveur d'intégration continue Jenkins se trouve dans le dossier doc.

