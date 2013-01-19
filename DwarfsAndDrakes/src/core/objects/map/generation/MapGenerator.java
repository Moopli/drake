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
    
    public static void generateMap(int w, int h, String path) {
        BSPRect dungeon = new BSPRect(0, 0, w, h);
        dungeon.partition(0.6);
        dungeon.joinRooms();
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
        generateMap(40, 40, "test.map");
    }
}