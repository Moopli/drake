#!usr/bin/env python
from collections import deque

# Stub
class Obj:
    def doAction(self):
        return 1 # auto-wait 1 turn
    def isAlive(self):
        return True

# Stub
class QEntry:
    def __init__(self):
        self.next, self.data = None, deque()

class RotPQ:
    def __init__(self, size):
        assert size > 0, "the queue's size must be strictly positive"
        # set up circularity
        self.data = [deque() for i in xrange(size)]
        #for i in xrange(size):
         #   self.data[i].next = self.data[(i+1)%size]
        self.front = 0
    def rotate(self):
        self.front = (self.front+1)%len(self.data)
    def insertObj(self, obj, index):
        #assert isinstance(obj, Obj)
        self.data[(self.front + index)%len(self.data)].append(obj)
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
