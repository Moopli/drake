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
    protected int faction;

    public void attack(int x, int y) {
        /*this method will probably have to be in a different class, 
         because it needs access to the map
         
         attacks the mob at x, y
         
         consider factions before attacking*/
        System.out.println("something has been hit");
    }
    
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
        int move = mappable.moveTo(mappable.x, mappable.y-1);
        if (move == 1) {
            attack(mappable.x, mappable.y-1);
        }
    }

    @Override
    public void goE() {
        int move = mappable.moveTo(mappable.x+1, mappable.y);
        if (move == 1) {
            attack(mappable.x+1, mappable.y);
        }
    }

    @Override
    public void goS() {
        int move = mappable.moveTo(mappable.x, mappable.y+1);
        if (move == 1) {
            attack(mappable.x, mappable.y+1);
        }
    }

    @Override
    public void goW() {
        int move = mappable.moveTo(mappable.x-1, mappable.y);
        if (move == 1) {
            attack(mappable.x-1, mappable.y);
        }
    }

    @Override
    public void goNW() {
        int move = mappable.moveTo(mappable.x-1, mappable.y-1);
        if (move == 1) {
            attack(mappable.x-1, mappable.y-1);
        }
    }

    @Override
    public void goSW() {
        int move = mappable.moveTo(mappable.x-1, mappable.y+1);
        if (move == 1) {
            attack(mappable.x-1, mappable.y+1);
        }
    }

    @Override
    public void goNE() {
        int move = mappable.moveTo(mappable.x+1, mappable.y-1);
        if (move == 1) {
            attack(mappable.x+1, mappable.y-1);
        }
    }

    @Override
    public void goSE() {
        int move = mappable.moveTo(mappable.x+1, mappable.y+1);
        if (move == 1) {
            attack(mappable.x+1, mappable.y+1);
        }
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
