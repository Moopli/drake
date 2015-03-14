# Introduction #

This page deals with our system (or rather, our interpretation on Dwarf Fortress's) for quantifying damage. Quite simply, HP numbers are not good enough. At heart, all monsters (and other stuff which can take damage) has a body of some sort. In the case of the human and most other chordates, this body includes a brain, heart, liver, stomach, spleen, notochord, mouth, etc. A tetrapod also has a distinct neck, limbs, possibly a tail (as an appendage distinct from the torso), and of course the same host of internal organs.

Clearly, there is the need to characterize the body of different creatures; and to tell whether the body (or any part thereof) is still alive. Here are a couple ideas to solve the problem, and in the end a way to abstract the details for the purposes of swinging an axe.

# The body as Graph #
The body of any creature can be considered as a graph of connections -- the head is connected to the neck is connected to the torso is connected to the thigh is connected to the calf is connected to the foot is connected to the toe. The torso contains the heart and lungs and liver and stomach and nerves and blood vessels and so on. The head contains the brain and eyes and nose and ears and mouth and nerves and blood vessels and so on. You may think the inclusion of blood vessels and nerves and the nose is unnecessary. Nay, thou art false. Here are some ideas on how a body (the example here being a human body, not all of us are intimately familiar with the inner workings of orcish homeostasis) would function in-game:

### Is it Still Alive? ###
Let's say our example human has a hand (which of course it does, two, last I counted). We want to know whether this hand is able to function (that is, do what a hand does -- hold something). Well, everything needs a connection to working blood vessels to get food, release waste, and whatnot, so that will be our first check:
  * Does body-part have a functioning blood-connection? That is, is the plumbing hooked up to a heart and whatnot?

With that out of the way, on to the other questions. The other connective-network we mentioned above was the nervous system -- for a hand to not just sit limply (alive, but unresponsive), it needs a nerve connection to the brain (or any other center of control). So that's question two:
  * Does body-part have a functioning nervous connection to a nervous control center?

These two checks are simple enough to floodfill from the heart(s) and nervous command center(s) to see what bits and pieces are still attached. But wait, you'll say -- a hand also needs oxygen, and waste removal through the kidneys, and a supply of immune cells, doesn't it? Yes, but all of these flow through the circulatory system -- as long as a body-part is connected to a brain, and the same heart as all that necessary stuff, it's fine. Actually, the test can be simplified even further -- blood vessels go everywhere nerves go; so all a hand needs to know is whether it is connected to a functioning brain; and if not, whether it's still on life-support.

We can take the simplifying step a bit farther -- if we assume that nerves and blood vessels permeate all body-arts; we can forgo checking whether there is a path through the right infrastructure; and just check whether there is a path.

# Bodily Functions #
As we saw above, different body parts (organs, or whatever) do different things. some systems vital, others not so much (well, a hand and an eye are both vital, just on a different layer of abstraction). We can say that each body-part has different functions -- all body parts have nerve-connections and blood vessels, a hand has grasping, a foot is ambulatory, a stomach digests, an eye sees, a nose smells (yes, smells -- smelling is the primary way our monsters will pathfind), and so on. In many cases, there will be a bunch of body-parts which perform a function. In such cases, we have to go case-by-case. While the entire digestive system must be functional to eat, only one eye is needed to see, only one grasper to hold, only one ear to hear, only one vestibular organ to keep balance. Of course, in these cases where one is sufficient, the number available affects how well the creature functions. Two eyes see better than one, seven tentacles hold more than two, and so on.

# A Crash Course in Hurting Things #
There are some general rules on can keep in mind when thinking about how a meatbag would respond to this or that form of physical abuse. First, piercing a meatbag with a spear is likely to damage internal organs in the area pierced. Second, blunt force trauma generally causes internal bleeding, internal organ rupture, and bone fracture. Third, blades are designed to sever things from other things.

Bodies are made of flesh (usually) and flesh exhibits a [J-shaped stress-strain curve](http://www.doitpoms.ac.uk/tlplib/bioelasticity/j-shaped-curves.php).


-- this part incomplete --
> - Now, a stupid orcling (there are smart orclings too) might, when attacking a meatbag, waggle his sword wildly in the general direction of the meatbag (hence why we had to pick a stupid one -- most orclings have more sense than that). this orcling isn't thinking about whether he's rupturing a spleen, or slicing off a finger. He's just waggling his sword around. So the question is, how do we figure out what this orcling would be damaging? That is, how do we abstract away the specifics of bodily damage enough that somebody not well-versed in surgery could just press the waggle sword around button, and yet deal realistic damage? This question is the harder one -- the answer probably involves multiple layers of abstraction (that is, layer one might abstract "hit spleen", "hit heart", "hit liver" etc into "hack at torso"; so layer one would have commands like "hit torso", "hit arm 1", "hit leg 1", "hit head", "hit tail" etc -- layer two would abstract this into "waggle sword wildly", and "slash down" etc)




# Implementation Details #
We'll need a few classes (maybe more, but here's the basic structure):
  * The BodyPart class
    * A simple object, serves to store the data a body part needs; including where it's connected, what functions it has, what functions it needs to function, its size, and other information handling the taking of damage.
  * The Body class
    * Less Simple, this would calculate damage, whether parts are functional, bleeding to death, etc.
  * The BodyBuilder class (for want of a better name)
    * This would read data from file, constructing body objects to spec. Could be very complicated, but with java configuration files, very simple.