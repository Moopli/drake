/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

import java.awt.Color;

/**
 *
 * @author filip
 */
public enum DungeonTile {
    FLOOR{
        @Override
        public Color getColorBack(){
            return new Color(100, 100, 100);
        }
        @Override
        public char getCh(){
            return '.';
        }
        @Override
        public Color getCharColor(){
            return new Color(70, 70, 70);
        }
    }, WALL{
        @Override
        public Color getColorBack(){
            return new Color(100, 100, 100);
        }
        @Override
        public char getCh(){
            return '#';
        }
        @Override
        public Color getCharColor(){
            return new Color(70, 70, 70);
        }
    }, TRANSLUCENT{
        @Override
        public Color getColorBack(){
            return Color.blue.brighter();
        }
        @Override
        public char getCh(){
            return '~';
        }
        @Override
        public Color getCharColor(){
            return Color.cyan.darker();
        }
    }, STAIR {
        @Override
        public Color getColorBack(){
            return new Color(100, 100, 100);
        }
        @Override
        public char getCh(){
            return '<';
        }
        @Override
        public Color getCharColor(){
            return new Color(0, 0, 0);
        }
    }
    
    ;
    public abstract Color getColorBack();
    public abstract char getCh();
    public abstract Color getCharColor();
}
