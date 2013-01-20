/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menus;

import java.awt.Color;

/**
 * A GUI button which acts through a clickCallback.
 * @author filip
 */
public class Button extends InterfaceObject {
    
    String text;
    Color back = Color.black, fore = Color.lightGray, backHighlight = Color.darkGray, foreHighlight = Color.red;
    
    ButtonCallback clickCallback;
    ButtonCallback hoverCallback;
    
    
    public Button(String text, ButtonCallback clickCallback, ButtonCallback hoverCallback){
        super(text.length(), 1);
        this.text = text;
        this.clickCallback = clickCallback;
        this.hoverCallback = hoverCallback;
        for (int i = 0; i < text.length(); i++) {
            graphics.foreground[0][i] = text.charAt(i);
        }
    }
    
    @Override
    public void onHover(int mouseX, int mouseY){
        hovering = this.isHovering(mouseX, mouseY);
        if (hoverCallback == null) return;
        if (hovering){
            hoverCallback.action();
        } else {
            hoverCallback.unAction();
        }
    }
    
    @Override
    public void onClick(){
        if (clickCallback == null) return;
        if (hovering) {
            clickCallback.action();
        }
        else {
            clickCallback.unAction();
        }
    }
    
    @Override
    public void updateGraphics(){
        if (hovering){
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
}
