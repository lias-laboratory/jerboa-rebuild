
# Table of Contents

1.  [Introduction](#org8d303d6)
    1.  [title](#org977ff33)
    2.  [slide 1 context](#org1a35d5b)
        1.  [animation 1](#orga6e0db5)
        2.  [animation 2](#orgb5d2bc1)
        3.  [animation 3](#orge0b5d50)
    3.  [slide 2 our approach](#orgd422680)
2.  [Transition](#orgf658b16)
3.  [Main concepts](#orgb9efc56)
    1.  [Generalized maps](#org68a0015)
        1.  [animation 1](#org3065adf)
        2.  [animation 2](#org4d34290)
        3.  [animation 3](#orgb95c02c)
        4.  [animation 4](#org9a31a5a)
    2.  [Transition](#org13d6452)
    3.  [Orbits](#orgaa1c740)
        1.  [animation 1](#orgd6c5ad4)
        2.  [animation 2](#orgfebeb8b)
        3.  [animation 3](#org015338f)
        4.  [animation 4](#org1e0a8c8)
        5.  [animation 5](#org34301cf)
    4.  [Transition](#orgb4d5484)
    5.  [Jerboa graph transformation rules](#org7abf24f)
        1.  [animation 1](#org3910bee)
        2.  [animation 2](#orga5eba4e)
        3.  [animation 3](#org2533483)
        4.  [animation 4](#orgb3e087a)
        5.  [animation 5](#org4662b43)
        6.  [animation 6](#org107506f)
        7.  [animation 7](#orgd823157)
4.  [Transition](#orged0788d)
5.  [Detecting topological events](#org7b6d4f5)
    1.  [Creation](#orgdc4aab3)
    2.  [Transition](#org7769251)
    3.  [Split](#org12d0e75)
        1.  [implicit](#orgd1dd1bb)
        2.  [transition](#org40eb0f0)
        3.  [explicit](#orgf53caaa)
        4.  [false positive split](#orga8879b9)
6.  [Transition](#orgf2117fd)
7.  [Other events](#org237e28b)
    1.  [Deletion](#org954918e)
    2.  [Merging](#orga0ee42d)
    3.  [Modification](#org480b59d)
    4.  [Non-modification](#orgf79715d)
8.  [Conclusion](#org2e108ad)



<a id="org8d303d6"></a>

# Introduction


<a id="org977ff33"></a>

## title

Today, I present a method to automatically detect the topological changes caused
by modelling operations on objects.

A topological change is an event which, for example, creates, splits, merges,
topological cells (faces, edges, vertices and so on) within an object.


<a id="org1a35d5b"></a>

## slide 1 context


<a id="orga6e0db5"></a>

### animation 1

For example, take the following object


<a id="orgb5d2bc1"></a>

### animation 2

and subdivide it.


<a id="orge0b5d50"></a>

### animation 3

Faces are split, vertices are created. Nowadays, all modelling APIs detect and
track topological events.

However, detecting these changes means performing either a dynamic check or dynamically.

In the dynamic method, the operations must first be applied to a given mesh, then this mesh is analyzed to list the events produced. Downside is, the more complex the object, the more expensive the event detection.

In the static method, a piece of code must be added to the operations and
exhaustively list all the events that will occur. Downside is, it makes more
work and there can be missing or error event detection.

Our approach does combine both static and dynamic detection in order to minimize
detection costs.


<a id="orgd422680"></a>

## slide 2 our approach

We propose to formalize the static detection of local events and automate this
process based on an automatic analysis of operations.

Doing so, reduces the cost of event detection and prevents the addition of code
to the operations.


<a id="orgf658b16"></a>

# Transition

I will start presenting the formal tools we use before continuing with our
contribution.


<a id="orgb9efc56"></a>

# Main concepts


<a id="org68a0015"></a>

## Generalized maps


<a id="org3065adf"></a>

### animation 1

A generalized map is a model allowing us to represent all the cells composing an
object. Consider the following object: a triangle and a quad sharing an edge
(the edge BC).


<a id="org4d34290"></a>

### animation 2

Firstly, the object is decomposed into faces connected together with a 2-link
(in blue). On the boundaries, the links are forming loops.


<a id="orgb95c02c"></a>

### animation 3

Secondly, faces are decomposed into edges connected together with 1-links (in
red).


<a id="org9a31a5a"></a>

### animation 4

Finally, the edges are decomposed into vertices connected together with 0-links
(in black).

At last, A G-map is a graph whose vertices are called darts and arcs are called
links.


<a id="org13d6452"></a>

## Transition

In generalized maps, sub-graphs represent cells.


<a id="orgaa1c740"></a>

## Orbits


<a id="orgd6c5ad4"></a>

### animation 1

For example, the face incident to dart &ldquo;a&rdquo; contains all the reachable darts
though 0- and 1- links as well as these links.


<a id="orgfebeb8b"></a>

### animation 2

Similarly, the edge incident to dart a contains these darts and these 0- and
2-links.


<a id="org015338f"></a>

### animation 3

This orbit is a valid 0,2 edge thanks to the loops.


<a id="org1e0a8c8"></a>

### animation 4

Here is the vertex incident to a with its darts and 1- and 2-links.


<a id="org34301cf"></a>

### animation 5

Orbit also generalize the notion of cells. For example, this face edge of
type 0.

An orbit is parameterized with a type &ldquo;o&rdquo; that can correspond to any set of
dimensions such as these.


<a id="orgb4d5484"></a>

## Transition

We are done with the objects&rsquo; formalism. I will now go on with the formalization
of the operations.


<a id="org7abf24f"></a>

## Jerboa graph transformation rules


<a id="org3910bee"></a>

### animation 1

Jerboa is the language we use to represent operations as graph transformation
rules

For example, we will work through the triangulation operation here which matches
the quad face and replaces it with a several triangles.


<a id="orga5eba4e"></a>

### animation 2

Here is the triangulation rule.
Its left side matches a sub-graph, the right side transforms it into a new one.


<a id="org2533483"></a>

### animation 3

The left side matches a <0,1> face, when the node n0 is mapped to dart a0, the
quad face is matched.

For your information, the gray parts remain untouched throughout the
transformation.


<a id="orgb3e087a"></a>

### animation 4

On the right, n0 is preserved and so are its darts. The node is rewritten from
0,1 to 0 \_. That is, the 0-links are preserved and the 1-links are deleted.


<a id="org4662b43"></a>

### animation 5

n1 is created and is a copy of the left hand side node n0.

n1 creates and matches as much darts as n0. However, in n1, 0 is rewritten into
an underscore and 1 is rewritten into a 2. Subsequently, n1&rsquo;s darts are
connected together with 2-links only.

nodes n1 and n0 are connected 1-to-1 with a 1-link. Subsequently, their
respective darts are connected 1-to-1 with 1-links.


<a id="org107506f"></a>

### animation 6

n2 is also a created node. Unlike n0, it is written as 1,2. Therefore, its darts
are connected with 1-links instead of 0-links and 2-links instead of 1-links.

The values written within nodes are called implicit links.

These links, between nodes, are called explicit links


<a id="orgd823157"></a>

### animation 7

While we can define orbits in a gmap, we can do so in rules. Here are the face
orbits incident to n0.

Notice that in this orbit, each node matches both 0- and 1-links, n0 matches 0
implicitly, 1 explicitly, n1 matches 1 and 0 explicitly and n2 matches 0
explicitly and 1 implicitly. Therefore the orbit is said to be complete.

Subsequently, the orbit matches complete faces in the gmap

Moreover, an orbit is said to be complete when each of its node matches either
implicitly or explicitly all the links described in an orbit type.

For example, on the left, the face orbit matches both 0 and 1 links.

Lastly, syntactic conditions guaranteeing that a G-map remains a G-map
after the application of a rule.


<a id="orged0788d"></a>

# Transition

Now we reach the heart of our method. I will start with a simple event : the
creation.


<a id="org7b6d4f5"></a>

# Detecting topological events


<a id="orgdc4aab3"></a>

## Creation

We continue with the same object and the same rule but this time we study the
creation of this <0,2> edge orbit incident to n1 and these edges.

This orbit only contains created nodes, that is the orbit is created.

Jerboa rules&rsquo; syntaxes imposes a created node matches all the possible
dimensions.

Thus this orbit is complete and entirely creates these edges.

All rules are pre-processed with such a syntactic analysis. In practice no more
computing is necessary for this event when applying this rule.


<a id="org7769251"></a>

## Transition

Now a more complex event, the split.


<a id="org12d0e75"></a>

## Split

There are two kind of splits: through implicit and explicit link.


<a id="orgd1dd1bb"></a>

### implicit

Once again, same rule, same object and we go back with the <0,1> face orbit.

In this example, we take interest into this (montrer) second implicit 1-link. On
the left, each node&rsquo;s second implicit link is either deleted or rewritten into
a 2-link.

Those 1-links (montrer à gauche) are deleted and you can see (montrer) that no
second implicit link connects darts with either 0- or 1-links in the object

Thus, this orbit (montrer à droite) matches several faces

Additionally, each new face comes from an edge of the former (one). This face has four
edges, hence the four faces we see here (montrer).


<a id="org40eb0f0"></a>

### transition

Now we have seen an implicit split, we will see an explicit split.


<a id="orgf53caaa"></a>

### explicit

We now consider a new object: a patch of quads; and a new rule. The rule
disconnects two face edges by cutting their explicit 2-link.

We take interest into this <0,2> edge orbit incident to n0.
As in previous slide, the orbit is complete.

Cutting the explicit 2-link explicitly splits the orbit in two (montrer règle).
Therefore, in the object, the edge is split and the 2-links have become loops
(montrer).


<a id="orga8879b9"></a>

### false positive split

As a last example, we keep the same object, the same rule but we take interest
into the <1,2> vertex orbits in the rule.

To split an edge means to also split their vertices. Here, the rule matches two
vertices, one incident to a0 (montrer) the other incident to b0 (montrer).

However, since none of the node matches a 1-link, the orbits are incomplete. As
a result, the matched vertices in the rule are only a0,a1 and b0,b1.

(montrer l&rsquo;objet de droite) This boundary vertex (montrer le sommet de bord) is
effectively split. This vertex at the center (montrer), however, is only
modified. As you can see, there still exist a 1-2-path in the object between a0
and a1.

Such situation requires a localized dynamic check to verify whether or not two
matched darts belong to the same orbit.


<a id="orgf2117fd"></a>

# Transition

We have seen how to detect two topological events, namely the creation and the
split, from Jerboa rules.


<a id="org237e28b"></a>

# Other events

We can do it for more even events.


<a id="org954918e"></a>

## Deletion


<a id="orga0ee42d"></a>

## Merging


<a id="org480b59d"></a>

## Modification


<a id="orgf79715d"></a>

## Non-modification


<a id="org2e108ad"></a>

# Conclusion

In order to automatically detect topological changes, we take advantage of
generalized maps to model objects and graph transformation rules to formalize
modelling operations.

These formalisms allow us to syntactically analyse rules instead of code. Furthermore, its prevents the need to add specific code instruction within operations.

Another advantage is the possibility to pre-compute the rules for a static
detection and dynamically check a localized sub-graph when needed.

