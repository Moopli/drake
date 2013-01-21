/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

import core.graphics.ASCIIPane;
import core.graphics.TextRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author filip
 */
public class MenuTest implements ActionListener {

    Menu topLevelMenu = new Menu(70, 60);
    final Menu mainMenu = new Menu(70, 60);
    final Menu gameMenu = new Menu(70, 60);
    final Menu instructions = new Menu(30, 8);
    Timer timer = new Timer(30, this);
    ASCIIPane pane = new ASCIIPane(topLevelMenu.graphics);

    public MenuTest() {
        topLevelMenu.isActive = true;

        topLevelMenu.addChild("mainmenu", mainMenu, 0, 0);
        mainMenu.graphics.fillSurface('.', Color.black, Color.red);
        mainMenu.isActive = true;

        topLevelMenu.addChild("gamemenu", gameMenu, 0, 0);
        gameMenu.graphics.fillSurface(' ', Color.white, Color.black);
        gameMenu.isActive = false;
        
        mainMenu.addChild("instructions", instructions, 20, 30);
        TextRenderer rules=new TextRenderer(20,10);
        rules.addString("Use numpad to move. The goblins will try to follow and attack you."
                + " If they walk into you, you will take damage and eventually  die."
                + " Try to reach the staircase at the opposite corner of the map to go to the next level.");
        rules.pane=instructions.graphics;
        rules.renderText();
        instructions.isActive = false;

        Button gm = new Button("New Game", new ButtonCallback() {
            @Override
            public void action() {
                gameMenu.isActive=true;
                mainMenu.isActive=false;
            }

            @Override
            public void unAction() {
            }
        }, new ButtonCallback() {
            @Override
            public void action() {
            }

            @Override
            public void unAction() {
            }
        });
        gm.isActive = true;
        mainMenu.addChild("gm", gm, 30, 10);
        
        Button inst = new Button("Instructions", new ButtonCallback() {
            @Override
            public void action() {
            }

            @Override
            public void unAction() {
            }
        }, new ButtonCallback() {
            boolean hovering=false;
            @Override
            public void action() {
                if (!hovering) {
                    hovering=true;
                    instructions.isActive=true;
                }
            }

            @Override
            public void unAction() {
                if (hovering) {
                    hovering = false;
                    instructions.isActive=false;
                }
            }
        });
        inst.isActive = true;
        mainMenu.addChild("inst", inst, 28, 20);
        
        Button mm = new Button("Main Menu", new ButtonCallback() {
            @Override
            public void action() {
                gameMenu.isActive=false;
                mainMenu.isActive=true;
            }

            @Override
            public void unAction() {
            }
        }, new ButtonCallback() {
            @Override
            public void action() {
            }

            @Override
            public void unAction() {
            }
        });
        mm.isActive = true;
        gameMenu.addChild("mm", mm, 60, 55);

        pane.setup();

        timer.start();
    }

    public static void main(String[] args) {
        new MenuTest();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        topLevelMenu.onHover(pane.getMouseX(), pane.getMouseY());
        if (pane.getMouseClicked()) {
            topLevelMenu.onClick();
        }
        gameMenu.graphics.fillSurface(' ', Color.white, Color.black);
        mainMenu.graphics.fillSurface('.', Color.black, Color.red);
        topLevelMenu.updateGraphics();
        pane.repaint();



    }
}