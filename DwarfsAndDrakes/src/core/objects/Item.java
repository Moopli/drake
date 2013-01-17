/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.objects.map.*;
import core.objects.equips.*;
import core.objects.flavor.*;

/**
 *
 * @author 301916706
 */
public class Item implements IsMappable, IsInventoriable {

    Mappable mapRep;
    InventoryItem invData;
    
    @Override
    public Mappable getMapRepresentation() {
        return mapRep;
    }

    @Override
    public InventoryItem getInventoryInfo() {
        return invData;
    }
    
}
