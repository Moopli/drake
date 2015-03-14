# The Main Game State #

The Main Game State consists of a bunch of things:
  * The ActiveArea
  * The Scheduler
  * The mobs etc handling system
    * A direct handle on the player
  * the possibly extensive interfacing system
    * The Graphics Panes
      * The text interface

# The Main Game Loop #
Every iteration of the main game loop, the following things happen:

  * The Scheduler names a mob which has the opportunity to make a move.
  * The main game state makes the mob take its move.
  * The ActiveArea updates its bitmasks etc
  * The ActiveArea produces a graphical representation of the map state, given the surface to doodle it on and the position of the player
  * Everything is rendered.

When it's the player's turn to make a move:
  * The interface system kicks in, and the game waits for the player to move

# In-depth #
-- TODO -- in-depth explanation --