
# Table of Contents

1.  [Introduction](#orga8cd80f)
    1.  [title](#org2e16d53)
    2.  [slide 1](#orgf497dce)
        1.  [animation 1](#orgd89df5a)
        2.  [animation 2](#orgb368f72)
        3.  [animation 3](#org06ffcc8)
    3.  [slide 2](#org88e23c1)
2.  [Transition](#orga7c1223)
3.  [Main concepts](#org39040b9)
    1.  [Generalized maps](#org07f217a)
        1.  [animation 1](#org2906a29)
        2.  [animation 2](#org9530abb)
        3.  [animation 3](#orgff74b3a)
        4.  [animation 4](#org68ab3a1)
    2.  [Transition](#org33d1d24)
    3.  [Orbits](#org6a55229)
        1.  [animation 1](#orge66b0c2)
        2.  [animation 2](#org213c159)
        3.  [animation 3](#org3bc25cf)
        4.  [animation 4](#org9049d77)
        5.  [animation 5](#orgd3a2f28)
    4.  [Transition](#org4ddc472)
    5.  [Jerboa&rsquo;s graph transformation rules](#orgdb5cebb)
        1.  [animation 1](#orgb2295e9)
        2.  [animation 2](#org2f541de)
        3.  [animation 3](#orgba6a319)
        4.  [animation 4](#orgea93dce)
        5.  [animation 5](#orgd02996e)
        6.  [animation 6](#org54885b2)
        7.  [animation 7](#org436e457)
4.  [Transition](#org79a460e)
5.  [Detecting topological events](#orgd8265fd)
    1.  [Creation](#orgb9f03dc)
    2.  [Transition](#org92612a1)
    3.  [Split](#org978452c)
        1.  [implicit](#org48c0062)
        2.  [explicit](#org96535c7)
        3.  [false positive split](#org1fdf19c)
6.  [Transition](#org1125e28)
7.  [Other events](#org746cdca)
    1.  [Deletion](#orgdd7f8a6)
    2.  [Merging](#org32fa308)
    3.  [Modification](#org3bd2893)
    4.  [Non-modification](#orgfcb0aa9)
8.  [Conclusion](#orgcedc473)



<a id="orga8cd80f"></a>

# Introduction


<a id="org2e16d53"></a>

## title

Today, I present a method to automatically detect the topological changes caused
by modelling operations on objects.

A topological change is an event which, for example, creates, splits, merges,
topological cells (faces, edges, vertices and so on) within an object.


<a id="orgf497dce"></a>

## slide 1


<a id="orgd89df5a"></a>

### animation 1

For example, take the following object


<a id="orgb368f72"></a>

### animation 2

and subdivide it.


<a id="org06ffcc8"></a>

### animation 3

Faces are split, vertices are created.

Detecting these changes is performed dynamically, in other words the operations
must first be applied to a given mesh, then this mesh is analysed to list the
events produced.

In addition, the more complex the object, the more expensive the event
detection.

Nowadays, all modelling APIs detect and track topological events.


<a id="org88e23c1"></a>

## slide 2

To answer these issues, we propose to formalize the static detection of local
events and automate this process based on an automatic analysis of operations.


<a id="orga7c1223"></a>

# Transition

First, we need to formalize the objects using generalized maps.

Second, we need to formalize the operations with graph transformation rules.


<a id="org39040b9"></a>

# Main concepts


<a id="org07f217a"></a>

## Generalized maps


<a id="org2906a29"></a>

### animation 1

A generalized map, or gmap, is a model allowing us to represent all the cells
composing an object. Consider the following object: a triangle and a quad sharing
an edge (the edge BC).


<a id="org9530abb"></a>

### animation 2

Firstly, the object is decomposed into faces connected together with a 2-link
(in blue). Where borders are not shared, each link forms a loop.


<a id="orgff74b3a"></a>

### animation 3

Secondly, faces are decomposed into edges connected together with 1-links (in
red).


<a id="org68ab3a1"></a>

### animation 4

Finally, the edges are decomposed into vertices connected together with 0-links
(in black).

At last, A G-map is a graph whose vertices are called darts and arcs are called
links.


<a id="org33d1d24"></a>

## Transition

In a G-map, orbits can represent all sort of cells and more.


<a id="org6a55229"></a>

## Orbits

Given a set &ldquo;o&rdquo; of dimensions (also known as orbit type), an orbit is the
sub-graph of a g-map incident to a dart for which all links belong to &ldquo;o&rdquo;.


<a id="orge66b0c2"></a>

### animation 1

For example, the face incident to dart &ldquo;a&rdquo; contains darts &ldquo;a&rdquo; to &ldquo;h&rdquo; and the 0-
and 1- links that connects them.


<a id="org213c159"></a>

### animation 2

The edge incident to dart a contains these darts these 0- and 2-links.


<a id="org3bc25cf"></a>

### animation 3

This orbit is a valid edge thanks to the loops.


<a id="org9049d77"></a>

### animation 4

Here is the vertex incident to a with its 1- and 2-links.


<a id="orgd3a2f28"></a>

### animation 5

Orbits also represent more specific topology such as this face edge.


<a id="org4ddc472"></a>

## Transition

Now that we have formalized the objects, we formalize the operations with
Jerboa&rsquo;s graph transformation rules.

Jerboa is a rule-based modelling software we use.


<a id="orgdb5cebb"></a>

## Jerboa&rsquo;s graph transformation rules


<a id="orgb2295e9"></a>

### animation 1

Let&rsquo;s work through the triangulation rule


<a id="org2f541de"></a>

### animation 2

Here is the rule. See, it has two members. The left side matches a sub-graph out
of a G-map. The right side transforms it into a new one. Each member contains
nodes to match sub-graphs.


<a id="orgba6a319"></a>

### animation 3

Node n0, on the left, has labels 0 and 1. As a hook node, when mapped to a0, it
matches the quad face.


<a id="orgea93dce"></a>

### animation 4

On the right, n0 is preserved and its only label is 0. Therefore the darts are
preserved, the 0-links too, however, 1-links are deleted.


<a id="orgd02996e"></a>

### animation 5

For consistency purpose a created node is a copy of the hook node. Here, n1 is
created and is a copy of n0. It creates as many darts as n0 matches.

Due to a rewriting of its labels, there is no 0-link between n1&rsquo;s darts. Also,
1-links are replaced by 2-links. The green label matching green links is called
an implicit link.

The 1-link between n0 and n1 connects n0&rsquo;s darts to n1&rsquo;s darts with 1-links.
This link matching the magenta ones in the sub-graph is called an explicit link.


<a id="org54885b2"></a>

### animation 6

n2 is also a created node. Unlike n0, its implicit links are 1,2. Therefore, its
darts are connected with 1-links instead of 0-links and 2-links instead of
1-links.


<a id="org436e457"></a>

### animation 7

While we can define orbits in a gmap, we can do so in rules. Here are the face
orbits incident to n0 and their matches before and after the application.

Moreover, an orbit is said to be complete when each of its node matches either
implicitly or explicitly all the links described in an orbit type.

For example, on the left, the face orbit matches both 0 and 1 links.

Lastly, there are syntactic conditions guaranteeing that a G-map remains a G-map
after the application of a rule.


<a id="org79a460e"></a>

# Transition

Now we reach the heart of our method. I will start with a simple event : the
creation.


<a id="orgd8265fd"></a>

# Detecting topological events


<a id="orgb9f03dc"></a>

## Creation

Here, the edge orbit only contains created nodes therefore the orbit is created.

By Jerboa&rsquo;s syntactic conditions a created node matches all the possible
dimensions and a created orbit is necessarily complete.

Therefore, since the edge orbit created in the rule is complete, the edges it
matches in the gmap are created.

When starting a modeler, all rules are pre-processed with such a syntactic
analysis. This allows a static detection of all occurring events at the moment a
rule is applied.


<a id="org92612a1"></a>

## Transition

Now a more complex event, the split.


<a id="org978452c"></a>

## Split

There are two kind of splits: implicit and explicit.


<a id="org48c0062"></a>

### implicit

In the right-hand side of the rule, the face orbit&rsquo;s second implicit links are
either deleted or rewritten from 1 to 2. Therefore, the orbit is split
through implicit 1-links in the rule.

The right-hand side matches exactly four faces because there is neither 0- nor
1-links to connect them (otherwise there wouldn&rsquo;t be any split).

Additionally, each new face comes from an edge of the former (one). This face has four
edges, hence the four faces we see here.


<a id="org96535c7"></a>

### explicit

Now consider the rule that disconnects two face edges by cutting their 2-links
and a new object being a patch of quads.

Here we expect to split the edge incident to a0 by cutting its 2-links

In the right-hand side, the explicit 2-link between n0 and n1 is replaced by one
2-loop for each of them. The orbit is split in two and, therefore, the matched
edge in the gmap is also split in two.

Since the edge orbit is complete in the rule, no further verification is needed.


<a id="org1fdf19c"></a>

### false positive split

Now, for a vertex orbit, we may expect two splits.

Yet, the orbit is incomplete, all of its nodes miss a 1-link.

See, even though the vertex on the right is split, the one at the center
is only modified.

Such situation requires a localized dynamic check to verify whether or not two
matched darts belong to the same orbit.


<a id="org1125e28"></a>

# Transition

We have seen how to detect two topological events, namely the creation and the
split, from Jerboa&rsquo;s rules.


<a id="org746cdca"></a>

# Other events

We can do it for more even events.


<a id="orgdd7f8a6"></a>

## Deletion


<a id="org32fa308"></a>

## Merging


<a id="org3bd2893"></a>

## Modification


<a id="orgfcb0aa9"></a>

## Non-modification


<a id="orgcedc473"></a>

# Conclusion

In order to automatically detect topological changes, we take advantage of
generalized maps to model objects and graph transformation rules to formalize
modelling operations.

These formalisms allow us to syntactically analyse rules instead of code. Furthermore, its prevents the need to add specific code instruction within operations.

Another advantage is the possibility to pre-compute the rules for a static
detection and dynamically check a localized sub-graph when needed.

