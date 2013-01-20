/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.graphics;

import core.menus.InterfaceObject;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *
 * @author filip
 */
public class TextRenderer extends InterfaceObject implements KeyListener {
    
    public TextSurface pane;
    
    public ArrayList<String> strings = new ArrayList<String>();
    
    
    public TextRenderer(int lineSize, int rows){
        super(lineSize, rows, 0, 0, null);
        strings.add("");
        pane = new TextSurface(lineSize, rows);
        isActive = true;
    }
    
    
    public void render(){
        System.out.println(strings);
        pane.fillSurface(' ', Color.black, Color.BLACK);
        for (int i = 0; i < pane.rows && i < strings.size(); i++) {
            drawString(strings.get(strings.size() - i - 1), pane, 0, pane.rows - i - 1, Color.WHITE, Color.BLACK);
        }
        if (isActive){
            pane.setColorBack(strings.get(strings.size()-1).length(), pane.rows-1, Color.red);
        }
    }
    
    /**
     * Adds a string to the strings buffer, splitting the string between words 
     * to wrap.
     * @param s 
     */
    public void addString(String s){
        StringBuilder slice = new StringBuilder();
        String[] ar = s.split(" ");
        int length = 0;
        for (int i = 0; i < ar.length; i++) {
            length += ar[i].length();
            if (length >= pane.columns ){
                length = 0;
                strings.add(slice.toString());
                slice.delete(0, slice.capacity());
            }
            slice.append(ar[i]);
            slice.append(" ");
        }
        strings.add(slice.toString());
    }
    
    /**
     * Draws a given line of text on a given TextSurface at the given cursor 
     * start position with the given foreground and background colors.
     * @param s
     * @param pane
     * @param x
     * @param y
     * @param fore
     * @param back 
     */
    public static void drawString(String s, TextSurface pane, int x, int y, Color fore, Color back){
        assert (y < pane.rows && x < pane.columns && y > 0 && x > 0);
        for (int i = 0; i < s.length() && i < pane.columns - x; i++) {
            pane.setChar(x + i, y, s.charAt(i));
            pane.setColorBack(x + i, y, back);
            pane.setColorFore(x + i, y, fore);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (isActive){
            if (e.getKeyChar() =='\n'){
                strings.add("");
            } else {
                strings.add(strings.remove(strings.size()-1) + e.getKeyChar());
            }
            render();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
