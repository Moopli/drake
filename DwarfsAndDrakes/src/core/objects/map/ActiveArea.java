/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import java.util.*;
import java.io.*;
/**
 *
 * @author 301916706
 */
public class ActiveArea {
    
    HashSet<Mappable> mappables = new HashSet<Mappable>();
    
    int width = 10, height = 10;
    
    DungeonTile[][] tiles = new DungeonTile[height][width];
    
    /**
     * Flags for tileFlags bitmasks.
     */
    public static final int HAS_OBJECT = 1, HAS_MONSTER = 2, HAS_ITEM = 4,
            BLOCKS_LIGHT = 8, BLOCKS_MOVEMENT = 16, AIRTIGHT = 32;
    
    /**
     * An array of boolean bitmasks.
     */
    int[][] tileFlags = new int[height][width];
    
    /**
     * The strength of the player's odor in each square (possibly zero)
     */
    int[][] playerScent = new int[height][width];
    
    
    /*
     * -- TODO -- 
     * - LOS (note -- monsters only need to LOS player, so we can just calc 
     *   player LOS for everything)
     * - Scent
     * 
     */
    
    /**
     * The farthest distance smell "diffuses" from the player in a turn.
     */
    public static final int MAX_SMELL_DISTANCE = 7;
    
    /**
     * Updates the scent on every tile -- scent decays over time, and spreads 
     * from the player.
     * @param px
     * @param py 
     */
    public void updateScent(int px, int py){
        for (int y = 0; y < playerScent.length; y++){
            for (int x = 0; x < playerScent[y].length; x++){
                // diffuse scent from player -- unless the player isn't nearby, 
                // in which case we just decay the smell
                playerScent[y][x] = playerLOS[y][x] <= MAX_SMELL_DISTANCE? 
                        MAX_SMELL_DISTANCE - playerLOS[y][x] : 
                        (playerScent[y][x] == 0 ? 0 : playerScent[y][x]-1);
            }
        }
        
        
    }
    
    /**
     * Each location contains the distance to the player -- unless there is no 
     * line-of-sight between the two, in which case the location contains -1.
     */
    int[][] playerLOS = new int[height][width];
    
    public static int PLAYER_LOS = 7;
    
    public void updateLOS(int px, int py){
        // clear array
        for (int y = 0; y < playerLOS.length; y++){
            for (int x = 0; x < playerLOS[y].length; x++){
                playerLOS[y][x] = -1;
            }
        }
        
        // now for a short flood fill
        LinkedList floodQueue = new LinkedList();
        
        
        
        
        
        
    }
    
    public void loadMap(String filename) {
        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            int c=0;
            while ((strLine = br.readLine()) != null) {
                for (int i = 0; i < strLine.length(); i++) {
                    char tile=strLine.charAt(i);
                    if (tile=='#') {
                        tiles[c][i]=DungeonTile.WALL;
                    }
                    else if (tile=='.'){
                        tiles[c][i]=DungeonTile.FLOOR;
                    }
                }
                // Print the content on the console
                c++;
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
    
}
