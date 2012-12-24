#!/usr/bin/env python

"""
Statuses available:
healthy --- perfectly fine
bruised --- took a hit or two, but functioning pretty much perfectly.
damaged --- took quite the beating, failing a bit.
broken ---- busted up real bad, but with much bedrest may get better.
nonpresent- completely gone, or so mutilated as to be.
            cannot be healed except in rare cases.
"""

"""
A simple class for connected body parts
"""
class ConnectedPart(BodyPart):
    def __init__(self, name):
        self.attached = {}
        self.name = name
        self.contained = {}
        self.container = None
        self.location = "center"
        self.status = "healthy"
    def lopOff(self, partName):
        self.attached[partName].attached.remove(self.name)
        self.attached.remove(partName)
        pass
    """
    Connects two body parts to each other. these connections
    are the sort it is possible to chop off.
    """
    def linkTo(self, other):
        other.attached[self.name] = self
        self.attached[other.name] = other
        pass
    """
    Connects two parts by putting the one within the other.
    """
    def putInside(self, container):
        if self.container is not None and self.container.contained.has_key(self.name):
            self.container.contained.pop(self.name)
        self.container = container
        if container is not None:
            container.contained[self.name] = self
            pass
        pass
    
    pass



# scripting section
def genDude():
    BP = ConnectedPart

    upperTorso = BP("upper torso")
    lowerTorso = BP("lower torso")
    leftArm = BP("left arm")
    rightArm = BP("right arm")
    leftForeArm = BP("left forearm")
    rightForeArm = BP("right forearm")
    leftHand = BP("left hand")
    rightHand = BP("right hand")
    head = BP("head")
    jaw = BP("jaw")
    leftThigh = BP("left thigh")
    rightThigh = BP("right thigh")
    leftCalf = BP("left calf")
    rightCalf = BP("right calf")
    leftFoot = BP("left foot")
    rightFoot = BP("right foot")

    head.linkTo(jaw)
    upperTorso.linkTo(head)
    lowerTorso.linkTo(upperTorso)

    leftArm.linkTo(upperTorso)
    leftForeArm.linkTo(leftArm)
    leftHand.linkTo(leftForeArm)

    rightArm.linkTo(upperTorso)
    rightForeArm.linkTo(rightArm)
    rightHand.linkTo(rightForeArm)

    leftFoot.linkTo(leftCalf)
    leftCalf.linkTo(leftThigh)
    leftThigh.linkTo(lowerTorso)

    rightFoot.linkTo(rightCalf)
    rightCalf.linkTo(rightThigh)
    rightThigh.linkTo(lowerTorso)
    
    dude = [head, jaw, upperTorso, leftArm, leftForeArm, leftHand, rightArm, rightForeArm, rightHand, lowerTorso, leftThigh, leftCalf, leftFoot, rightThigh, rightCalf, rightFoot]
    return dude

class Dude:
    def __init__(self):
        self.name = "generic dude"
        self.parts = {}
        for i in genDude():
            self.parts[i.name] = i
    def status(self, name):
        if name not in self.parts:
            return "nonpresent"
        return self.parts[name].status
    def print_statuses(self):
        for i in self.parts:
            print self.name + "'s " + i + " is " + self.parts[i].status

        

    """
Time to manage stances --
- body parts will be in particuar areas
- these areas will be defined by three axes:
    - left - center - right
    - forward - center - back
    - high - center - low
    - GAH these zones are very broad (about 2ft each) and lose a lot of nuances 
    Perhaps we should us these zones for general location, but special
    part-part relative location info to help. (that is, left arm left of torso,
    upper torso above lower torso, etc). A weapon swiping down, for example, can
    only hit things in its path -- so, say, the head, upper torso, lower torso,
    but not the arm off to the side.
- A stance is just a placement of kinds of parts in different areas.
    - For example, a standard human 2h-sword stance would put the forearms
    in front center, the sword in front top center and front center, the legs in
    bottom center, torso in center, head in top center.
- Stances and receiving damage:
    - Were a goblin with a battleaxe to slash at the left of our human, he'd
    probably get the left arm and torso. How to simulate?
    - Simulating battleaxe movement:
        - axe swipes into left center
        - axe swipes into center
        - damage
    - So, it appears we'd have the axe do damage to stuff in left center when
    it enters left center, lose momentum, enter center, deal damage there, lose
    momentum, and of course, follow goblin's gestures.
        - more detail into axe's motion:
            - axe enters left center, no body parts here to damage
            - axe enters center without having lost momentum
            - several body parts here to damage:
                - upper torso, left arm, lower torso
                - left arm is defined to be left of torso, so it catches hit
                    - it takes some damage, may impede further swiping
                    - if the left arm was not severed, th axe is done slashing
                    - otherwise, the axe caries on and hits one of:
                        - upper torso, lower torso
                        - upper torso is defined as above lower torso, so axe
                        can't hit both moving sideways.

    """
    def derp(self):
        pass
    pass




