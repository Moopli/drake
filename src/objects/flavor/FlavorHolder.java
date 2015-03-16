/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.flavor;

import data.DataRoot;
import java.io.IOException;
import java.util.*;

/**
 * This is the class that stores flavortext.
 * @author filip
 */
public class FlavorHolder {
    
    
    public static final String PATH_PREFIX = "flavor/";
    
    /**
     * The class object which is situated at the base of the resources tree
     */
    public static final Class RESOURCE_TRUNK = DataRoot.class;
    
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
    
    public String description;
    
    public static FlavorHolder loadFlavor(String path){
        FlavorHolder flv = new FlavorHolder();
        Properties config = new Properties();
        
        try {
            config.load(RESOURCE_TRUNK.getResourceAsStream(path));
            
            if (config.containsKey("noun")){
                flv.noun = config.getProperty("noun");
            }
            if (config.containsKey("proper_name")){
                flv.properName = config.getProperty("proper_name");
            }
            if (config.containsKey("description")){
                flv.description = config.getProperty("description");
            }
            
            
            
            
            
            
        } catch (IOException ioe){
            
        }
        return flv;
    }
    
}
