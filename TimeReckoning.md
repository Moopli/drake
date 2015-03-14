# Who's On First? #

The problem is a simple one: We have a bunch of objects, each doing actions. Different actions take different amounts of time. Each object should do its next action as soon as the previous is complete. The problem is, how?

# The Solution #

Let's create a queue; which contains the objects which need to move, and thus represents the order in which objects will be updated. So, say we pop an object off of the queue -- it does its action; but now what? We have to put it back in the queue, otherwise it shan't move again. So, we decide to shove it back in the queue. But wait -- where do we shove it back in? We could shove it back in at the end of the queue. That would work, but then we wouldn't have any actions taking more or less time. So, let's say our action takes 3 time. So, we bump our object back three slots; so three time later, it'll move. "But wait!" you'll say, "that wont work!". And you're right. That won't work -- what happens to the objects in fourth? They never get a chance to move.

As it turns out, there is an easy way to fix all of this. Let's say we have a priority queue PQ. PQ is a priority queue of queues. Each queue is a queue of objects. Anyways, each element in PQ contains a bunch of objects which will move a certain time in the future (how far in the future being the index). So, here's our algorithm:
  1. Pop first queue in PQ, call it Q.
  1. Iterate through all objects O in Q:
    1. Let O pick an action -- moving through map, attacking, etc
    1. O does the action
    1. The action takes a certain amount of time to complete -- insert O in the queue this far back in PQ. (No zero-turn actions here)
  1. Push a new empty queue onto PQ.
  1. GOTO step 1.

## Implementation details ##
PQ can be handled as a rotating queue -- a circular linked list,  with a moving pointer to the start of the list -- hence, the first step of popping and the last step of pushing are foregone for simply repointing to the next queue. This works, because our active queue, once we've handled all objects in it, will be empty. So when we rotate the circle, the empty queue will end up at the end of the list.

```
from collections import deque

# Stub
class Obj:
    def doAction(self):
        return 1 # auto-wait 1 turn

# Stub
class QEntry:
    def __init__(self):
        self.next, self.data = None, deque()

class RotPQ:
    def __init__(self, size):
        assert size > 0, "the queue's size must be strictly positive"
        # set up circularity
        self.data = [QEntry() for i in xrange(size)]
        for i in xrange(size):
            self.data[i].next = self.data[(i+1)%size]
        self.front = 0
    def rotate(self):
        self.front = (self.front+1)%len(self.data)
    def insertObj(self, obj, index):
        assert isinstance(obj, Obj)
        self.data[(self.front + index)%len(self.data)].append(obj)
    def stepTime(self):
        "Steps through one unit of time, moving all objects in this time slot"
        while self.data[self.front]:
            # pop off first obj
            obj = self.data[self.front].popleft()
            pos = obj.doAction()
            # schedule the object for next movement
            insertObj(obj, pos)
        # rotate next queue up and old queue to end
        self.rotate()
```






