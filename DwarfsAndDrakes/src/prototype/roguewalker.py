#!/usr/bin/env python
import pygame, sys, os, platform
from pygame.locals import * 
from pygame.color import THECOLORS
from pygame.mouse import get_pos
from random import randint
from time import time
from math import *
import rotpq

TILESET = {
    "wall" : "#",
    "path" : ".",
    "water" : "~"
    }

def readMapAr(path):
    out = []
    with open(path, "r") as fin:
        for line in fin:
            out.append(line)
    return "".join(out)

m = readMapAr("dungeon.map")

_INPUT_BUFFER = [""]
_OUTPUT_BUFFER = [""]

def toMapArr(s):
    return [[i for i in j.strip()] for j in s.split()]

def display(map_ar):
    os.system("CLS")
    for line in map_ar:
        print "".join(line)

the_map = toMapArr(m)
                

############
# Commands
############
COM_GONORTH = ("north", "n")
COM_GOSOUTH = ("south", "s")
COM_GOEAST = ("east", "e")
COM_GOWEST = ("west", "w")
COM_LOOK = ("look", "k")
COM_INV = ("inventory", "i")

###########
# Verbs -- not implemented yet
###########
VERB_GO = {"go" : (COM_GONORTH, COM_GOSOUTH, COM_GOEAST, COM_GOWEST)}


###########
#
#

class RandomMob:
    def __init__(self):
        self.x, self.y = 2,2
        self.map_ar = the_map
        self.symbol = "@"
    def moveTo(self, x, y):
        if x >= 0 and y >= 0 and y < len(self.map_ar) and x < len(self.map_ar[y]):# bounds check.
            if self.map_ar[y][x] != TILESET["wall"]:  # can step on
                self.x, self.y = x,y
    def doAction(self):
        i = randint(0,3)
        if i == 0:
            self.moveTo(self.x, self.y-1)
        elif i == 1:
            self.moveTo(self.x, self.y+1)
        elif i == 2:
            self.moveTo(self.x-1, self.y)
        elif i == 3:
            self.moveTo(self.x+1, self.y)
        return 2
    def isAlive(self):
        return True
            

class Player:
    def __init__(self):
        self.x, self.y = 1,1
        self.map_ar = the_map
        self.symbol = "@"
        self.events = []
    def moveTo(self, x, y):
        if x >= 0 and y >= 0 and y < len(self.map_ar) and x < len(self.map_ar[y]):# bounds check.
            if self.map_ar[y][x] != TILESET["wall"]:  # can step on
                self.x, self.y = x,y
    def eatCommand(self, command):
        _OUTPUT_BUFFER.append("")
        if command in COM_GONORTH:
            # move player north
            self.moveTo(self.x, self.y-1)
        elif command in COM_GOSOUTH:
            # move player south
            self.moveTo(self.x, self.y+1)
        elif command in COM_GOEAST:
            # move player east
            self.moveTo(self.x+1, self.y)
        elif command in COM_GOWEST:
            # move player west
            self.moveTo(self.x-1, self.y)
        elif command in ("quit", "exit", "suicide", "die"):
            return True
        else:
            _OUTPUT_BUFFER[-1] = "you try to "+command+". You fail miserably."
    def isAlive(self):
        return True
    def getEventBuffer(self, events):
        self.events = events
    def doAction(self):
        # do stuff
        done = 0
        while not done:
            events = pygame.event.get()
            for event in events:
                if event.type == KEYDOWN:
                    done = 1
                    if event.key == K_UP:
                        player.eatCommand("n")
                    elif event.key == K_DOWN:
                        player.eatCommand("s")
                    elif event.key == K_LEFT:
                        player.eatCommand("w")
                    elif event.key == K_RIGHT:
                        player.eatCommand("e")
                    else:
                        done = 0
                    if done:
                        pygame.event.get(KEYDOWN)
                        return 1
            
        return 1
    pass

class DungeonMap:
    def __init__(self, w = 5, h = 5):
        self.tiles = [[" " for i in xrange(w)] for j in xrange(h)]
        self.objs = [Player(), RandomMob(), RandomMob()]
        self.scheduler = rotpq.RotPQ(10)
        for obj in self.objs:
            self.scheduler.insertObj(obj, 0)
    def renderable(self, player, w = 13, h = 13): # given render window size, produces a map centered on player
        cam_x, cam_y = (player.x if player.x > w/2 else w/2), (player.y if player.y > h/2 else h/2)
        out = [[self.tiles[y][x] for x in xrange(max(0, cam_x - w/2), min(len(self.tiles[y]), cam_x + w/2))] for y in xrange(max(0, cam_y - h/2), min(len(self.tiles), cam_y + h/2))]
        for obj in self.objs: # remember to ignore objects outside view
            if abs(obj.y - cam_y) < h/2 and abs(obj.x - cam_x) < w/2:
                out[obj.y - cam_y + h/2][obj.x - cam_x + w/2] = obj.symbol
            #print obj.x, obj.y
        return out  
    def stepTime(self, events):
        self.scheduler.stepTime()
        pass
    
    

"""
def stepTime(self):
        "Steps through one unit of time, moving all objects in this time slot"
        while self.data[self.front]:
            # pop off first obj
            obj = self.data[self.front].popleft()
            pos = obj.doAction()
            # schedule the object for next movement (and gc at the same time)
            if obj.isAlive():
                self.insertObj(obj, pos)
        # rotate next queue up and old queue to end
        self.rotate()
"""

########
# PYGAME MODE YAY
#

maxx, maxy = 800, 800

class TextDisplay:
    def __init__(self):
        self.font = pygame.font.SysFont("Courier New", 18)
        self.w = maxx / self.font.size("@")[0]
        self.h = maxy / self.font.size("@")[1]
        self.char_array = [[[" ", "black", "green"] for j in xrange(self.w)] for  i in xrange(self.h)]
        self.map_x = 2
        self.map_y = 2
        self.txt_x = 2
        self.txt_y = 15
        self.out_x = 2
        self.out_y = 13
        self.charRect = Rect(0,0,self.font.size("@")[0], self.font.size("@")[1])
        self.x, self.y, self.color = 0,0, (0,255,0,255)
        self.inText = ""
        self.outText = ""
        self.textmap = TextSurface(w = self.w, h = self.h)
    def updateMap(self):
        self.textmap.char_array = [[["-", "black", "green"] for j in xrange(self.w)] for  i in xrange(self.h)]
        #for y in xrange(len(the_map)):
         #   for x in xrange(len(the_map[y])):
          #      self.textmap.char_array[y + self.map_y][x + self.map_x][0] = the_map[y][x]
        for x in xrange(len(self.inText)):
            self.textmap.char_array[x / len(self.char_array) + self.txt_y][x % len(self.char_array) + self.txt_x] = [self.inText[x], "black", "red"]
        for x in xrange(len(self.outText)):
            self.textmap.char_array[x / len(self.char_array) + self.out_y][x % len(self.char_array) + self.out_x][0] = self.outText[x]
    def render(self, surface):
        self.textmap.update()
        for y in xrange(len(self.char_array)):
            for x in xrange(len(self.char_array[y])):
                surface.blit(self.font.render(self.textmap.char_array[y][x][0], True, THECOLORS[self.textmap.char_array[y][x][2]]), (x*self.charRect.w, y*self.charRect.height))

class TextSurface:
    def __init__(self, x = 0, y = 0, w = 1, h = 1):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        # each grid location carries the char, the background color, and then the char color
        self.char_array = [[[" ", "black", "green"] for i in xrange(self.w)] for j in xrange(self.h)]
        # array of surfaces
        self.children = []
    def update(self):
        for surface in self.children:
            for i in xrange(surface.w):
                if x + i >= self.w:
                    break
                for j in xrange(surface.h):
                    if y + j >= self.h:
                        break
                    self.char_array[surface.y + j][surface.x + i] = surface.char_array[j][i]
    



################

if platform.system() == 'Windows':
    os.environ['SDL_VIDEODRIVER'] = 'windib'

pygame.init() 
window = pygame.display.set_mode((maxx, maxy)) 
pygame.display.set_caption('CAPTION') 
screen = pygame.display.get_surface()
screen.fill(THECOLORS['black'])

main_surface = pygame.Surface((maxx, maxy), SRCALPHA)



try:
    run = True
    textDisplay = TextDisplay()
    mapImg = TextSurface(x = 2, y = 2, w = 13, h = 13)
    textDisplay.textmap.children.append(mapImg)
    dungeon = DungeonMap()
    dungeon.tiles = the_map
    player = dungeon.objs[0]
    
    while run:
        #updateMap(the_map)
        #display(the_map)
        textDisplay.inText = _INPUT_BUFFER[-1]
        textDisplay.outText = _OUTPUT_BUFFER[-1]
        textDisplay.updateMap()
        out_map = dungeon.renderable(player)
        
        for y in xrange(len(out_map)):
            if y >= mapImg.h:
                break
            for x in xrange(len(out_map[y])):
                if x >= mapImg.w:
                    break
                mapImg.char_array[y][x][0] = out_map[y][x]
        
        events = pygame.event.get()
        dungeon.stepTime(events)
        for event in events:
            '''
            if event.type == KEYDOWN:
                if event.key == K_UP:
                    player.eatCommand("n")
                elif event.key == K_DOWN:
                    player.eatCommand("s")
                elif event.key == K_LEFT:
                    player.eatCommand("w")
                elif event.key == K_RIGHT:
                    player.eatCommand("e")
                elif event.unicode == "\r":
                    # BLAH RESPOND TO ENTER
                    if run and player.eatCommand(_INPUT_BUFFER[-1].strip()):
                        run = False
                    _INPUT_BUFFER.append("")
                else:
                    _INPUT_BUFFER[-1] = _INPUT_BUFFER[-1] + event.unicode
            '''
            if event.type == QUIT:
                run = False
        
        screen.fill(THECOLORS["black"])
        textDisplay.render(screen)
        #screen.blit(main_surface, (0,0))
        pygame.display.flip()

        
finally:
    pygame.quit()














