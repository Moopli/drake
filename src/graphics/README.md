The graphics folder will contain all code which handles the GUI 
(ascii -> drawing on screen). It will be built with an API for the rest of the 
code, so theoretically, we could swap out the entire Graphics handler for 
something else without affecting the program. Actually, this is probs the only 
necessary condition on the graphics handling -- that it be completely modular 
and separate from the game.
