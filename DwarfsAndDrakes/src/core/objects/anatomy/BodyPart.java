/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import core.objects.materials.Material;
import java.util.*;
import core.objects.equips.*;
import core.objects.materials.MaterialLoader;


/*
 * Should this subclass DamageableArea? or should we just merge the two?
 * 
 */

/**
 * 
 * @author filip
 */
public class BodyPart {
    
    
    public String internalName;
    
    public HashSet<BodyPart> connected = new HashSet<BodyPart>();
    public HashSet<String> functionsProvided = new HashSet<String>();
    public HashSet<String> functionsRequired = new HashSet<String>();
    public HashSet<String> equipTags = new HashSet<String>();
    
    // if an organ is vaporized, it hardly has nerves or blood vessels, does it?
    boolean supportsInfrastructure = true; 
    
    // true if the part is able to carry out its functions, false otherwise.
    boolean isActive = true;
    
    // a body part may be wholly within another
    BodyPart contained_inside;
    
    // ## MERGED FROM DAMAGEABLEAREA
    
    ArrayList<BodyPart> contained_parts = new ArrayList<BodyPart>();
    
    boolean isArmorable = false; // you can't wear a spleenplate, but you can sure wear a guantlet!
    
    int volume; // overall volume, determines likelihood to be hit (along with length) and penetration depth if hit (along with hardness)
    int length; // length of major axis, to determine how likely it is to be hit
    // int hardness; // how easy it is to cut/poke/smash through?
    
    
    // OH OH OH I KNOW EXACTLY HOW TO DO THE DAMAGE STUFF -- bodyparts have materials.
    
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
    
    /**
     * The material this BodyPart is made of. For most "normal" creatures, that 
     * would be flesh. For others it would be jello. For others it would be 
     * salt. And so on and so forth.
     */
    Material material = MaterialLoader.makeElasticGoo();
    
    
    /**
     * This value is the highest wear the part's material can take before it 
     * fails. It is possible that the part
     */
    int maxWear;
}
