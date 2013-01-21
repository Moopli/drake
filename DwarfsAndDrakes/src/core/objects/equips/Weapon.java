/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.equips;

import core.objects.flavor.FlavorHolder;
import core.objects.materials.MaterialChunk;
import core.objects.materials.MaterialLoader;
import data.DataRoot;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author filip
 */
public class Weapon {
    
    /*
     * Standard weapon -- something like a pseudopod.
     */
    public MaterialChunk material = MaterialLoader.makeElasticGoo();
    public double contactArea = 5;
    public double hitForce = 2;
    
    public FlavorHolder flavor;
    
    public static final String PATH_PREFIX = "weapons/";
    
    /**
     * The class object which is situated at the base of the resources tree
     */
    public static final Class RESOURCE_TRUNK = DataRoot.class;
    
    
    public static Weapon loadWeapon(String path){
        return loadWeapon(path, new Weapon());
    }
    
    public static Weapon loadWeapon(String filepath, Weapon wp){
        
        Properties config = new Properties();
        
        try {
            config.load(RESOURCE_TRUNK.getResourceAsStream(PATH_PREFIX + filepath));
            
            
            // grab inheritances here, in order
            if (config.containsKey("inherits")){
                for (String path : config.getProperty("inherits").split(" ")){
                    loadWeapon(path, wp);
                }
            }
            
            if (config.contains("material")){
                wp.material = MaterialLoader.loadMaterial(config.getProperty("material"));
            }
            
            if (config.containsKey("hit_force")){
                wp.hitForce = Double.parseDouble(config.getProperty("hit_force"));
            }
            
            if (config.containsKey("contact_area")){
                wp.contactArea = Double.parseDouble(config.getProperty("contact_area"));
            }
            
            if (config.containsKey("flavor")){
                wp.flavor = FlavorHolder.loadFlavor(config.getProperty("flavor"));
            }
            
        } catch (IOException ioe){
            System.out.println("errored loading "+ PATH_PREFIX + filepath);
        }
        
        
        return wp;
    }
}
