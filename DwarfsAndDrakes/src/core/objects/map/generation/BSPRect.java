/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map.generation;

import java.util.Random;

/**
 *
 * @author filip
 */
public class BSPRect {
    
    public static Random random = new Random();
    
    public static int MIN_DIM = 6;
    
    public enum CutType {
        HORIZ_CUT, VERT_CUT;
    }
    
    public int x, y, w, h;
    public BSPRect[] children = new BSPRect[]{null, null};
    public CutType cutType = null;
    
    public BSPRect(){}
    
    public void partition(double split_rate){
        if (random.nextFloat() > split_rate) return; // random chance decrees no split
        if (random.nextFloat() > 0.5 && this.h > MIN_DIM * 2){
            // make horizontal cut
            this.cutType = CutType.HORIZ_CUT;
            int cut_y = MIN_DIM + this.y + random.nextInt(this.y + this.h - MIN_DIM * 2);
            BSPRect top = new BSPRect();
            top.x = this.x; top.y = this.y; top.w= this.w; top.h = cut_y - this.y;
            top.partition(split_rate);
            BSPRect bot = new BSPRect();
            bot.x = this.x; bot.y = cut_y; bot.w = this.w; bot.h = this.h + this.y - cut_y;
            bot.partition(split_rate);
            this.children[0] = top; this.children[1] = bot;            
        } else if (this.w > MIN_DIM * 2){
            this.cutType = CutType.VERT_CUT;
            int cut_x = MIN_DIM + this.x + random.nextInt(this.x + this.w - MIN_DIM * 2);
            BSPRect lft = new BSPRect();
            lft.x = this.h; lft.y = this.y; lft.h= this.h; lft.w = cut_x - this.x;
            lft.partition(split_rate);
            BSPRect rgt = new BSPRect();
            rgt.x = cut_x; rgt.y = this.y; rgt.h = this.h; rgt.w = this.w + this.x - cut_x;
            rgt.partition(split_rate);
            this.children[0] = lft; this.children[1] = rgt;
        }
        
    }
    
}
