package core.objects.map.generation;

import java.io.*;

/**
 *
 * @author Sepehr
 */
public class MapGenerator {

    public void generateMap() {
        BSPRect dungeon = new BSPRect(0, 0, 40, 40);
        dungeon.partition(0.6);
        dungeon.joinRooms();
        try {
            FileWriter fstream = new FileWriter("map.map");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(dungeon.toString());
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}