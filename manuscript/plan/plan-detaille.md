
# Table des matières

1.  [Définitions nécessaires](#orgae72e9d)
2.  [Formalisation des évènements liés à l&rsquo;application des opérations de modélisation](#org82279c0)
    1.  [Que sont les évènements, en quoi sont-ils liés aux opérations de modélisation ?](#org22f755b)
    2.  [Les évènements dans un graphe/une G-carte](#org8c0c704)
        1.  [Formaliser la création](#org94a9c8e)
        2.  [Formaliser la suppression](#orgbceed4c)
        3.  [Formaliser le non-changement](#orgf7fc7cf)
        4.  [Formaliser la modification](#org03c2ae7)
        5.  [Formaliser la scission](#org1f53302)
        6.  [Formaliser la fusion](#org349f1e7)
        7.  [(Formaliser le sans effet ?)](#org0bc7a59)
    3.  [Les évènements dans les règles de transformation de graphe](#org424203b)
    4.  [Les évènements dans les règles Jerboa](#org4b3cd36)
    5.  [Type (d&rsquo;orbite) d&rsquo;origine d&rsquo;un évènement](#org4074a82)
        1.  [Formaliser l&rsquo;origine d&rsquo;un évènement créé par une règle de transformation de graphe](#org25aecc9)
        2.  [Formaliser l&rsquo;origine d&rsquo;un évènement créé par une règle Jerboa](#orgb21435c)
3.  [Conception d&rsquo;un système de spécification paramétrique](#org3153287)
4.  [Des DAGS pour représenter l&rsquo;histoire d&rsquo;une cellule topologique](#orgce9d50f)
    1.  [Les origines pour un historique complet des cellules topologiques](#org50933d3)
        1.  [Combiner les orbites d&rsquo;origine et de suivi dans un DAG pour une représentation complète d&rsquo;un évènement entre deux applications de règles](#org2db9dd5)
    2.  [Des chemins pour plus de précision](#org8c5c43d)
        1.  [Formaliser l&rsquo;usage de chemins pour préciser une origine lorsque celle-ci n&rsquo;est pas directement liée à un nœud d&rsquo;accroche dans une règle](#org7dbb211)



<a id="orgae72e9d"></a>

# Définitions nécessaires

-   G-carte
-   Orbite
-   Règles de transformation de graphe
-   Règles Jerboa de transformation de graphe
-   Chemin (?)
-   Lexique pour les G-cartes
    -   brin, liaisons, orbites
-   Lexique pour les règles
    -   nœud, arcs, orbites


<a id="org82279c0"></a>

# Formalisation des évènements liés à l&rsquo;application des opérations de modélisation


<a id="org22f755b"></a>

## Que sont les évènements, en quoi sont-ils liés aux opérations de modélisation ?


<a id="org8c0c704"></a>

## Les évènements dans un graphe/une G-carte


<a id="org94a9c8e"></a>

### Formaliser la création


<a id="orgbceed4c"></a>

### Formaliser la suppression


<a id="orgf7fc7cf"></a>

### Formaliser le non-changement


<a id="org03c2ae7"></a>

### Formaliser la modification


<a id="org1f53302"></a>

### Formaliser la scission


<a id="org349f1e7"></a>

### Formaliser la fusion


<a id="org0bc7a59"></a>

### (Formaliser le sans effet ?)


<a id="org424203b"></a>

## Les évènements dans les règles de transformation de graphe


<a id="org4b3cd36"></a>

## Les évènements dans les règles Jerboa


<a id="org4074a82"></a>

## Type (d&rsquo;orbite) d&rsquo;origine d&rsquo;un évènement


<a id="org25aecc9"></a>

### Formaliser l&rsquo;origine d&rsquo;un évènement créé par une règle de transformation de graphe


<a id="orgb21435c"></a>

### Formaliser l&rsquo;origine d&rsquo;un évènement créé par une règle Jerboa


<a id="org3153287"></a>

# Conception d&rsquo;un système de spécification paramétrique

\*\*


<a id="orgce9d50f"></a>

# Des DAGS pour représenter l&rsquo;histoire d&rsquo;une cellule topologique


<a id="org50933d3"></a>

## Les origines pour un historique complet des cellules topologiques


<a id="org2db9dd5"></a>

### Combiner les orbites d&rsquo;origine et de suivi dans un DAG pour une représentation complète d&rsquo;un évènement entre deux applications de règles


<a id="org8c5c43d"></a>

## Des chemins pour plus de précision


<a id="org7dbb211"></a>

### Formaliser l&rsquo;usage de chemins pour préciser une origine lorsque celle-ci n&rsquo;est pas directement liée à un nœud d&rsquo;accroche dans une règle

