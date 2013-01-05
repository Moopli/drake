/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.flavor;

import java.util.*;

/**
 * This is the class that stores flavor
 * @author filip
 */
public class FlavorHolder {
    
    /**
     * Flavor objects only constructed through builder methods
     */
    private FlavorHolder(){}
    
    
    public static final String PATH_PREFIX = "";
    
    /*
     * Examples of use:
     * <properName> the <noun> 
     * 
     * 
     */
    
    
    /**
     * Objects have nouns
     */
    public String noun;
    
    /**
     * Objects may also have proper nouns, that is, names.
     */
    public String properName;
    
    
    
    public static FlavorHolder loadFlavor(String path){
        FlavorHolder flv = new FlavorHolder();
        Properties config = new Properties();
        
        
        
        
        
        
        return flv;
    }
    
}
