/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

/**
 * The callback that buttons use to do their actions.
 * @author filip
 */
public interface ButtonCallback {
    
    public void action();
    /**
     * Sometimes it's necessary to undo past mistakes. For those times, the 
     * unAction callback is for you! Call 1-888 888 8888 today, and get your 
     * very own!
     */
    public void unAction();
}
