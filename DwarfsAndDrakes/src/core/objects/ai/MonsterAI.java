/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

import core.objects.Monster;

/**
 *
 * @author filip
 */
public class MonsterAI extends AIController{

    Monster m;
    
    public MonsterAI(Monster m){
        this.m = m;
    }
    
    
    @Override
    public int think() {
        return m.think();
    }
    
}
