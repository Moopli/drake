/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import java.io.IOException;
import java.util.Properties;

/**
 * This class constructs bodies; given config file templates.
 * @author filip
 */
public class BodyBuilder {
    
    public static final String BODY_PATH_PREFIX = "";
    
    /**
     * The class object which is situated at the base of the resources tree
     */
    public static final Class RESOURCE_TRUNK = BodyBuilder.class;
    
    public static void loadConfTest(){
        
        Properties config = new Properties();
        try{
            config.load(BodyBuilder.class.getResourceAsStream("sample.body"));
            
            System.out.println(config.getProperty("NAME"));
            System.out.println(config.getProperty("PARTS"));
            
        } catch (IOException e){
            System.out.println("errored");
        }
        
        
    }
    
    public static void main(String[] args) {
        loadConfTest();
    }
    
    public static BodyPart loadBodyPart(String filepath){
        return loadBodyPart(filepath, new BodyPart());
    }
    
    private static BodyPart loadBodyPart(String filepath, BodyPart bp){
        Properties config = new Properties();
        
        try{
            config.load(RESOURCE_TRUNK.getResourceAsStream(BODY_PATH_PREFIX + 
                    filepath));
            
            // grab inheritances here, in order
            if (config.containsKey("inherits")){
                for (String path : config.getProperty("inherits").split(" ")){
                    loadBodyPart(path, bp);
                }
            }
            
            // Get values
            /* Not implemented yet:
             * - Reading a weapons template (this would be done by a separate 
             *   weapon template reader)
             * - Reading flavor data (also a separate reader)
             * 
             */
            
            if (config.containsKey("name")){
                bp.internalName = config.getProperty("name");
            }
            if (config.containsKey("functions")){
                for (String func : config.getProperty("functions").split(" ")){
                    bp.functionsProvided.add(func);
                }
            }
            if (config.containsKey("required_functions")){
                for (String func : config.getProperty("required_functions").split(" ")){
                    bp.functionsRequired.add(func);
                }
            }
            
            if (config.containsKey("volume")){
                bp.volume = Integer.parseInt(config.getProperty("volume"));
            }
            if (config.containsKey("length")){
                bp.volume = Integer.parseInt(config.getProperty("length"));
            }
            if (config.containsKey("hardness")){
                bp.volume = Integer.parseInt(config.getProperty("hardness"));
            }
            
            
        } catch (IOException e){
            System.out.println("errored");
        }
        
        return bp;
    }
    
    public static Body loadBody(String filepath){
        return loadBody(filepath, new Body());
    }
    
    public static Body loadBody(String filepath, Body b){
        
        Properties config = new Properties();
        
        try {
            config.load(RESOURCE_TRUNK.getResourceAsStream(BODY_PATH_PREFIX + 
                    filepath));
            
            // Grab inheritances
            if (config.containsKey("inherits")){
                for (String path : config.getProperty("inherits").split(" ")){
                    loadBody(path, b);
                }
            }
            
            // load bodyparts
            if (config.containsKey("parts")){
                for (String path : config.getProperty("parts").split(" ")){
                    BodyPart part = loadBodyPart(path);
                    b.bodyparts.put(part.internalName, part);
                }
            }
            
            
            // TODO connect bodyparts
            
            
            
        } catch (IOException e){
            System.out.println("errored");
        }
        
        return b; 
    }
    
}
