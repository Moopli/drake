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
public class ASCIIPane extends JComponent implements ActionListener{
    
    JFrame frame;
    javax.swing.Timer timer;
    Font font;
    int rows = 50, columns = 70, char_width = 10, char_height = 16;
    
    TextSurface surface = new TextSurface(columns, rows);
    
    ActiveArea map= new ActiveArea();
    
    Player player = new Player(5,5, map);
    
    TextSurface ovw;
        
    public ASCIIPane(){
        super();
        // Font and dimension setup
        font = new Font("Monospaced", Font.PLAIN, char_height);
        
        // test code
        ovw = new TextSurface(20,20);
        ovw.x = 3; ovw.y = 3;
        
        surface.children.put("overworld", ovw);
        
        surface.fillSurface('~', Color.red.darker().darker(), Color.red.darker());
        map.loadMap("test.map");
        map.mappables.add(player.getMapRepresentation());
        map.updateBitMasks();
        map.updateLOS(5, 6);
        map.updateLOS(14, 12);
        map.displayTo(ovw, 5, 6);
        
        surface.update();
        
        // boilerplate
        frame = new JFrame("Of Narwhales and Manticores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columns * char_width, rows * char_height);
        frame.setResizable(false);
        Container c = frame.getRootPane();
        c.setLayout(new BorderLayout());
        c.add(this, BorderLayout.CENTER);
        timer = new javax.swing.Timer(30, this);
        frame.setVisible(true);
        
        frame.addKeyListener((PlayerAI)player.getController());
        
        timer.start();
    }
    
    /*
     * Note -- this is not the place the main loop should be -- the main loop 
     * should be with the object manager. this is just a test.
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        map.updateBitMasks();
        map.updateLOS(player.getMapRepresentation().x, player.getMapRepresentation().y);
        player.getController().think(); //
        ovw.fillSurface(' ', Color.black, Color.black);
        map.displayTo(ovw, player.getMapRepresentation().x, player.getMapRepresentation().y);
        System.out.println(player.getMapRepresentation().x  + " " + player.getMapRepresentation().y);
        repaint();
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
