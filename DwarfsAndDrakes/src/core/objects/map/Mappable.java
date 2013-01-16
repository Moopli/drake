/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

/**
 *
 * @author 301916706
 */
public class Mappable {
    
    public int x, y;
    
    public char img;
    
    public ActiveArea dungeon; // really shouldn't be puvblic, we'll refactor later
    
    public void moveTo(int x, int y){
        // out of bounds
        if (x < 0 || x > dungeon.width || y < 0 || y > dungeon.height) return;
        // overlaps movement-blocker
        if ((dungeon.tileFlags[y][x] & ActiveArea.BLOCKS_MOVEMENT) != 0) return;
        this.x = x; this.y= y;
    }
    
    public int getSmellOnTile(){
        return dungeon.playerScent[y][x];
    }
    
}
