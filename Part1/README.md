# Projet AMT-Projet-2018

## Auteurs: Jimmy Verdasca, Mika Pagani et Nathan Gonzalez Montes

Projet de Gamification pour le cours AMT.

## Lancement de l'image docker

Pour pouvoir lancer l'image docker de ce projet, veillez cloner le repo dans un nouveau dossier, 
ouvrir une ligne de commande dans le fichier topology-amt, où ce trouve le fichier docker-compose.yml, 
et executer la commande:

```docker-compose up```

pour pouvoir lancer votre image docker.

Après le déploiment fait, vous pouvez vous connecter sur le lien suivant:

```http://192.168.99.100:8080/AMT-Project```

## Création d'un nouveau fichier .war

Si vous voulez avoir un nouveau fichier ".war", ouvrez le projet dans IntelliJ, créez un serveur Payara qui se 
déploit dans le domain1 des domaines par défaut de payara, et sélectionnez l'artifact `AMT-Projet-2018:war exploded`.
Après ceci, veillez lancer les commandes clean and install de maven pour créer un nouveau dossier target, qui 
contiendra le fichier .war du projet, dans ce cas `AMT-Project.war`.

Le fichier ".war" se trouve dans le dossier de l'image payara dans les dossier depuis la racine: ```/Part1/images/payara```
Vous pourrez modifier ce fichier et relancer la commande: ```docker-compose up --build``` pour reconstruir l'image avec le nouveau fichier ".war".

## Base de données après déploiment

Si vous voulez vérifier l'état de la base de données, vous pouvez vous connecter à phpmyadmin avec le lien 
suivant:

```http://192.168.99.100:6060```