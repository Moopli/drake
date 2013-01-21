package core.objects.map.generation;

import data.DataRoot;
import java.io.*;
import java.net.URL;

/**
 *
 * @author Sepehr
 */
public class MapGenerator {
    
    public static final Class RESOURCE_TRUNK = DataRoot.class;
    
    public static final String PATH_PREFIX = "maps/";
    
    public static void generateMap(int w, int h, int goblinses, String path) {
        BSPRect dungeon = new BSPRect(0, 0, w, h);
        dungeon.partition(0.6);
        dungeon.joinRooms();
        
        int gobCount = 0;
        while (gobCount < goblinses){
            int x = (int)(Math.random() * dungeon.room.get(0).size());
            int y = (int)(Math.random() * dungeon.room.size());
            
            if (dungeon.room.get(y).get(x).equals(".")){
                dungeon.room.get(y).set(x, "g");
                gobCount++;
            }
        }
        
        boolean droppedPlayer = false;
        while (!droppedPlayer){
            int x = (int)(Math.random() * dungeon.room.get(0).size());
            int y = (int)(Math.random() * dungeon.room.size());
            
            if (dungeon.room.get(y).get(x).equals(".")){
                dungeon.room.get(y).set(x, "@");
                droppedPlayer = true;
            }
        }
        
        
        int stairCount = 0;
        while (stairCount < 3){
            int x = (int)(Math.random() * dungeon.room.get(0).size());
            int y = (int)(Math.random() * dungeon.room.size());
            
            if (dungeon.room.get(y).get(x).equals(".")){
                dungeon.room.get(y).set(x, "<");
                stairCount++;
            }
        }
        
        try {
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(dungeon.toString());
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        generateMap(40, 40, 12, "test.map");
    }
}