/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

import core.graphics.ASCIIPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author filip
 */
public class MenuTest implements ActionListener {
    
    Menu topLevelMenu = new Menu(30, 40);
    Timer timer = new Timer(30, this);
    
    ASCIIPane pane = new ASCIIPane(topLevelMenu.graphics);
    
    public MenuTest(){
        topLevelMenu.isActive = true;
        topLevelMenu.graphics.fillSurface('~', Color.black, Color.red);
        
        Button button = new Button("A button", new ButtonCallback(){

            @Override
            public void action() {
                System.out.println("did a click action");
            }

            @Override
            public void unAction() {
                System.out.println("did a click unaction");
            }
        }, new ButtonCallback(){
            boolean hovering= false;
            @Override
            public void action() {
                if (!hovering) {
                    hovering = true;
                    System.out.println("did an action -- hover");
                }
            }

            @Override
            public void unAction() {
                if (hovering) {
                    hovering = false;
                    System.out.println("did an unaction -- unhover");
                }
            }
        });
        
        button.isActive = true;
        
        topLevelMenu.addChild("button", button, 5, 5);
        
        pane.setup();
        
        timer.start();
    }
    
    
    public static void main(String[] args){
        new MenuTest();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        topLevelMenu.onHover(pane.getMouseX(), pane.getMouseY());
        if (pane.getMouseClicked()) {
            topLevelMenu.onClick();
        }
        topLevelMenu.updateGraphics();
        pane.repaint();
        
        
        
    }
    
    
}
