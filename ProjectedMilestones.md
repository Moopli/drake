# Projected Milestones #

## The Metaplan ##
In general, systems will be prototyped in python first to get general logic down; and then a port to java for integration into the rest of the project.

An overly simplified design flow would look like this:
idea -> python -> basic spec -> dirty java code -> improved spec -> integrated java code -> final documentation
Note that as issues with the code arise, we could simply go back a step or two, and iterate.

As such, most milestones would have to be reached earlier than stated for the python version; so the java code is ready in time.

As for version numbers, milestones, and dates:
  * Version 1.0.0 shall represent the version when all core systems are together; and the game is _sufficiently balanced to be playable_. in other words, the game at version 1.0.0 should be releasable without jeopardizing our reputations.
  * Therefore, version 0.8.0 or so would be when the game engine is version-1.0.0-complete; so all that's left by then would be assets and content. if we take the hyper-procedural route, which we could, version 0.8.0 would instead be when all non-asset-producing engine code is done.
  * Milestone dates, of course, would have to be adjusted based on local conditions (which is why we're using version numbers for milestones in the first place). After all, it'll only be done when it's done.

## The Plan ##

### 0.0.1 -MET- ###
This milestone is reached once we decide on a (at least temporary) directory structure, get local hg repositories set up, have the spec ready, and are ready to start coding.

### 0.0.2 -MET- ###
For version 0.0.2 we need a few python prototypes:
  * The graphics UI handling -- a "surface" of sorts, which is but a 2D array of colored chars with colored backgrounds; which the graphics handling draws onto a pane. With this handler, the rest of the game draws to an ascii buffer, simplifying graphics greatly.
    * The handler must assign non-intersecting rectangles for different UI tasks (eg, one rectangle for the map, another for the minimap, another for the inventory, etc)
    * This could be done by allowing ASCII-Char surfaces to nest; so the surface for the main UI would contain surfaces for minimap, map, inventory etc ASCII-Char surfaces.
  * Either separate (in-terminal) or built on the graphics handling -- a primitive room with @ player walking around

**Code semi-done for these, we just needs the 0.0.1 milestone first**

### 0.0.3 -MET- ###
For version 0.0.3, we'll prototype up a few more systems:
  * SpellBinder, yaay: Perhaps for hotseat PvP, or even with a basic AI. does not have to implement all spells, but once the SpellBinder is made, the spells will come easily -- this plan has been annulled
  * Body Plans and bodypart-based damage: Read up on Dwarf Fortress modding, loads of material for designing a body plan system.
  * Crude map generators -- constructing ascii map files with one program, exploring them with an expanded room-explorer


### 0.1.0 -MET- ###
The "first Working Version"

  * Basic Movable Character with melee "walk into" attack
  * Basic Monsters with walk into attack (actually, even basiccer would be okay)
  * Enough Map generation for a dungeon with monsters (and a player)
  * Basic Menus

### 0.1.5 ###
By version 0.1.5, all of the systems implemented in 0.1.0 should be relatively bug-clean -- remember: fail fast, fail hard, that way we catch and fix errors. We also need more content, so, perhaps, a few different kinds of material with realistic values, bodies with multiple parts, and monsters properly created from file.

The map generation also needs to be cleaned over, to make proper use of Brushes, Predicates, and Painters. The generator could load a config specifying the map size, split rate, and information pertaining to which Brushes, Predicates, and Painters to use.

Finally, the user interface has to be cleaned up -- it should look nice (and work too).

### 0.2.0 ###


### 0.5.0 ###


### 0.8.0 ###
By version 0.8.0, practically all engine development should be complete. The only engine work left should only include content-generation code, minor additional features, and of course, inescapable bugfixing. After 0.8.0, the brunt of development would rest on making content, which, of course, in the case of a roguelike still involves lots of coding.



## To be Scheduled ##
### For Prototyping ###
  * Flavor handling -- something simple which attaches flavor to stuff (names and descriptions, quotes, backstories)
  * Inventory -- a simple "bag of holding", with player commands to pick up, drop, examine, etc
  * Items -- bundle a flavor-handler, a map-object, and an inventory-object together
  * Effect/buff system (separate from spellcasting system, so items etc can have effects too
  * Memory -- player remembers names of things, flavor, etc

### For Inclusion ###
(note -- features migrate from prototyping list to here)

### For Content and Testing ###
(after inclusion, content, and fixing the code!)
  * Map Object -- with an accessible list of things sitting on the map
  * Action Scheduler -- when/how often things can move (see TimeReckoning)
  * Bodies -- body parts attached together, which can be forcibly separated, and some graph theory (a hand can only be controlled if it is still connected to the head; some essential organs must be connected to avoid certain death)
    * This is actually included right now (well, the "is functioning" algorithms, but no real content)
  * Mobs
  * Material Damage
  * A great idea borrowed from Dwarf Fortress (and extended): A "Soul" object; which basically bundles up a bunch of stuff:
    * A name
    * A personality
    * Conversation
    * Memory
      * Likes/Dislikes for particular types of thing (like, say, "I hate rats"), particular instances of things (like, say, "I like Joe"), places ("I like that hill") and any other abstract somethingorother which the soul is capable of distinguishing from other somethingorothers (perhaps "I like sunsets") -- including intersections perhaps ("I like sunsets on that hill").
      * Past events ("I saw a sunset on that hill..."), and emotional responses from them ("...and I was happy").
    * A creature with a soul could then act based on emotions with an AI (not part of the soul) that queries the soul for how it's feeling about stuff, can talk to other stuff (like a singing sword that lets you know when it's scared)
  * These soul ideas would need a nice NLP writer to transcribe thoughts into English for user interaction -- a basic "list topics on my mind" interface is fine for debugging, but not the eventual user!