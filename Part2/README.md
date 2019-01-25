# Engine API

GET `/users`  returns all users

GET `/users/1213` returns user 1213

GET `/pointScales`  returns all pointScales

GET `/pointScales/1213` returns pointScale 1213

# Projet AMT-Projet-2018

## Auteurs: Jimmy Verdasca, Mika Pagani et Nathan Gonzalez Montes

Projet de Gamification pour le cours AMT.

## Lancement de l'image docker

Pour pouvoir lancer l'image docker de ce projet, veillez cloner le repo dans un nouveau dossier, 
ouvrir une ligne de commande dans le dossier topology-amt, situé dans le chemin /swagger/spring-server, où ce trouve le fichier docker-compose.yml, et executer la commande:

```docker-compose up```

pour pouvoir lancer votre image docker.

Après avoir lancer la commande, vous pouvez accéder au lien suivant pour voir l'API:

```http://localhost:8080/api/swagger-ui.html```
