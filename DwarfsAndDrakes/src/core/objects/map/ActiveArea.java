/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import core.graphics.TextSurface;
import java.awt.Color;
import java.util.*;
import java.io.*;
/**
 *
 * @author 301916706
 */
public class ActiveArea {
    
    public HashSet<Mappable> mappables = new HashSet<Mappable>();
    
    int width = 10, height = 10;
    
    DungeonTile[][] tiles = new DungeonTile[height][width];
    
    /**
     * Flags for tileFlags bitmasks.
     */
    public static final int HAS_OBJECT = 1, HAS_MOB = 2, HAS_ITEM = 4,
            BLOCKS_LIGHT = 8, BLOCKS_MOVEMENT = 16, AIRTIGHT = 32, WAS_SIGHTED = 64;
    
    /**
     * An array of boolean bitmasks.
     */
    int[][] tileFlags = new int[height][width];
    
    /**
     * The higher the number, the bloodier the floor
     */
    int[][] gore = new int[height][width];
    
    public void dropGore(int x, int y, int amt){
        gore[Math.max(0, Math.min(y, gore.length-1))][Math.max(0, Math.min(x, gore[0].length-1))] += amt;
    }
    
    public ActiveArea(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                tiles[i][j] = DungeonTile.FLOOR;
            }
        }
    }
    
    public void updateBitMasks(){
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                tileFlags[y][x] = tileFlags[y][x] & WAS_SIGHTED;
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
        // visuals yay
        for (int i = 0; i < this.gore.length; i++) {
            for (int j = 0; j < this.gore[i].length; j++) {
                gore[i][j] = gore[i][j] <= 0? 0 : gore[i][j] - 1;
            }
        }
    }
    
    /**
     * The strength of the player's odor in each square (possibly zero)
     */
    public int[][] playerScent = new int[height][width];
    
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
                playerScent[y][x] = (playerLOS[y][x] <= MAX_SMELL_DISTANCE &playerLOS[y][x] != -1)? // is player nearby?
                        (MAX_SMELL_DISTANCE - playerLOS[y][x])*10 : // then he leaves his scent
                        (playerScent[y][x] <= 0 ? 0 : playerScent[y][x]-1); // otherwise the scent decays
            }
        }
    }
    
    public int getScent(int x, int y){
        return playerScent[y][x];
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
            
            // turns off fog of war for tile
            tileFlags[(int)point[1]][(int)point[0]] |= WAS_SIGHTED;
            
            if ((tileFlags[(int)point[1]][(int)point[0]] & BLOCKS_LIGHT) != 0) continue; //can't see/smell past
            
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
        
        ArrayList<ArrayList<DungeonTile>> map = new ArrayList<ArrayList<DungeonTile>>();
        
        try {
            
            FileInputStream fstream = new FileInputStream(filename);
            
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            int y = 0;
            int x = 0;
            while ((strLine = br.readLine()) != null) {
                ArrayList<DungeonTile> line = new ArrayList<DungeonTile>();
                x = 0;
                for (char tile: strLine.toCharArray()) {
                    for (DungeonTile dt : DungeonTile.values()){
                        if (tile == dt.getCh()){
                            line.add(dt);
                        } 
                    }
                    if (tile == '@'){
                        Mappable m = new Mappable();
                        m.x = x;
                        m.y = y;
                        m.img = '@';
                        m.colorBack = Color.BLACK;
                        m.colorFore = Color.WHITE;
                        m.tileFlags |= ActiveArea.BLOCKS_MOVEMENT|ActiveArea.HAS_MOB;
                        m.dungeon = this;
                        mappables.add(m);
                        line.add(DungeonTile.FLOOR);
                    } else if (tile == 'g'){
                        Mappable m = new Mappable();
                        m.x = x;
                        m.y = y;
                        m.img = 'g';
                        m.colorBack = Color.BLACK;
                        m.colorFore = Color.GREEN.darker().darker();
                        m.tileFlags |= ActiveArea.BLOCKS_MOVEMENT|ActiveArea.HAS_MOB;
                        m.dungeon = this;
                        mappables.add(m);
                        line.add(DungeonTile.FLOOR);
                    } 
                    x++;
                }
                y++;
                map.add(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // set up arrays
        this.tiles = new DungeonTile[map.size()][map.get(0).size()];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j]  = map.get(i).get(j);
            }
        }
        this.height = this.tiles.length;
        this.width = this.tiles[0].length;
        this.playerLOS = new int[height][width];
        this.playerScent = new int[height][width];
        this.tileFlags = new int[height][width];
        this.gore = new int[height][width];
        this.updateBitMasks();
        
        
    }
    
    /**
     * Renders the map onto a given text surface, where the point 
     * (centerX, centerY) is transferred to the center of the surface.
     * @param surface 
     * @param centerX 
     * @param centerY
     */
    public void displayTo(TextSurface surface, int centerX, int centerY){
        int cam_x = centerX > surface.getWidth()/2 ? centerX - surface.getWidth()/2 : 0;
        int cam_y = centerY > surface.getHeight()/2 ? centerY - surface.getHeight()/2 : 0;
        for (int x = Math.max(0, cam_x); x < Math.min(this.width,cam_x + surface.getWidth() ); x++) {
            for (int y = Math.max(0, cam_y); y < Math.min(this.height,cam_y + surface.getHeight() ); y++) {
                // x and y iterate through all points on this surface which are within the output surface
                if ((tileFlags[y][x] & WAS_SIGHTED) == 0) continue;
                //System.out.println("" + y + " " + x + " " + this.tiles[y][x]);
                surface.setChar(x - cam_x, y - cam_y, this.tiles[y][x].getCh());
                if (playerLOS[y][x] == -1){
                    surface.setColorFore(x - cam_x, y - cam_y, this.tiles[y][x].getCharColor().darker().darker());
                    surface.setColorBack(x - cam_x, y - cam_y, this.tiles[y][x].getColorBack().darker().darker());
                } else {
                    surface.setColorFore(x - cam_x, y - cam_y, this.tiles[y][x].getCharColor());
                    //System.out.println(gore[y][x]);
                    if (gore[y][x] > 15){
                        surface.setColorBack(x - cam_x, y - cam_y, new Color(Math.min(this.gore[y][x], 125), 0, 0));
                    }else {
                        surface.setColorBack(x - cam_x, y - cam_y, this.tiles[y][x].getColorBack());
                    }
                }
                //Color scentColor = new Color(playerScent[y][x] * 2, playerScent[y][x] * 2, 0);
                //surface.setColorBack(x - cam_x, y - cam_y, scentColor); // uncomment background lines above and comment this out
            }
        }
        
        for (Mappable m : mappables){
            // skip renderin this mappable if out of LOS 
            if (playerLOS[m.y][m.x] == -1) {
                //System.out.println("skipped out of LOS " + m.img);
                continue;
            }
            // skip rendering this mappable if it's out of the bounds
            if (m.x < cam_x || m.x >  cam_x + surface.getWidth() || m.y < cam_y || m.y > cam_y + surface.getHeight()){
                //System.out.println("skipped out of bounds " + m.img);
                continue;
            }
            //System.out.println(m.img);
            
            surface.setChar(m.x - cam_x, m.y - cam_y, m.img);
            surface.setColorFore(m.x - cam_x, m.y - cam_y, m.getColorFore());
            surface.setColorBack(m.x - cam_x, m.y - cam_y, m.getColorBack());
        }
        
    }
    
    public Mappable getMappableAt(int x, int y){
        if ((this.tileFlags[y][x] & ActiveArea.HAS_OBJECT) == 0) return null;
        if (mappables.isEmpty()) return null;
        for (Mappable m : mappables){
            if (m.x == x && m.y == y){
                return m;
            }
        }
        return null;
    }
}
