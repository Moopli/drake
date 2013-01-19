/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import core.graphics.TextSurface;
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
            BLOCKS_LIGHT = 8, BLOCKS_MOVEMENT = 16, AIRTIGHT = 32, WAS_SIGHTED = 64;
    
    /**
     * An array of boolean bitmasks.
     */
    int[][] tileFlags = new int[height][width];
    
    /**
     * The strength of the player's odor in each square (possibly zero)
     */
    int[][] playerScent = new int[height][width];
    
    
    public void updateBitMasks(){
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                tileFlags[y][x] = 0;
                tileFlags[y][x] |= tiles[y][x] == DungeonTile.WALL? 
                        BLOCKS_MOVEMENT | BLOCKS_LIGHT | AIRTIGHT: // walls let nothing through
                        0;
                tileFlags[y][x] |= tiles[y][x] == DungeonTile.TRANSLUCENT? 
                        BLOCKS_MOVEMENT | AIRTIGHT: // translucent walls let only light through
                        0;
            }
        }
        
        for (Mappable m : mappables){
            tileFlags[m.y][m.x] |= m.tileFlags;
        }
    }
    
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
                playerScent[y][x] = playerLOS[y][x] <= MAX_SMELL_DISTANCE? // is player nearby?
                        MAX_SMELL_DISTANCE - playerLOS[y][x] : // then he leaves his scent
                        (playerScent[y][x] <= 0 ? 0 : playerScent[y][x]-1); // otherwise the scent decays
            }
        }
    }
    
    /**
     * Each location contains the distance to the player -- unless there is no 
     * line-of-sight between the two, in which case the location contains -1.
     */
    int[][] playerLOS = new int[height][width];
    
    public static int PLAYER_LOS = 7; 
    // this and smell distance being separate variables may cause problems.
    
    
    private static float ROOT2 = (float)Math.sqrt(2);
    
    /**
     * Computes a floodfill LOS up to PLAYER_LOS away from player.
     * @param px Player's x position.
     * @param py PLayer's y position.
     */
    public void updateLOS(int px, int py){
        // clear array
        for (int y = 0; y < playerLOS.length; y++){
            for (int x = 0; x < playerLOS[y].length; x++){
                playerLOS[y][x] = -1;
            }
        }
        
        // now for a short flood fill
        LinkedList<float[]> floodQueue = new LinkedList<float[]>();
        
        floodQueue.add(new float[]{px, py, 0}); // location and distance to player
        while (!floodQueue.isEmpty()){
            float[] point = floodQueue.removeFirst();
            if (point[2] > PLAYER_LOS)continue;
            if (point[0] < 0 || point[0] >= this.width) continue; // x bound
            if (point[1] < 0 || point[1] >= this.height) continue; // y bound
            if (playerLOS[(int)point[1]][(int)point[0]] != -1 && 
                    playerLOS[(int)point[1]][(int)point[0]] < point[2]) continue; // already flooded a shorter way
            // can be seen
            tileFlags[(int)point[1]][(int)point[0]] |= WAS_SIGHTED;
            if ((tileFlags[(int)point[1]][(int)point[0]] & BLOCKS_LIGHT) == 0) continue; //can't see/smell past
            
            // do actual LOS update
            playerLOS[(int)point[1]][(int)point[0]] = (int)point[2];
            
            // add 4 edge neighbors
            floodQueue.add(new float[]{point[0] + 1, point[1], point[2] + 1});
            floodQueue.add(new float[]{point[0] - 1, point[1], point[2] + 1});
            floodQueue.add(new float[]{point[0], point[1] + 1, point[2] + 1});
            floodQueue.add(new float[]{point[0], point[1] - 1, point[2] + 1});
            // add four corner neighbors
            floodQueue.add(new float[]{point[0] + 1, point[1] + 1, point[2] + ROOT2});
            floodQueue.add(new float[]{point[0] + 1, point[1] - 1, point[2] + ROOT2});
            floodQueue.add(new float[]{point[0] - 1, point[1] + 1, point[2] + ROOT2});
            floodQueue.add(new float[]{point[0] - 1, point[1] - 1, point[2] + ROOT2});
        }
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
    
    /**
     * 
     * @param surface 
     * @param centerX 
     * @param centerY
     */
    public void displayTo(TextSurface surface, int centerX, int centerY){
        int cam_x = centerX > surface.getWidth()/2 ? centerX : surface.getWidth()/2;
        int cam_y = centerY > surface.getHeight()/2 ? centerY : surface.getHeight()/2;
        for (int x = Math.max(0, cam_x - surface.getWidth()/2); x < Math.min(this.width,cam_x + surface.getWidth()/2 ); x++) {
            for (int y = Math.max(0, cam_y - surface.getHeight()/2); y < Math.min(this.height,cam_y + surface.getHeight()/2 ); y++) {
                // x and y iterate through all points on this surface which are within the output surface
                if ((tileFlags[y-cam_y][x-cam_x] & WAS_SIGHTED) == 0) continue;
                surface.setChar(x - cam_x, y - cam_y, this.tiles[y][x].getCh());
                surface.setColorFore(x - cam_x, y - cam_y, this.tiles[y][x].getCharColor());
                surface.setColorBack(x - cam_x, y - cam_y, this.tiles[y][x].getColorBack());
            }
        }
        
        for (Mappable m : mappables){
            // skip if out of LOS 
            if (playerLOS[m.y][m.x] == -1) continue;
            surface.setChar(m.x, m.y, m.img);
            surface.setColorFore(m.x, m.y, m.getColorFore());
            surface.setColorBack(m.x, m.y, m.getColorBack());
        }
        
    }
}
