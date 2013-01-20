/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

import java.awt.Color;

/**
 *
 * @author filip
 */
public class Button extends InterfaceObject{
    
    String text;
    Color back = Color.black, fore = Color.lightGray, backHighlight = Color.darkGray, foreHighlight = Color.red;
    
    Menu target;
    
    
    public Button(int x, int y, String text, Menu target, InterfaceObject parent){
        super(x,y, text.length(), 1, parent);
        this.text = text;
        this.target = target;
    }
    
    @Override
    public void onHover(int mouseX, int mouseY){
        if (hovering = this.isHovering(mouseX, mouseY)){
            for (int i = 0; i < graphics.getWidth(); i++) {
                this.graphics.background[0][i] = backHighlight;
            }
            for (int i = 0; i < graphics.getWidth(); i++) {
                this.graphics.foreground_color[0][i] = foreHighlight;
            }
            
        } else {
            for (int i = 0; i < graphics.getWidth(); i++) {
                this.graphics.background[0][i] = back;
            }
            for (int i = 0; i < graphics.getWidth(); i++) {
                this.graphics.foreground_color[0][i] = fore;
            }
        }
    }
    
    @Override
    public void onClick(){
        // signal handler to switch target
    }
    
}
