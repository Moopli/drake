/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import core.objects.Mob;
import java.awt.Color;

/**
 *
 * @author 301916706
 */
public class Mappable {
    
    public int x, y;
    
    public char img;
    public Color colorBack = Color.BLACK, colorFore = Color.white;
    
    public int tileFlags = ActiveArea.HAS_OBJECT;
    
    public Mob mob; // the mappable may belong to a Mob
    
    public ActiveArea dungeon; // really shouldn't be puvblic, we'll refactor later
    
    public int moveTo(int x, int y){
        // out of bounds
        if (x < 0 || x > dungeon.width || y < 0 || y > dungeon.height) return -1;
        // overlaps movement-blocker
        if ((dungeon.tileFlags[y][x] & ActiveArea.HAS_MOB)!=0)return 1;
        if ((dungeon.tileFlags[y][x] & ActiveArea.BLOCKS_MOVEMENT) != 0) return 2;
        this.x = x; this.y= y;
        return 0;
    }
    
    public int getSmellOnTile(){
        return dungeon.playerScent[y][x];
    }
    
    public Color getColorBack(){
        return colorBack;
    }
    public Color getColorFore(){
        return colorFore;
    }
    
    public DungeonTile getTileBelow(){
        return dungeon.tiles[y][x];
    }
    
}
