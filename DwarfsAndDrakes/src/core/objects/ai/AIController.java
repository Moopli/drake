/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

/**
 *
 * @author 301916706
 */
public abstract class AIController {
    
    HasAI controlled;
    
    /**
     * 
     * @param hasai 
     */
    public void setControlled(HasAI hasai){
        controlled = hasai;
    }
    
    /**
     * Should we have one big function that does the thinking? Yup.
     * @return True if the game should wait and try calling think again before 
     * moving on.
     */
    public abstract boolean think();
    
    
}
