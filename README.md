### Projet Calculatrice ###
Bienvenue dans cette calculatrice en JAVA.
Elle est multi-type (cf Section Suivante), et elle permet les rappels de valeurs(extension 3).
Elle est en Syntaxe Polonaise Inversé (RPN). Cette calculatrice a été
réalisée sur 'openjdk version "10.0.1"'.

### Types supportés ###
- Nombres Décimaux, avec les opérations addition (`+`),soustraction(`-`) , multiplication (`*`) et
la division entière (`/`);
- Booléen : Représentés avec les Valeurs `VRAI` et `FAUX` et les opérations négation (`NON`),
ou (`OU`) et et (`ET`);
- Ensemble : Avec la forme `{elt1,elt2,...,eltn}`, accompagnée des opérations union (`UNION`),
intersection (`INTER`) et différence (`DIFF`);

### Utilisation ###
Pour lancer la calculatrice, il suffit de faire la commande `./run`.

Après cette commande, vous vous retrouvez face à cela :
```
Bienvenue dans cette calculatrice
>
```
Il suffit alors de taper une valeur dont le type est supporté et c'est parti !
La calculatrice étant en RPN, n'oubliez de taper les arguments avant l'opération
demandée.
Pour quitter la calculatrice, il faut écrire la commande `exit`.
- Rappel de valeurs
Voici la liste des commandes, permettant de se rappeler d'anciennes valeurs :
	* `hist(n)` : Affiche la n-ième valeur tapée depuis le début de l'exécution et l'empile,
	* `pile(n)` : Affiche le n-ième élément de la pile et l'empile,
	* `!x` : Dépile et stocke la valeur de l'ancien sommet dans `x`,
	* `?x` : Affiche le contenu de `x` et l'empile.
Remarque : `n` peut être négatif et correspondant alors à la |n|-ième valeur en partant de la fin.
