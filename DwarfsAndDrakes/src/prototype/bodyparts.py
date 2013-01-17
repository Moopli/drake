#!usr/bin/env python
import collections

"""
# consider this a shorthand for now

[ALL:REQUIRED_ORGANS]
    [LUNGS]
    [HEART]
    [LIVER]
    [INTESTINE]
    [BRAIN]
[/]

[GI_TRACT:REQUIRED_ORGANS]
    [STOMACH]
    [ESOPHAGUS]
    [MOUTH]
    [THROAT]
    [INTESTINE]
    [COLON]
[/]

-----------
[HAND:FUNCTIONS]
    [GRASPING]
[/]

[FOOT:FUNCTIONS]
    [AMBULATORY]
[/]

[]



"""

"""
Statuses available:
healthy --- perfectly fine
bruised --- took a hit or two, but functioning pretty much perfectly.
damaged --- took quite the beating, failing a bit.
broken ---- busted up real bad, but with much bedrest may get better.
nonpresent- completely gone, or so mutilated as to be.
            cannot be healed except in rare cases.

"""


class BodyPart:
    def __init__(self):
        self.connected = [] # a list of parts this is conencted to
        self.functions = [] # a list of functions this body-part provides
        self.required_functions = [] # a list of functions which this part requires to be useful
    



class Body:
    def __init__(self):
        parts_list = []
        pass
    


class Bodybuilder:
    def __init__(self):
        pass
    
















