# Introduction #

Map generation and editing is a difficult thing -- we need to be able to do certain things to maps, we need to know where to do _different_ things (to keep maps heterogenous), and we want to repeat as little code as possible for the related tasks of "do this stuff here" and "do this other stuff over there" etc. for this purpose we will define three kinds of object:
  * The Brush -- given a location on a map, a brush does something to the map at that location. Perhaps it places a particular monster, perhaps it places an entrance to a dungeon, perhaps it makes a lake (even applying it's own sub-brushes and so on). Each brush does something different; and hopefully we'll develop a library of different brushes for all of our needs.
  * The Predicate -- given a location on a map, a predicate checks some condition; and gives us a boolean result (satisfies/does not satisfy). For example, we might have a predicate to check whether a location is this far away from water; or whether this place would be the perfect place for a gnoll to call home (actually, gnolls are notoriously not that picky; they'll take any old darkened corner; so perhaps a better predicate would be "good enough for a gnoll to call home").
  * The Painter -- given a brush and a predicate, the Painter takes that brush and applies it to a bunch of places that satisfy the predicate. Perhaps it picks three random locations. Or perhaps it tries all locations reachable from point (x,y) by a grue (self-respecting grues are notoriously light-averse, so this calculation isn't trivial). Or perhaps it takes a predicate, and inverts all results. Whatever. As long as it does _something_ (although that is not strictly necessary either).

your job as a dungeon-maker would be to build a dungeon interesting enough for these sorts of choices to matter, and then use Painters with certain Brushes and Predicates to make your dungeon even more interesting.


# Brushes #
Here follows a list of Brushes, either implemented or yet to be.
### implemented ###

### unimplemented ###
  * placeMonster
  * makeThroneRoom
  * dropTacticalNuke



# Predicates #
Here follows a list of Predicates, either implemented or yet to be.
### implemented ###

### unimplemented ###
  * isThisCloseToWater
  * hasTemperatureBetween
  * hasPowerLevelOverNineThousand




# Painters #
Here follows a list of Painters, either implemented or yet to be.
### implemented ###

### unimplemented ###
  * tryAllInArea(area, etc)
  * doXTimes(x, etc)
  * tryXTimes(x, etc)
  * floodFillAll(start location, etc)
  * doXClosestTo(x, location, etc)
  * brownianWalk(steps, start location, etc)