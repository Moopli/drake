/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.GameState;
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
    
    public Mob(){
        mappable.mob = this;
        this.body = BodyBuilder.loadBody("blob.body");
    }
    
    public void attack(int x, int y) {
        /*should consider factions before attacking*/
        System.out.println("something has been hit");
        GameState.commandLine.addString("something has been hit");
        Weapon w = this.body.procureWeaponry();
        if (w == null){
            w = new Weapon(); // a basic "push" attack
        }
        Mappable m = this.mappable.dungeon.getMappableAt(x, y);
        if (m.mob != null){
            m.mob.body.delegateDamage(w.material, w.contactArea, w.hitForce);
            m.dungeon.dropGore(m.x, m.y, 26);
        }
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
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x, mappable.y-1);
        if (move == 1) {
            attack(mappable.x, mappable.y-1);
        }
    }

    @Override
    public void goE() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x+1, mappable.y);
        if (move == 1) {
            attack(mappable.x+1, mappable.y);
        }
    }

    @Override
    public void goS() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x, mappable.y+1);
        if (move == 1) {
            attack(mappable.x, mappable.y+1);
        }
    }

    @Override
    public void goW() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x-1, mappable.y);
        if (move == 1) {
            attack(mappable.x-1, mappable.y);
        }
    }

    @Override
    public void goNW() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x-1, mappable.y-1);
        if (move == 1) {
            attack(mappable.x-1, mappable.y-1);
        }
    }

    @Override
    public void goSW() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x-1, mappable.y+1);
        if (move == 1) {
            attack(mappable.x-1, mappable.y+1);
        }
    }

    @Override
    public void goNE() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
        int move = mappable.moveTo(mappable.x+1, mappable.y-1);
        if (move == 1) {
            attack(mappable.x+1, mappable.y-1);
        }
    }

    @Override
    public void goSE() {
        if ((statusBitMask & CAN_MOVE) == 0) return;
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
    // note inventory constructor really should set capacity, but there's a default
    
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
