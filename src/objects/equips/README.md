Basically, Items (objects which have a representation both on-ground and in 
inventory) would have a has-a relationship with a InventoryItem and some sort of 
MapItem.

Actually, this is as good a place as any to elaborate on how this game will 
hierarch objects. Generally, we want to avoid inheritance relationships -- 
nothing ever handles them well, we always end up wanting multiple inheritances, 
and everything goes down the fail. Now, for simple classes, where it's clear 
what should superclass what, that is, where there is a definite thread, and not 
some twisted graph, of what different classes need in their arsenals; then we 
can inherit. However, most major classes end up being a little too fat for that.

So, our solution: Important classes, which tie together several separate threads 
of functionality, can't somehow inherit from all of them. So, we inherit from 
none of them. Our monsters won't be bodies, that also have AI functionality, map 
movement functionality, and flavor. Neither will the be map-movers with bodies 
etcetera. They are just monsters; which tie together all of those 
functionalities. So, a monster class would inherit from nobody; but it'll have 
several objects, one a map-mover, one a body, one an AI, etc. To make the 
recognition of these objects easier for API purposes; an object can implement a 
HasAI interface (as an example); which provides a few standard methods for 
getting a handle on the monster's AI instance etc.


Now for a on-topic question (yeah the above section should be moved somewhere 
more pertinent) -- How to manage equipping?

Equips are by their nature tightly connected to body management, and item 
management. 

Body management should control defining what kinds of equips can go where.

Item management should control what kind of items are what kind of equips.

Item management should control "where" you items go when you are equipping 
them (if they leave your inventory at all - DCSS keeps them in inventory, which 
makes sense).

Apart from this there are gray areas:

Who controls whether you are getting effects? Item management perhaps, body 
management perhaps -- if a ring is on a finger cut of, does it still take 
effect?

We can let item management handle this -- the item-equip system check whether 
the body-parts equips attach to are functional; if not, no effect. Plain and 
simple; after all, the body already checks for itself whether parts function.

As for effects then -- we'd probably have a separate system for effects;  more 
on this later.
