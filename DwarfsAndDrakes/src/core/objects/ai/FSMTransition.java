/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

/**
 *
 * @author 301916706
 */
public enum FSMTransition {
   ALWAYS_TRUE{
       @Override
       public boolean switchState(){
           return true;
       }
   }
    
    
   ;
   
   public abstract boolean switchState();
   
}
