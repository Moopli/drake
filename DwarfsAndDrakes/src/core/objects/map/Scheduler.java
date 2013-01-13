/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import java.util.*;

/**
 *
 * @author filip
 */
public class Scheduler {
    /*
     * This class schedules mob actions in the main game run loop.
     * For more info, see here:
     * http://code.google.com/p/dwarfs-and-drakes/wiki/TimeReckoning
     */
    
    /*
     * These two properties together define a circular list of queues.
     */
    
    /**
     * 
     */
    private ArrayList<LinkedList<String>> pq = new ArrayList<LinkedList<String>>();
    /**
     * 
     */
    private int currentPosition = 0;
    
    
    public Scheduler(){
        this(1);
    }
    
    public Scheduler(int size){
        for (int i = 0; i < size; i++ ){
            pq.add(new LinkedList<String>());
        }
    }
    
    /**
     * Called in the main loop, this updates the given String ID (by bumping it 
     * back the given number of ticks). 
     * Note that if the String passed in is null, the enqueue that would 
     * otherwise be committed is ignored.
     * 
     * @param lastMoved
     * @param lastMoveDelay 
     * @return 
     */
    public String oneTick(String lastMoved, int lastMoveDelay){
        // enqueue argument
        if (lastMoved != null){
            enqueue(lastMoved, lastMoveDelay);
        }
        // dequeue and return next
        
        // rotate to next tick if no mobs in current tick (possibly multiple)
        int rotStart = currentPosition;
        boolean isEmpty = true;
        do {
            isEmpty = this.pq.get(currentPosition).isEmpty();
            currentPosition = (currentPosition+1)%this.pq.size();
        }while (!isEmpty && rotStart != currentPosition);
        // if rotations reveal total emptiness; there are no more mobs which can 
        // act.
        
        // otherwise, though:
        if (!isEmpty){
            return this.pq.get(currentPosition).removeFirst();
        }
        return null; // special case -- no mob which can act
    }
    
    
    /**
     * Enqueues the given String ID to the circular buffer into the queue at the 
     * given location.
     * @param id The String ID to be enqueued.
     * @param pos The index.
     */
    public void enqueue(String id, int pos){
        assert (pos <= this.pq.size() && pos > 0);
        this.pq.get((currentPosition+pos)%this.pq.size()).add(id);
    }
}
