/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import java.util.*;


/*
 * Should this subclass DamageableArea? or should we just merge the two?
 * 
 */

/**
 * 
 * @author filip
 */
public class BodyPart extends DamageableArea {
    
    
    public String internalName;
    
    public HashSet<BodyPart> connected = new HashSet<BodyPart>();
    public HashSet<String> functionsProvided = new HashSet<String>();
    public HashSet<String> functionsRequired = new HashSet<String>();
    
    // if an organ is vaporized, it hardly has nerves or blood vessels, does it?
    boolean supportsInfrastructure = true; 
    
    // a body part may be wholy within another
    BodyPart contained_inside;
    
}
