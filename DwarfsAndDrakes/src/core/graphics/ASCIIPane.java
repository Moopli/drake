/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 *
 * @author filip
 */
public class ASCIIPane extends JComponent implements ActionListener{
    
    JFrame frame;
    javax.swing.Timer timer;
    Font font;
    int rows = 50, columns = 70, char_width = 10, char_height = 16;
    
    TextSurface surface = new TextSurface(columns, rows);
    
    
    public ASCIIPane(){
        super();
        // Font and dimension setup
        font = new Font("Monospaced", Font.PLAIN, char_height);
        
        // test code
        surface.fillSurface('~', Color.red.darker().darker(), Color.red.darker());
        
        // boilerplate
        frame = new JFrame("ASCII Panel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columns * char_width, rows * char_height);
        frame.setResizable(false);
        Container c = frame.getRootPane();
        c.setLayout(new BorderLayout());
        c.add(this, BorderLayout.CENTER);
        timer = new javax.swing.Timer(30, this);
        frame.setVisible(true);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //repaint();
    }
    
    public static void main(String[] args) {
        new ASCIIPane();
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
    
    
}
