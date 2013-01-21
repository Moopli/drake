/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.equips;

import core.objects.flavor.*;
import core.objects.materials.MaterialChunk;
import core.objects.materials.MaterialLoader;

/**
 *
 * @author filip
 */
public class InventoryItem {
    
    private FlavorHolder flavor;
    
    private MaterialChunk material = MaterialLoader.makeElasticGoo();
    
    
}
