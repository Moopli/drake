/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.graphics.*;
import core.objects.*;
import core.objects.ai.PlayerAI;
import core.objects.map.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class GameState {

    int rows = 50, columns = 70;
    TextSurface surface = new TextSurface(columns, rows);
    ActiveArea map = new ActiveArea();
    Player player = new Player(5, 5, map);
    TextSurface ovw;
    ASCIIPane graphics = new ASCIIPane(surface);
    Scheduler scheduler = new Scheduler(10);
    HashMap<String, Mob> mobMap = new HashMap<String, Mob>();

    public void test() {

        mobMap.put("player", player);
        scheduler.enqueue("player", 1);



        ovw = new TextSurface(20, 20);
        ovw.x = 3;
        ovw.y = 3;

        surface.children.put("overworld", ovw);

        surface.fillSurface('~', Color.red.darker().darker(), Color.red.darker());
        map.loadMap("test.map");
        map.mappables.add(player.getMapRepresentation());
        map.updateBitMasks();
        map.displayTo(ovw, 5, 6);
        surface.update();


        graphics.setup();

        graphics.frame.addKeyListener((PlayerAI) player.getController());

        gameLoop();
    }

    public static void main(String[] args) {
        new GameState().test();
    }

    public void gameLoop() {

        while (true) {
            try {
                step();
            } catch (InterruptedException ex) {
                System.out.println("interrupted");
            }
        }
    }
    
    String last = null;
    int lastPos = 0;
    
    public void step() throws InterruptedException {
        map.updateBitMasks();
        map.updateLOS(player.getMapRepresentation().x, player.getMapRepresentation().y);
        graphics.repaint();
        
        //System.out.println("herp");
        last = scheduler.oneTick(last, lastPos);
        
        if (last == "player") {
            // special player handling
            lastPos = 0;
            map.updateScent(player.getMapRepresentation().x, player.getMapRepresentation().y);
            while (lastPos == 0) {
                lastPos = player.getController().think();
                System.out.println(lastPos);
                graphics.repaint();
                //System.out.println("derp");
                Thread.sleep(70);
            }
        } else if (last != null) {
            lastPos = this.mobMap.get(last).getController().think(); // this handles all mob thinking
        }
        
        ovw.fillSurface(' ', Color.black, Color.black);
        map.displayTo(ovw, player.getMapRepresentation().x, player.getMapRepresentation().y);
    }
}
