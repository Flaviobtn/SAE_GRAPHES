# Rapport individuel – Flavio BOUTON – SAE 2.02
## Travail individuel
Dans ce projet, j'ai personnellement développé l’ensemble des fonctions Java, à l’exception de celle chargée de convertir un fichier texte en JSON. Cela inclut la mise en œuvre des fonctions de calcul de distance, ainsi que d’autres opérations sur les graphes, comme la détermination des collaborateurs.

J’ai également contribué à l’élaboration des algorithmes de calcul de distance entre les sommets du graphe. Cela m’a permis d’approfondir les différentes approches possibles et de comparer leur efficacité. Par exemple, pour la fonction collaborateursProches, j'avais d’abord opté pour une version récursive. Mais au final, une version non récursive s’est révélée moins coûteuse. J’ai donc retenu cette dernière.

L’une des principales difficultés rencontrées a été la compréhension du concept de centralité dans un graphe. Ce n’était pas un concept que j’avais abordé en profondeur auparavant, et j’ai dû faire des recherches et demander l’avis de camarades de classe sur le sujet.

## Nouvelles notions apprises et utilisation des compétences acquises
### Nouvelles notions apprises
Bien que je ne l'aie pas utilisée directement, j'ai découvert dans cette SAE la bibliothèque Gson, qui permet de manipuler des données au format JSON en Java.

### Utilisation des compétences acquises
J’ai analysé différentes implémentations possibles de la fonction collaborateursProches, notamment en comparant une version récursive et une version plus optimisée. J’ai évalué laquelle des deux offrait les meilleures performances en temps d’exécution et en consommation mémoire, puis j’ai choisi en fonction de ces deux critères.

Je n’ai pas formellement comparé plusieurs algorithmes de tri ou de recherche dans ce projet.

Je n’ai pas utilisé d’outils mathématiques spécifiques tels que des matrices d’adjacence ou des méthodes de calcul formel. Cependant, l’utilisation de bibliothèques comme JGraphT m’a permis d’implémenter certains de ces concepts, notamment tout ce qui est en rapport avec les chemins et les distances.