/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects;

import core.objects.map.ActiveArea;

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
        monster.moveDelay=3;
        monster.getMapRepresentation().img = 'G';
        monster.getMapRepresentation().tileFlags=ActiveArea.HAS_OBJECT|ActiveArea.BLOCKS_MOVEMENT|ActiveArea.AIRTIGHT|ActiveArea.HAS_MOB;
        monster.faction=1;
        return monster;
    }
    
    public boolean think(){
        int smell=0, dir=4;
        if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y-1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y-1];
            dir=0;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y-1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y-1];
            dir=1;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y-1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y-1];
            dir=2;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y];
            dir=3;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y];
            dir=4;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y];
            dir=5;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y+1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x-1][this.getMapRepresentation().y+1];
            dir=6;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y+1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x][this.getMapRepresentation().y+1];
            dir=7;
            //System.out.println(dir+" "+smell);
        }if (this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y+1]>smell){
            smell=this.getMapRepresentation().dungeon.playerScent[this.getMapRepresentation().x+1][this.getMapRepresentation().y+1];
            dir=8;
            //System.out.println(dir+" "+smell);
        }
        if (dir==0) {
            this.goNW();
        }if (dir==1) {
            this.goN();
        }if (dir==2) {
            this.goNE();
        }if (dir==3) {
            this.goW();
        }if (dir==5) {
            this.goE();
        }if (dir==6) {
            this.goSW();
        }if (dir==7) {
            this.goS();
        }if (dir==8) {
            this.goSE();
        }
        return true;
    }
}
