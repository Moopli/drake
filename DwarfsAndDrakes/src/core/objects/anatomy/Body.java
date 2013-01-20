/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import java.util.*;

/**
 *
 * @author filip
 */
public class Body {
    
    
    public HashMap<String, BodyPart> bodyparts = new HashMap<String, BodyPart>();
    
    /**
     * Breaks the body up into maximal contiguous sections, then updates the 
     * status of each part in each section by whether all functions required 
     * are provided.
     */
    public void updateFunctioning(){
        /* 
         * Here's how it'll work -- 
         * we break the body into maximal contiguous sections.
         * any requirement in a section must be met somewhere else in the 
         * section, otherwise something malfunctions.
         * 
         * In each contiguous section:
         * Tally up provided functions (that is, x parts provide foo)
         * For each part -- if it's requirements are not met, set it as inactive,
         * and thus decrement any service it provides. Keep going through all 
         * parts until your group stops shrinking.
         * 
         */
        
        // the disjoint set maximum connected subgraphs
        HashSet<DisjointSet<BodyPart>> parts = new HashSet<DisjointSet<BodyPart>>();
        for (BodyPart bp : bodyparts.values()){
            bp.isActive = true;
            parts.add(new DisjointSet(bp));
        }
        // merge
        for (DisjointSet<BodyPart> a : parts){
            for (DisjointSet<BodyPart> b : parts){
                if (a.datum.connected.contains(b.datum) && // parts must be attached
                        a.datum.supportsInfrastructure && // and still extant
                        b.datum.supportsInfrastructure){
                    a.merge(b);
                }
            }
        }
        // partition -- any two djs's with different roots are disconnected
        // this maps roots to their respective tree members (roots included)
        HashMap<DisjointSet<BodyPart>,HashSet<BodyPart>> partition = new HashMap<DisjointSet<BodyPart>, HashSet<BodyPart>>();
        for (DisjointSet<BodyPart> djs : parts){
            if (!partition.containsKey(djs.root())){
                partition.put(djs.root(), new HashSet<BodyPart>());
            }
            partition.get(djs.root()).add(djs.datum);
        }
        
        for (HashSet<BodyPart> group : partition.values()){
            updateConnectedGroup(group);
        }
        
    }
    
    /**
     * Given a group of parts which are connected, 
     * @param parts 
     */
    public void updateConnectedGroup(HashSet<BodyPart> parts){
        // once we have a particular connected set, 
        int activeParts = parts.size();
        
        // make tally of functions provided
        HashMap<String, Integer> tally = new HashMap<String, Integer>();
        for (BodyPart bp : parts){
            for (String func : bp.functionsProvided){
                if (!tally.containsKey(func)){
                    tally.put(func, 1);
                } else {
                    tally.put(func, tally.get(func) + 1);
                }
            }
        }
        
        boolean inactivate;
        boolean checkAgain;
        
        do { // while group doesn't keep shrinking
            checkAgain = false;
            
            
            for (BodyPart bp : parts) { // go though all, update
                inactivate = false; 
                for (String func : bp.functionsRequired){
                    if (!tally.containsKey(func) || tally.get(func) <= 0){
                        inactivate = true;
                        break;
                    }
                }
                if (inactivate){ // if the part doesn't have all functions required
                    bp.isActive = false;
                    checkAgain = true;
                    // decrement the tally of all sources of functions this provides
                    for (String func : bp.functionsProvided){
                        if (tally.containsKey(func)){
                            tally.put(func, tally.get(func) - 1);
                        }
                    }
                }
            }
            
        } while (checkAgain);
    }
    
    
    /*
     * And here we need a bunch of code dealing with getting hit -- update 
     * organs directly hit here, and call floodfill.
     */
    
    public void delegateDamage(){
        
        
        
    }
    
    
    
}
