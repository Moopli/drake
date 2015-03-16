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
     * @return The number of turns to bump this object back after thinking. 0 is 
     * a special case, meaning the game waits a bit and asks again.
     */
    public abstract int think();
    
    
}
