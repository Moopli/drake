/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.graphics;

import java.awt.*;
import java.util.*;

/**
 *
 * @author filip
 */
public class TextSurface {
    
    public boolean isActive = true;
    
    int rows, columns; public int x = 0, y = 0;
    public HashMap<String, TextSurface> children = new HashMap<String, TextSurface>();
    
    public Color[][] background;
    public char[][] foreground;
    public Color[][] foreground_color;
    
    public TextSurface(int width, int height){
        rows = height; columns = width;
        background = new Color[rows][columns];
        foreground = new char[rows][columns];
        foreground_color = new Color[rows][columns];
        
        
        for (int x = 0; x < columns; x++){
            for (int y = 0; y < rows; y ++){
                background[y][x] = Color.BLACK;
                foreground[y][x] = ' ';
                foreground_color[y][x] = Color.GRAY;
            }
        }
        
        
    }
    
    /**
     * Recursively updates all changes made to children onto this surface.
     */
    public void update(){
        for (TextSurface surface: children.values()){
            if (!surface.isActive) continue;
            surface.update();
            for (int i  = 0; i < surface.columns && i + surface.x < this.columns; i++){
                for (int j = 0; j < surface.rows && j + surface.y < this.rows; j ++){
                    this.background[j + surface.y][i + surface.x] = surface.background[j][i];
                    this.foreground[j + surface.y][i + surface.x] = surface.foreground[j][i];
                    this.foreground_color[j + surface.y][i + surface.x] = surface.foreground_color[j][i];
                }
            }
        }
    }
    
    
    /**
     * Fills a surface with the given char, background color, and foreground color.
     * @param ch 
     */
    public void fillSurface(char ch, Color back, Color fore){
        for (int x = 0; x < columns; x++){
            for (int y = 0; y < rows; y ++){
                background[y][x] = back;
                foreground[y][x] = ch;
                foreground_color[y][x] = fore;
            }
        }
    }
    
    public void setChar(int x, int y, char ch){
        if (y < 0 || x < 0 || y >= this.rows || x >= this.columns) return;
        this.foreground[y][x] = ch;
    }
    
    public void setColorFore(int x, int y, Color c){
        if (y < 0 || x < 0 || y >= this.rows || x >= this.columns) return;
        this.foreground_color[y][x] = c;
    }
    
    public void setColorBack(int x, int y, Color c){
        if (y < 0 || x < 0 || y >= this.rows || x >= this.columns) return;
        this.background[y][x] = c;
    }
    
    
    public int getWidth(){
        return columns;
    }
    
    public int getHeight(){
        return rows;
    }
}
