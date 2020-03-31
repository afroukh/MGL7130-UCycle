Fait par :
-	Sid Ahmed Boularas
-	Elyes Bejaoui
-	Marouane Afroukh

![Image description](https://i.ibb.co/VxHCwkH/image.png)





Bonjour, 
Ci-dessous est une courte description de l’application :
-	L’application est constituée de 3 menus principales : Home, Profile et Favoris
Dans ce livrable on a développé les modules d’ajout et de consultation des annonces puisqu’Ils constituent les fonctionnalités les plus importantes dans l’application.
Cette dernière utilise Firebase comme backend pour persister les annonces ainsi que leurs photos.
Fonctionnalités implémentés:
-	Les filtres des catégories (3) : sont récupérés depuis le backend, permettent de filtrer les annonces.
-	Le choix d’une ville depuis la liste déroulante (1) : cette liste est remplie depuis le backend, mais le filtre n’est pas encore implémenté.
-	La Recherche textuelle (2) : permet de trouver des annonces par leurs titres (non encore implementée).
-	Liste des annonces (4) : en cliquant sur une annonce on peut consulter son détail récupérer depuis le backend
-	Le bouton + (5) qui permet d’afficher l’écran de publication d’une annonce avec sa photo.
-	Dans l’écran d’ajout on peut récupérer la ville actuelle à partir du capteur GPS
Fonctionnalité non encore implémentée :
-	Mettre une annonce en favoris, et consulter la liste des favoris.
-	Gérer le profil de l’utilisateur.
-	Les filtres manquants.

Bugs :
La liste des annonces s’affiche juste lors du premier chargement de l’application, même si le filtre « All » utilise la même méthode du premier chargement, il retourne toujours nulle, ce qui cause aussi une bug dans la rotation, les autres filtres fonctionnent bien.

