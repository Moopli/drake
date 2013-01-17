/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map.generation;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author filip
 */
public class BSPRect {
    public static Random random = new Random();
    public static int MIN_DIM = 8;
    public static int MIN_NO_SPLIT = 10;

    public enum CutType {

        HORIZ_CUT, VERT_CUT;
    }
    public ArrayList<ArrayList<String>> room = new ArrayList<ArrayList<String>>();
    private int x, y, w, h;
    public BSPRect[] children = new BSPRect[]{null, null};
    public CutType cutType = null;

    public BSPRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        for (int i = 0; i < this.h; i++) {
            room.add(new ArrayList<String>());
        }
    }

    public void partition(double split_rate) {
        if (random.nextFloat() > split_rate + (this.h+this.w)/MIN_NO_SPLIT) {
            return; // random chance decrees no split
        }
        boolean hen = false, wen = false;
        if (this.h > MIN_DIM * 2) {
            hen = true;
        }
        if (this.w > MIN_DIM * 2) {
            wen = true;
        }
        if ((hen && random.nextFloat() > 0.5) || (hen && !wen)) { //half the time it will make horizontal cut, and will always make cut if possible
            // make horizontal cut
            this.cutType = CutType.HORIZ_CUT;
            int cut_y = MIN_DIM + this.y + random.nextInt( this.h - MIN_DIM * 2);
            BSPRect top = new BSPRect(this.x, this.y, this.w, cut_y - this.y);
            top.partition(split_rate);
            BSPRect bot = new BSPRect(this.x, cut_y, this.w, this.h + this.y - cut_y);
            bot.partition(split_rate);
            this.children[0] = top;
            this.children[1] = bot;
        } else if (wen) {
            this.cutType = CutType.VERT_CUT;
            int cut_x = MIN_DIM + this.x + random.nextInt( this.w - MIN_DIM * 2);
            BSPRect lft = new BSPRect(this.x, this.y, cut_x - this.x, this.h);
            lft.partition(split_rate);
            BSPRect rgt = new BSPRect(cut_x, this.y, this.w + this.x - cut_x, this.h);
            rgt.partition(split_rate);
            this.children[0] = lft;
            this.children[1] = rgt;
        }

    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.w; j++) {
                buf.append(this.room.get(i).get(j)+" ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    public void joinRooms() {
        if (cutType == null) {
            for (int i = 0; i < this.w; i++) {
                room.get(0).add("#");
            }
            for (int i = 1; i < this.h - 1; i++) {
                room.get(i).add("#");
                for (int j = 0; j < this.w - 2; j++) {
                    room.get(i).add(".");
                }
                room.get(i).add("#");
            }
            for (int i = 0; i < this.w; i++) {
                room.get(this.h - 1).add("#");
            }
        } else {
            this.children[0].joinRooms();
            this.children[1].joinRooms();
            if (cutType == CutType.HORIZ_CUT) {
                for (int k = 0; k < 2; k++) {
                    for (int i = 0; i < this.children[k].room.size(); i++) {
                        this.room.set(k*this.children[0].h+i, this.children[k].room.get(i));
                    }
                }
                int cpoint = random.nextInt(this.room.get(0).size() - 2) + 1;
                int i = this.children[0].h;
                while (i > 0) {
                    this.room.get(i).set(cpoint, ".");
                    //don't get this part
                    if (cpoint > 0 && this.room.get(i).get(cpoint - 1).equals(".")) {
                        break;
                    }
                    if (cpoint < this.w - 1 && this.room.get(i).get(cpoint + 1).equals(".")) {
                        break;
                    }
                    if (this.room.get(i - 1).get(cpoint).equals(".")) {
                        break;
                    }
                    //end don't get
                    i--;
                }
                i = this.children[0].h;
                while (i < this.h - 2) {
                    this.room.get(i).set(cpoint, ".");
                    //don't get this part
                    if (cpoint > 0 && this.room.get(i).get(cpoint - 1).equals(".")) {
                        break;
                    }
                    if (cpoint < this.w - 1 && this.room.get(i).get(cpoint + 1).equals(".")) {
                        break;
                    }
                    if (this.room.get(i + 1).get(cpoint).equals(".")) {
                        break;
                    }
                    //end don't get
                    i++;
                }
                // */
            } else {
                System.out.println(h);
                System.out.println(this.room.size());
                System.out.println(this.children[0].room.size());
                for (int i = 0; i < h; i++) {
                    for (int k = 0; k < 2; k++) {
                        ArrayList herp = this.children[k].room.get(i);
                        this.room.get(i).addAll(herp);
                    }
                }
                int cpoint = random.nextInt(this.h - 2) + 1;
                int i = this.children[0].w;
                while (i > 0) {
                    this.room.get(cpoint).set(i, ".");
                    //don't get this part
                    if (cpoint > 0 && this.room.get(cpoint - 1).get(i).equals(".")) {
                        break;
                    }
                    if (cpoint < this.h - 1 && this.room.get(cpoint + 1).get(i).equals(".")) {
                        break;
                    }
                    if (this.room.get(cpoint).get(i - 1).equals(".")) {
                        break;
                    }
                    //end don't get
                    i--;
                }
                i = this.children[0].w;
                while (i < this.w - 2) {
                    this.room.get(cpoint).set(i, ".");
                    //don't get this part
                    if (cpoint > 0 && this.room.get(cpoint - 1).get(i).equals(".")) {
                        break;
                    }
                    if (cpoint < this.w - 1 && this.room.get(cpoint + 1).get(i).equals(".")) {
                        break;
                    }
                    if (this.room.get(cpoint).get(i + 1).equals(".")) {
                        break;
                    }
                    //end don't get
                    i++;
                }
                // */
            }
        }
    }
}
