/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.objects.ai.*;
import core.objects.anatomy.*;
import core.objects.equips.*;
import core.objects.map.*;


/**
 *
 * @author filip
 */
public class Mob implements HasAI, IsMappable, HasInventory, HasBody {
    
    protected AIController brain;
    
    protected int moveDelay = 4; // not 1, so we can make things that move fast
    
    /**
     * Status bitmask flags
     */
    public static final int CAN_SEE = 1, CAN_MOVE = 2, CAN_SMELL = 4;
    
    protected int statusBitMask = CAN_SEE | CAN_MOVE | CAN_SMELL;
    
    @Override
    public void setController(AIController contr) {
        brain = contr;
    }
    
    @Override
    public AIController getController(){
        return brain;
    }
    
    private Mappable mappable = new Mappable();
    
    @Override
    public Mappable getMapRepresentation() {
        return mappable;
    }
    
    @Override
    public void goN() {
        mappable.moveTo(mappable.x, mappable.y-1);
    }

    @Override
    public void goE() {
        mappable.moveTo(mappable.x+1, mappable.y);
    }

    @Override
    public void goS() {
        mappable.moveTo(mappable.x, mappable.y+1);
    }

    @Override
    public void goW() {
        mappable.moveTo(mappable.x-1, mappable.y);
    }

    @Override
    public void goNW() {
        mappable.moveTo(mappable.x-1, mappable.y-1);
    }

    @Override
    public void goSW() {
        mappable.moveTo(mappable.x-1, mappable.y+1);
    }

    @Override
    public void goNE() {
        mappable.moveTo(mappable.x+1, mappable.y-1);
    }

    @Override
    public void goSE() {
        mappable.moveTo(mappable.x+1, mappable.y+1);
    }

    @Override
    public void rangeAttack(String target) {
        
    }

    @Override
    public void pickUp() {
        
    }
    
    Inventory inventory = new Inventory(); 
    // note inventory constructor should set capacity
    
    @Override
    public Inventory getInventory() {
        return inventory;
    }
    
    protected Body body = new Body();
    
    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    
}
