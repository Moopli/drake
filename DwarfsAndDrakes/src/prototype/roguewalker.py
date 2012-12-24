#!/usr/bin/env python
import pygame, sys, os, platform
from pygame.locals import * 
from pygame.color import THECOLORS
from pygame.mouse import get_pos
from random import randint
from time import time
from math import *


m = """...............
...............
...............
...............
...............
...............
...............
...............
...............
...............
..............."""

_INPUT_BUFFER = [""]
_OUTPUT_BUFFER = [""]

def toMapArr(s):
    return [[i for i in j.strip()] for j in s.split()]

def display(map_ar):
    os.system("CLS")
    for line in map_ar:
        print "".join(line)

the_map = toMapArr(m)
pX = 0 # bad coding style -- refactor into player class eventually
pY = 0

def clearAtSym(map_ar):
    # clear @
    for y in xrange(len(map_ar)):
        for x in xrange(len(map_ar[y])):
            if map_ar[y][x] == "@":
                map_ar[y][x] = "."
                return
    

def updateMap(map_ar):
    clearAtSym(map_ar)
    map_ar[pY][pX] = "@"
                

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


def movePlayer(map_ar, command):
    global pX
    global pY
    _OUTPUT_BUFFER.append("")
    if command in COM_GONORTH:
        # move player north
        pY = (pY - 1) % len(the_map)
        pass
    elif command in COM_GOSOUTH:
        # move player south
        pY = (pY + 1) % len(the_map)
        pass
    elif command in COM_GOEAST:
        # move player east
        pX = (pX + 1) % len(the_map[pY])
        pass
    elif command in COM_GOWEST:
        # move player west
        pX = (pX - 1) % len(the_map[pY])
        pass
    elif command in ("quit", "exit", "suicide", "die"):
        return True
    else:
        _OUTPUT_BUFFER[-1] = "you try to",command+". You fail miserably."

"""
while True:
    updateMap(the_map)
    display(the_map)
    if movePlayer(the_map, raw_input("> ").strip()): break

print "You die. Better luck next time!"
"""


########
# PYGAME MODE YAY
#

class TextDisplay:
    def __init__(self):
        self.font = pygame.font.SysFont("Courier New", 18)
        self.WIDTH = 50
        self.HEIGHT = 20
        self.char_array = [[" " for j in xrange(self.WIDTH)] for  i in xrange(self.HEIGHT)]
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
    def updateMap(self):
        self.char_array = [[" " for j in xrange(self.WIDTH)] for  i in xrange(self.HEIGHT)]
        for y in xrange(len(the_map)):
            for x in xrange(len(the_map[y])):
                self.char_array[y + self.map_y][x + self.map_x] = the_map[y][x]
        for x in xrange(len(self.inText)):
            self.char_array[x / len(self.char_array) + self.txt_y][x % len(self.char_array) + self.txt_x] = self.inText[x]
        for x in xrange(len(self.outText)):
            self.char_array[x / len(self.char_array) + self.out_y][x % len(self.char_array) + self.out_x] = self.outText[x]
    def render(self, surface):
        for y in xrange(len(self.char_array)):
            for x in xrange(len(self.char_array[y])):
                surface.blit(self.font.render(self.char_array[y][x], True, self.color), (self.x + x*self.charRect.width, self.y + y*self.charRect.height)) 

if platform.system() == 'Windows':
    os.environ['SDL_VIDEODRIVER'] = 'windib'

maxx, maxy = 800, 800


pygame.init() 
window = pygame.display.set_mode((maxx, maxy)) 
pygame.display.set_caption('CAPTION') 
screen = pygame.display.get_surface()
screen.fill(THECOLORS['black'])

main_surface = pygame.Surface((maxx, maxy), SRCALPHA)



try:
    run = True    
    textDisplay = TextDisplay()
    
    while run:
        updateMap(the_map)
        #display(the_map)
        textDisplay.inText = _INPUT_BUFFER[-1]
        textDisplay.outText = _OUTPUT_BUFFER[-1]
        textDisplay.updateMap()
        
        events = pygame.event.get()
        for event in events:
            if event.type == KEYDOWN:
                if event.unicode == "\r":
                    # BLAH RESPOND TO ENTER
                    if run and movePlayer(the_map, _INPUT_BUFFER[-1].strip()):
                        run = False
                    _INPUT_BUFFER.append("")
                else:
                    _INPUT_BUFFER[-1] = _INPUT_BUFFER[-1] + event.unicode
            
            
            
            if event.type == QUIT:
                run = False
        screen.fill(THECOLORS["black"])
        textDisplay.render(screen)
        #screen.blit(main_surface, (0,0))
        pygame.display.flip()

        
finally:
    pygame.quit()














