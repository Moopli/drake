/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.graphics;

import java.awt.event.*;
import java.awt.*;
import java.util.*;


/**
 * Have to think this over -- is an object which flings all events into a queue 
 * really that good of an idea? The only thing this really should do is convert 
 * mouse events into corresponding events acting over the ASCII grid.
 * 
 * @author filip
 */
@Deprecated
public class EventHandler implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
    
    LinkedList<AWTEvent> event_queue = new LinkedList<AWTEvent>();
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    public int CHAR_W = 10, CHAR_H = 16, ROWS = 50, COLS = 70;

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    
}

/**
 * Not too sure why this exists
 * @author filip
 */
class ArrayClickEvent extends AWTEvent{
    
    public ArrayClickEvent(Object source, int id){
        super(source, id);
        
    }
    
    
    
}