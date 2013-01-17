/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

/**
 *
 * @author 301916706
 */
public class Player extends Mob {
    
    public Player( int x, int y){
        this.brain = new core.objects.ai.PlayerAI();
        this.getMapRepresentation().x = x;
        this.getMapRepresentation().y = y;
        this.getMapRepresentation().img = '@';
        
    }
    
    
}
