/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

import core.graphics.TextSurface;

/**
 *
 * @author filip
 */
public class InterfaceObject {
    
    public TextSurface graphics;
    
    InterfaceObject parent = null;
    boolean hovering;
    public boolean isActive;
    
    public InterfaceObject(int w, int h, int x, int y, InterfaceObject parent){
        graphics = new TextSurface(w,h);
        graphics.x = x; graphics.y = y;
        this.parent = parent;
    }
    
    public boolean isHovering(int mouseX, int mouseY){
        if (mouseX < graphics.x || mouseX >= graphics.x + graphics.getWidth() ) return false;
        if (mouseY < graphics.y || mouseY >= graphics.y + graphics.getHeight()) return false;
        return true;
    }
    
    
    public void onHover(int mouseX, int mouseY){}
    
    public void onClick(){}
    
    public void updateGraphics(){}
}
