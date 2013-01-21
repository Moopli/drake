/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.materials;

import data.DataRoot;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author filip
 */
public class MaterialLoader {
    
    /**
     * Produces a basic Elastic goo for a material.
     * @return A Material specifying the properties of some elastic goo.
     */
    public static MaterialChunk makeElasticGoo(){
        MaterialChunk m = new MaterialChunk();
        m.bulkModulus = m.youngsModulus = m.shearModulus = 1;
        m.yieldPoint = 1; // not at all strong
        m.density = 1; // as dense as water
        return m;
    }
    
    public static final String PATH_PREFIX = "anatomy/";
    
    /**
     * The class object which is situated at the base of the resources tree
     */
    public static final Class RESOURCE_TRUNK = DataRoot.class;
    
    public static MaterialChunk loadMaterial(String path){
        return loadMaterial(path, makeElasticGoo());
    }
    
    public static MaterialChunk loadMaterial(String path, MaterialChunk m){
        
        
        try {
            Properties config = new Properties();
            config.load(RESOURCE_TRUNK.getResourceAsStream(path));
            
            // grab inheritances here, in order
            if (config.containsKey("inherits")){
                for (String s : config.getProperty("inherits").split(" ")){
                    loadMaterial(s, m);
                }
            }
            
            if (config.containsKey("youngsModulus")){
                m.youngsModulus = Double.parseDouble(config.getProperty("youngsModulus"));
            }
            if (config.containsKey("shearModulus")){
                m.shearModulus = Double.parseDouble(config.getProperty("shearModulus"));
            }
            if (config.containsKey("bulkModulus")){
                m.bulkModulus = Double.parseDouble(config.getProperty("bulkModulus"));
            }
            if (config.containsKey("yieldPoint")){
                m.yieldPoint = Double.parseDouble(config.getProperty("yieldPoint"));
            }
            if (config.containsKey("density")){
                m.density = Double.parseDouble(config.getProperty("density"));
            }
            
            
        } catch(IOException ioe){
            System.out.println("errored loading " + PATH_PREFIX + path);
        }
        
        
        return m;
    }
}
