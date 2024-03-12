# Introduction
L'affichage d'une projection d'une scène efficacement et rapidement, peu importe la dimension, est une problématique récurrente des outils graphiques largement répandus dans le domaine
informatique.  
Pour ce faire, partitionner la scène et créer un arbre reprenant des sous-espaces et leurs informations (arbre BSP, que nous définirons plus en détail dans les premières pages) est une
solution intéressante. Pour ce qui est de l'affichage en tant que tel, l'algorithme du peintre, dont nous expliquerons également le fonctionnement dans ce rapport dans la partie dédiée
aux algorithmes, permet de définir efficacement, grâce à l'arbre BSP, quels sont les éléments appartenant au premier, second et dernier plan, depuis un point de vue. Cette stratégie
permet ainsi l'affichage de ce que pourrait voir un oeil dans la scène en fonction de la direction de son regard ainsi que de son champ de vision (Nous parlerons tout au long ce
rapport de FOV, Field Of View, pour y faire référence).  
Dans ce projet, nous avons donc implémenté l'arbre BSP et l'algorithme du peintre, ainsi que différentes heuristiques permettant de diversifier les façons de diviser l'espace, afin de
représenter des projections 1D de ce que voit un oeil placé dans des scènes en 2D constituées de segments non sécants. Nous avons également comparé ces différentes heuristiques et leur
impact sur l'efficacité des résultats calculés par la stratégie décrite ci-dessus.

# Utilisation
Le projet a été construit en utilisant Gradle pour Java. Nous vous conseillons de regarder la [documentation](https://gradle.org/guides/) si vous ne savez pas comment l'utiliser.

# Rapport
[Rapport](Rapport.pdf)

# Auteurs
Emilien Lemaire  
Nathan Amorison
