/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.objects.map.ActiveArea;

/**
 *
 * @author 301916706
 */
public class Player extends Mob {
    
    public Player( int x, int y, ActiveArea dungeon){
        this.brain = new core.objects.ai.PlayerAI();
        this.brain.setControlled(this);
        this.getMapRepresentation().x = x;
        this.getMapRepresentation().y = y;
        this.getMapRepresentation().img = '@';
        this.getMapRepresentation().dungeon = dungeon;
    }
    
    
}
