/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

/**
 *
 * @author 301916706
 */
public class ActiveArea {
    
    
    
    int width = 10, height = 10;
    
    DungeonTile[][] tiles = new DungeonTile[width][height];
    
    /**
     * Flags for tileFlags bitmasks.
     */
    public static final int HAS_OBJECT = 1, HAS_MONSTER = 2, HAS_ITEM = 4,
            BLOCKS_LIGHT = 8, BLOCKS_MOVEMENT = 16;
    
    /**
     * An array of boolean bitmasks.
     */
    int[][] tileFlags = new int[width][height];
    
    
    
}
