/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

import java.util.ArrayList;
import java.util.Map.Entry;

/**
 *
 * @author 301916706
 */
public class FSMState {
    
    public String stateName;
    // should we use entries? or something else..?
    public ArrayList<Entry<FSMTransition, FSMState>> transitions;
    
    
    
    
}
