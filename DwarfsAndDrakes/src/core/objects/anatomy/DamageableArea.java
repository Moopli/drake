/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import java.util.ArrayList;

/**
 * DamageableAreas are regions of a body which are able to be hurt from outside 
 * -- eg, a torso, or a hand, but not a spleen (a spleen is part of a torso, so 
 * the torso handles getting hit).
 * 
 * 
 * @author filip
 */
public class DamageableArea {
    
    ArrayList<BodyPart> contained_parts = new ArrayList<BodyPart>();
    
    boolean isArmorable = false; // you can't wear a spleenplate, but you can sure wear a guantlet!
    
    int volume; // overall volume, determines likelihood to be hit (along with length) and penetration depth if hit (along with hardness)
    int length; // length of major axis, to determine how likely it is to be hit
    int hardness; // how easy it is to cut/poke/smash through?
    
    
    /*
     * Yup, here it is; the big "damage that thing" method.
     * 
     * Things that can happen when a damageable area gets hit -- 
     * - surface damage (bruising, cuts)
     *  - based on this.hardness and collision parameters, we can tell if the 
     *    resulting penetration depth is low enough to ... not penetrate.
     * - penetration, misses stuff inside (oh thank goodness! that arrow managed 
     *   to miss every internal organ in my gut!)
     *  - If we rule penetration, based on relative volumes and stuff we can 
     *    decide that either soemthing got hit, or nothing got hit. Bad either 
     *    way.
     * - penetration, hits something inside (aah my spleen!)
     *  - Likely causes loss of function
     * 
     */
    
    public void damage(int contact_area, int contact_impulse){
        
    }
    
    // properties storing just howw damaged it is
    
    /*
     * mayhaps we could use a current HP vs. minimum HP to function system --
     * we change descriptors based on where current HP is, and say things like 
     * "about to fail", or "perfectly healthy".
     * What's more, the minHP value could be moved around, and so could curHP, 
     * based on something like a constitution number.
     */
    
    int curHP, minHP;
}
