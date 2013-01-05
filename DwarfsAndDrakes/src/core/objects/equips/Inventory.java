/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.equips;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author filip
 */
public class Inventory {
    
    
    /*
     * Not sure if using an entry is good idea, but we'll figure out
     * (note on the mapping: item name -> list of same item (a stack)
     */
    public HashMap<String, ArrayList<InventoryItem>> inventory;
    
    
}
