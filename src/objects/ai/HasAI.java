/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.ai;

/**
 *
 * @author 301916706
 */
public interface HasAI {
    
    public void setController(AIController contr);
    public AIController getController();
    
    public void goN();
    public void goE();
    public void goS();
    public void goW();
    public void goNW();
    public void goSW();
    public void goNE();
    public void goSE();
    
    public void rangeAttack(String target);
    public void pickUp();
    
    
}
