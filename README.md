# Projet AMT-Projet-2018

Pour pouvoir lancer l'image docker de ce projet, veillez cloner le repo dans un nouveau dossier, 
ouvrir une ligne de commande dans le fichier topology-amt, où ce trouve le fichier docker-compose.yml, 
et executer la commande:

```docker-compose up```

pour pouvoir lancer votre image docker.

Quand la construction de l'image soit finie, vous devez ouvrir un navigateur et vous connecter dans le 
serveur de payara avec le lien suivant:

```192.168.99.100:4848```

Pour vous connecter, vous pouvez le faire en mettant le nom d'utilisateur `admin` wt le mot de passe `admin`.
Après vous être connecté, veillez aller dans l'onglet Application à votre gauche. Cliquez sur le bouton Deploy
pour déployer votre application, sélectionnez le fichier `AMT-Project.war` dans le dossier war à la racine du 
projet et appuyez sur le bouton Ok en haut à votre droite.

Après le déploiment fait, vous pouvez vous connecter sur le lien suivant:

```192.168.99.100:8080/AMT-Project```

Si vous voulez avoir un nouveau fichier .war, ouvrez le projet dans IntelliJ, créez un serveur Payara qui se 
déploit dans le domain1 des domaines par défaut de payara, et sélectionnez l'artifact `AMT-Projet-2018:war exploded`.
Après ceci, veillez lancer les commandes clean and install de maven pour créer un nouveau dossier target, qui 
contiendra le fichier .war du projet, dans ce cas `AMT-Project.war`.

Si vous voulez vérifier l'état de la base de données, vous pouvez vous connecter à phpmyadmin avec le lien 
suivant:

```192.168.99.100:6060```