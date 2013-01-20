/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.graphics;

import core.objects.Player;
import core.objects.ai.PlayerAI;
import core.objects.map.ActiveArea;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 *
 * @author filip
 */
public class ASCIIPane extends JComponent {
    
    public JFrame frame;
    javax.swing.Timer timer;
    Font font;
    int rows = 50, columns = 70; int char_width = 16, char_height = 16;
    
    TextSurface surface = new TextSurface(columns, rows);
    
    ActiveArea map = new ActiveArea();
    
    Player player = new Player(5,5, map);
    
    TextSurface ovw;
    
    public ASCIIPane(TextSurface main_surface){
        super();
        // Font and dimension setup
        font = new Font("Monospaced", Font.PLAIN, char_height);
        
        surface = main_surface;
    }
    
    @Override
    public void paintComponent(Graphics g){
        surface.update();
        for (int x = 0; x < columns; x++){
            for (int y = 0; y < rows; y ++){
                g.setColor(surface.background[y][x]);
                g.fillRect(x * char_width, y * char_height, char_width, char_height);
                g.setColor(surface.foreground_color[y][x]);
                g.drawString("" + surface.foreground[y][x], x * char_width, (y + 1) * char_height);
                
            }
        }
    }
    
    public final void setup(){
        frame = new JFrame("Of Narwhales and Manticores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columns * char_width, rows * char_height);
        frame.setResizable(false);
        Container c = frame.getRootPane();
        c.setLayout(new BorderLayout());
        c.add(this, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
