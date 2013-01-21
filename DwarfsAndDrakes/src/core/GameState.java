/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.graphics.*;
import core.menus.Menu;
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
    
    Menu gameScreen = new Menu(columns, rows);
    
    
    TextSurface surface = gameScreen.graphics;
    TextSurface overWorld = new TextSurface(20, 20);
    TextSurface bodyInterface = new TextSurface(15, 15);
    public static TextRenderer commandLine = new TextRenderer(30, 8);
    
    ASCIIPane graphics = new ASCIIPane(surface);
    
    ActiveArea map = new ActiveArea();
    Player player = new Player(5, 5, map);
    
    Scheduler scheduler = new Scheduler(10);
    HashMap<String, Mob> mobMap = new HashMap<String, Mob>();
    
    public GameState(){}
    
    // a test constructor integrating the game with another menu system
    public GameState(ASCIIPane graphics, Menu topLevelMenu){
        this.graphics = graphics;
        gameScreen.isActive = false;
        topLevelMenu.addChild("gamescreen", gameScreen, 0, 0);
    }
    
    public void test() {

        // interface setup
        overWorld.x = 3;
        overWorld.y = 3;
        
        bodyInterface.x = 26;
        bodyInterface.y = 3;
        bodyInterface.fillSurface('*', Color.black, Color.LIGHT_GRAY);
        
        commandLine.pane.x = 3;
        commandLine.pane.y = 26;
        commandLine.pane.fillSurface(' ', Color.black, Color.LIGHT_GRAY);
        
        
        surface.children.put("overworld", overWorld);
        surface.children.put("commandline", commandLine.pane);
        surface.children.put("bodyinterface", bodyInterface);
        surface.fillSurface('~', Color.red.darker().darker(), Color.red.darker());
        
        
        // map loading
        mobMap.put("player", player);
        mobMap.put("mob1", Monster.createGoblin(6, 6, map));
        mobMap.put("mob2", Monster.createGoblin(6, 7, map));
        mobMap.put("mob3", Monster.createGoblin(7, 6, map));
        mobMap.put("mob4", Monster.createGoblin(7, 7, map));
        
        
        for (String key : mobMap.keySet()){
            scheduler.enqueue(key, 1);
        }
        
        map.loadMap("test.map");
        for (Mob m : mobMap.values()){
            map.mappables.add(m.getMapRepresentation());
        }
        map.updateBitMasks();
        map.displayTo(overWorld, 5, 6);
        surface.update();


        graphics.setup();

        graphics.frame.addKeyListener((PlayerAI) player.getController());
        graphics.frame.addKeyListener(commandLine);
        
        
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
                System.out.println("interrupted -- this should never happen...."
                        + " THIS SHOULD NEVER HAPPEN WHAT IS WRONG WITH THE WORLD "
                        + "AAAAH"); 
            }
        }
    }
    
    String last = null;
    int lastPos = 0;
    
    public void step() throws InterruptedException {
        map.updateBitMasks();
        map.updateLOS(player.getMapRepresentation().x, player.getMapRepresentation().y);
        commandLine.render();
        graphics.repaint();
        
        //System.out.println("herp");
        last = scheduler.oneTick(last, lastPos);
        
        if (last == "player") {
            // special player handling
            lastPos = 0;
            map.updateScent(player.getMapRepresentation().x, player.getMapRepresentation().y);
            while (lastPos == 0) {
                lastPos = player.getController().think();
                //System.out.println(lastPos);
                graphics.repaint();
                //System.out.println("derp");
                Thread.sleep(70);
            }
        } else if (last != null) {
            lastPos = this.mobMap.get(last).getController().think(); // this handles all mob thinking
            //System.out.println(last + " is thinking");
            commandLine.addString(last + " is thinking");
            //System.out.println(last + " is at " + this.mobMap.get(last).getMapRepresentation().x + ", " + this.mobMap.get(last).getMapRepresentation().y);
            commandLine.addString(last + " is at " + this.mobMap.get(last).getMapRepresentation().x + ", " + this.mobMap.get(last).getMapRepresentation().y);
            Thread.sleep(30);
        }
        
        overWorld.fillSurface(' ', Color.black, Color.black);
        map.displayTo(overWorld, player.getMapRepresentation().x, player.getMapRepresentation().y);
    }
}
