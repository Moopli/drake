/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

import core.objects.equips.Weapon;
import core.objects.flavor.FlavorHolder;
import core.objects.materials.MaterialLoader;
import java.io.IOException;
import java.util.Properties;
import data.DataRoot;
import java.util.ListIterator;


/**
 * This class constructs bodies; given config file templates.
 * @author filip
 */
public class BodyBuilder {
    
    public static final String PATH_PREFIX = "anatomy/";
    
    /**
     * The class object which is situated at the base of the resources tree
     */
    public static final Class RESOURCE_TRUNK = DataRoot.class;
    
    public static BodyPart loadBodyPart(String filepath){
        return loadBodyPart(filepath, new BodyPart());
    }
    
    private static BodyPart loadBodyPart(String filepath, BodyPart bp){
        Properties config = new Properties();
        
        try{
            config.load(RESOURCE_TRUNK.getResourceAsStream(PATH_PREFIX + 
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
            if (config.containsValue("material")){
                bp.material = MaterialLoader.loadMaterial(config.getProperty("material"));
            }
            if (config.containsKey("volume")){
                bp.material.volume = Integer.parseInt(config.getProperty("volume"));
            }
            if (config.containsKey("length")){
                bp.material.length = Integer.parseInt(config.getProperty("length"));
            }
            
            if (config.containsKey("max_wear")){
                bp.material.length = Double.parseDouble(config.getProperty("max_wear"));
            }
            
            if (config.containsKey("flavor")){
                bp.flavor = FlavorHolder.loadFlavor(config.getProperty("flavor"));
            }
            
            if (config.containsKey("weapon")){
                bp.intrinsicWeapon = Weapon.loadWeapon(config.getProperty("weapon"));
            }
            
            
            if (config.containsKey("armor_types")){
                bp.isArmorable = true;
                for (String eqp : config.getProperty("armor_types").split(" ")){
                    bp.equipTags.add(eqp);
                }
            }
            
        } catch (IOException e){
            System.out.println("errored loading "+ PATH_PREFIX + filepath);
        }
        
        return bp;
    }
    
    public static Body loadBody(String filepath){
        return loadBody(filepath, new Body());
    }
    
    public static Body loadBody(String filepath, Body b){
        
        Properties config = new Properties();
        
        try {
            config.load(RESOURCE_TRUNK.getResourceAsStream(PATH_PREFIX + 
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
                    if (part.intrinsicWeapon != null){
                        b.instrinsicWeapons.put(part, part.intrinsicWeapon);
                    }
                    b.bodyparts.put(part.internalName, part);
                }
            }
            
            
            // puts body parts inside the others they are attached to
            for (BodyPart bp : b.bodyparts.values()){
                if (config.containsKey(bp.internalName)) {
                    for (String connect : config.getProperty(bp.internalName).split(" ")){
                        BodyPart bp2 = b.bodyparts.get(connect);
                        bp.contained_parts.add(bp2);
                        bp2.contained_inside = bp;
                    }
                }
            }
            // finds body parts "within" each other, and repairs connection
            for (BodyPart bp : b.bodyparts.values()){
                ListIterator<BodyPart> iter = bp.contained_parts.listIterator();
                while (iter.hasNext()){
                    BodyPart bp2 = iter.next();
                    if (bp2.contained_parts.contains(bp)){
                        iter.remove();
                        bp2.contained_parts.remove(bp);
                        bp.connected.add(bp2);
                        bp2.connected.add(bp);
                    }
                }
            }
            
            
            
        } catch (IOException e){
            System.out.println("errored");
        }
        
        return b; 
    }
    
}
