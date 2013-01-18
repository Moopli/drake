/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.materials;

/**
 *
 * @author filip
 */
public class MaterialLoader {
    
    /**
     * Produces a basic Elastic goo for a material.
     * @return A Material specifying the properties of some elastic goo.
     */
    public static Material makeElasticGoo(){
        Material m = new Material();
        m.bulkModulus = m.youngsModulus = m.shearModulus = 1;
        m.yieldPoint = 1; // not at all strong
        m.density = 1; // as dense as water
        return m;
    }
    
    
    
}
