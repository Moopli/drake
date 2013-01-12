/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map.generation;

/**
 *
 * @author filip
 */
public interface MapPredicate {
    
    /*
     * Also needs a map object passed in;
     * and other data would be passed in at constructor.
     */
    
    public boolean satisfies(int x, int y);
}
