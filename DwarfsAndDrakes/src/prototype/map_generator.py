#!/usr/bin/env python
from random import randint, random

"""
This file contains a bunch of dungeon generators. Currently the list includes:
- Brownian noise generators
- Fractal Brownian noise generators
- A binary space partitioning dungeon generator

It is highly recommended to try this out. very cool.
"""

TILESET = {
    "wall" : "#",
    "path" : ".",
    "water" : "~"
    }
############
# first, some basic utilities:
def dirtMap(w, h):
    return [[TILESET["wall"] for i in xrange(w)] for j in xrange(h)]

def fillRect(ar, x, y, w, h, tile):
    for i in xrange(h):
        for j in xrange(w):
            ar[y + i][x + j] = TILESET[tile]

def display(ar):
    for line in ar:
        print "".join(line)

############
# for overworld and cavern generation
def brownian(ar, x, y):
    for i in xrange(len(ar) ** 3):
        x %= len(ar[0])
        y %= len(ar)
        ar[y][x] = TILESET["path"]
        x += randint(0,2) - 1
        y += randint(0,2) - 1

# for overworld and cavern generation
def fractalBrownian(ar, x, y):
    xinc = randint(0,2) - 1
    yinc = randint(0,2) - 1
    for i in xrange(len(ar) ** 2):
        x %= len(ar[0])
        y %= len(ar)
        ar[y][x] = TILESET["path"]
        if randint(0,1):
            xinc = randint(0,2) - 1
        if randint(0,1):
            yinc = randint(0,2) - 1
        x += xinc
        y += yinc

# Binary Space Partitioning for dungeon generation

MIN_DIM = 3 # the min width/height of a rectangle (rooms don't always fill their rects)

class BSPRect:
    def __init__(self):
        self.x = 0
        self.y = 0
        self.w = 0
        self.h = 0
        self.children = [None, None]
        self.cutType = None
        self.room = [[]]
    def partition(self, split_rate):
        #if self.children is not [None, None]: return # already partitioned
        if self.w <= MIN_DIM * 2 or self.h <= MIN_DIM * 2: return #too small
        if random() > split_rate: return # random chance decrees no split
        # actually partition now
        if random() > 0.5:
            # make horizontal cut
            self.cutType = "H"
            cut_y = randint(self.y + MIN_DIM, self.y + self.h - MIN_DIM)
            top = BSPRect()
            top.x, top.y, top.w, top.h = self.x, self.y, self.w, cut_y - self.y
            bot = BSPRect()
            bot.x, bot.y, bot.w, bot.h = self.x, cut_y, self.w, self.h + self.y - cut_y
            self.children = [top, bot]
            pass
        else:
            # make vertical cut
            self.cutType = "V"
            cut_x = randint(self.x + MIN_DIM, self.x + self.w - MIN_DIM)
            lft = BSPRect()
            lft.x, lft.y, lft.w, lft.h = self.x, self.y, cut_x - self.x, self.h
            rgt = BSPRect()
            rgt.x, rgt.y, rgt.w, rgt.h = cut_x, self.y, self.x + self.w - cut_x, self.h
            self.children = [lft, rgt]
            pass
        # partition children also
        for child in self.children:
            child.partition(split_rate)
    def roomify(self):
        if self.cutType is None:
            # makes room
            self.room = [[TILESET["wall"] for i in xrange(self.w)]]+[[TILESET["wall"]]+[TILESET["path"] for i in xrange(self.w-2)]+[TILESET["wall"]] for j in xrange(self.h-2)]+[[TILESET["wall"] for i in xrange(self.w)]]
        else:
            # roomify children, connect them
            for child in self.children: child.roomify()
            if self.cutType == "H": # put top child room above bottom
                self.room = [[i for i in row] for row in self.children[0].room] + [[i for i in row] for row in self.children[1].room]
                # must connect rooms here
                # to connect rooms -- pick some point along boundary between children
                c_point = randint(1, self.w - 2)
                # then, tunnel out in both directions until you strike
                # tunnel up
                y = self.children[0].h # start at boundary
                while y > 0:
                    self.room[y][c_point] = TILESET["path"] # [OOR ERR]
                    if c_point > 0 and self.room[y][c_point - 1] == TILESET["path"]:
                        break # left check
                    if c_point < self.w - 1 and self.room[y][c_point + 1] == TILESET["path"]:
                        break # right check
                    if self.room[y-1][c_point] == TILESET["path"]:
                        break # up check
                    # down check not needed
                    y -= 1
                # tunnel down
                y = self.children[0].h # start at boundary
                while y < self.h - 2:
                    self.room[y][c_point] = TILESET["path"]
                    if c_point > 0 and self.room[y][c_point - 1] == TILESET["path"]:
                        break # left check
                    if c_point < self.w - 1 and self.room[y][c_point + 1] == TILESET["path"]:
                        break # right check
                    if self.room[y+1][c_point] == TILESET["path"]:
                        break # down check
                    # up check not needed
                    y += 1
            else:
                # left child room left of right
                self.room = [[i for i in row] for row in self.children[0].room]
                for y in xrange(len(self.room)):
                    self.room[y].extend([i for i in self.children[1].room[y]])
                # must connect rooms here
                # same method applies as above
                c_point = randint(1, self.h - 2)
                # tunnel left
                x = self.children[0].w # start at boundary
                while x > 0:
                    self.room[c_point][x] = TILESET["path"]
                    if c_point > 0 and self.room[c_point-1][x] == TILESET["path"]:
                        break # up check
                    if c_point < self.h - 1 and self.room[c_point+1][x] == TILESET["path"]:
                        break # down check
                    if self.room[c_point][x-1] == TILESET["path"]:
                        break # left check
                    # right check not needed
                    x -= 1
                # tunnel right
                x = self.children[0].w # start at boundary
                while x < self.w - 2:
                    self.room[c_point][x] = TILESET["path"]
                    if c_point > 0 and self.room[c_point-1][x] == TILESET["path"]:
                        break # up check
                    if c_point < self.h - 1 and self.room[c_point+1][x] == TILESET["path"]:
                        break # down check
                    if self.room[c_point][x+1] == TILESET["path"]:
                        break # right check
                    # left check not needed
                    x += 1
                

###############
# SAMPLE CODE

# -- Dungeon --
print " ## DUNGEON MAP ## "
bspr = BSPRect()
bspr.w = 50
bspr.h = 40
bspr.partition(1)
bspr.roomify()
display(bspr.room)

# -- Overworld --
print " ## OVERWORLD MAP ## "
ovw = dirtMap(50, 40)
fractalBrownian(ovw, 12, 17)
display(ovw)


