/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.objects.ai.MonsterAI;
import core.objects.map.ActiveArea;
import java.awt.Color;

/**
 *
 * @author Sepehr
 */
public class Monster extends Mob {

    public Monster(int x, int y, ActiveArea dungeon) {
        this.getMapRepresentation().x = x;
        this.getMapRepresentation().y = y;
        this.getMapRepresentation().dungeon = dungeon;
    }
    
    public static Monster createGoblin(int x, int y, ActiveArea dungeon){
        Monster monster=new Monster(x,y,dungeon);
        monster.brain = new MonsterAI(monster);
        monster.moveDelay=3;
        monster.getMapRepresentation().img = 'g';
        monster.getMapRepresentation().colorFore = Color.GREEN.darker().darker();
        monster.getMapRepresentation().tileFlags=ActiveArea.HAS_OBJECT|ActiveArea.BLOCKS_MOVEMENT|ActiveArea.AIRTIGHT|ActiveArea.HAS_MOB;
        monster.faction=1;
        return monster;
    }
    
    // getScent(this.getMapRepresentation().x,this.getMapRepresentation().y)
    
    public int think(){
        int smell=0, dir=4;
        if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x, this.getMapRepresentation().y)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x,this.getMapRepresentation().y);
            dir=4; // NO MOVE
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y-1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y-1);
            dir=0; // NW
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x,this.getMapRepresentation().y-1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x,this.getMapRepresentation().y-1);
            dir=1; // N
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y-1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y-1);
            dir=2; // NE
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y);
            dir=3; // W
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y);
            dir=5; // E
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y+1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x-1,this.getMapRepresentation().y+1);
            dir=6; // SW
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x,this.getMapRepresentation().y+1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x,this.getMapRepresentation().y+1);
            dir=7; // S
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y+1)>smell){
            smell=this.getMapRepresentation().dungeon.getScent(this.getMapRepresentation().x+1,this.getMapRepresentation().y+1);
            dir=8; // 
            //System.out.println(dir+" "+smell);
        }
        if (dir==0) {
            this.goNW();
        } else if (dir==1) {
            this.goN();
        } else if (dir==2) {
            this.goNE();
        } else if (dir==3) {
            this.goW();
        } else if (dir==5) {
            this.goE();
        } else if (dir==6) {
            this.goSW();
        } else if (dir==7) {
            this.goS();
        } else if (dir==8) {
            this.goSE();
        }
        return 4; // how many ticks until it moves again
    }
}
