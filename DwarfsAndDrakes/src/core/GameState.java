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

/**
 *
 * @author filip
 */
public class GameState {

    int rows = 50, columns = 70;
    TextSurface surface = new TextSurface(columns, rows);
    TextSurface overWorld = new TextSurface(20, 20);
    TextSurface bodyInterface = new TextSurface(15, 15);
    TextSurface commandLine = new TextSurface(30, 8);
    
    ASCIIPane graphics = new ASCIIPane(surface);
    
    ActiveArea map = new ActiveArea();
    Player player = new Player(5, 5, map);
    
    Scheduler scheduler = new Scheduler(10);
    HashMap<String, Mob> mobMap = new HashMap<String, Mob>();

    public void test() {

        mobMap.put("player", player);
        scheduler.enqueue("player", 1);

        overWorld.x = 3;
        overWorld.y = 3;
        
        bodyInterface.x = 26;
        bodyInterface.y = 3;
        bodyInterface.fillSurface('*', Color.black, Color.LIGHT_GRAY);
        
        commandLine.x = 3;
        commandLine.y = 26;
        commandLine.fillSurface('-', Color.black, Color.LIGHT_GRAY);
        
        
        surface.children.put("overworld", overWorld);
        surface.children.put("commandline", commandLine);
        surface.children.put("bodyinterface", bodyInterface);
        
        
        surface.fillSurface('~', Color.red.darker().darker(), Color.red.darker());
        map.loadMap("test.map");
        map.mappables.add(player.getMapRepresentation());
        map.updateBitMasks();
        map.displayTo(overWorld, 5, 6);
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
        
        overWorld.fillSurface(' ', Color.black, Color.black);
        map.displayTo(overWorld, player.getMapRepresentation().x, player.getMapRepresentation().y);
    }
}
