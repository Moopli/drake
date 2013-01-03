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
public class BodyPart {
    
    public ArrayList<BodyPart> connected = new ArrayList<BodyPart>();
    public ArrayList<String> functions_provided = new ArrayList<String>();
    public ArrayList<String> functions_required = new ArrayList<String>();
    
    // And of course, all of those pesky properties:
    boolean supportsInfrastructure = true; // if an organ is vaporized, it hardly has nerves or blood vessels, does it?
    
    
    int thickness; // overall volume, determines likelihood to be hit (along with length) and penetration depth if hit (along with hardness)
    int length; // length of major axis, to determine how likely it is to be hit
    int hardness; // how easy it is to cut/poke/smash through?
    
    
}
