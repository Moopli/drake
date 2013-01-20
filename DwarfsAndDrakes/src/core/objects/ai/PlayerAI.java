/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author filip
 */
public class PlayerAI extends AIController implements KeyListener {
    
    Command nextMove = Command.NOTYET;
    
    @Override
    public int think() {
        //System.out.println(nextMove);
        switch (nextMove){
            case N:
                controlled.goN();
                break;
            case S:
                controlled.goS();
                break;
            case E:
                controlled.goE();
                break;
            case W:
                controlled.goW();
                break;
            case NE:
                controlled.goNE();
                break;
            case SE:
                controlled.goSE();
                break;
            case NW:
                controlled.goNW();
                break;
            case SW:
                controlled.goSW();
                break;
            case WAIT:
                break;
            default:
                // wait for input, think again
                return 0;
        }
        nextMove = Command.NOTYET;
        return 4;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
    if (nextMove != Command.NOTYET) return;
        switch (e.getKeyCode()){
            case KeyEvent.VK_NUMPAD1:
                nextMove = Command.SW;
                break;
            case KeyEvent.VK_NUMPAD2:
                nextMove = Command.S;
                break;
            case KeyEvent.VK_NUMPAD3:
                nextMove = Command.SE;
                break;
            case KeyEvent.VK_NUMPAD4:
                nextMove = Command.W;
                break;
            case KeyEvent.VK_NUMPAD5:
                nextMove = Command.WAIT;
                break;
            case KeyEvent.VK_NUMPAD6:
                nextMove = Command.E;
                break;
            case KeyEvent.VK_NUMPAD7:
                nextMove = Command.NW;
                break;
            case KeyEvent.VK_NUMPAD8:
                nextMove = Command.N;
                break;
            case KeyEvent.VK_NUMPAD9:
                nextMove = Command.NE;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    
    
}
