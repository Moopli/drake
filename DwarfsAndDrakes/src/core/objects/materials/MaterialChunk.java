/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.materials;

/**
 * A piece of some Material, with a certain volume, length, etc. In other words, 
 * we take a material, and add some extrinsic properties.
 * @author filip
 */
public class MaterialChunk extends Material {
    
    
    /**
     * Length of chunk along major axis.
     */
    public double length = 1;
    
    /**
     * Volume of chunk.
     */
    public double volume = 1;
    
    
    /**
     * Calculates a sigmoid curve with origin (5, 1/2). This number represents 
     * how important of a factor compression is in calculating the stresses in 
     * colliding objects. The importance of shear on the calculation is just 
     * 1-compressionFactor(contactArea).
     * @param contactArea The contact area of a collision.
     * @return The importance of compression in such a collision.
     */
    private static double compressionFactor(double contactArea){
        return 1.0 / (1 + Math.pow(Math.E,-contactArea+5));
    }
    
    
    /**
     * calculates the amount of wear inflicted on two chunks by a collision 
     * with the given contact area and collision energy. 
     * @param a Either of the participating chunks.
     * @param b The other participating chunk.
     * @param contact_area 
     * @param energy The amount of kinetic energy going into the collision.
     */
    public static void collision(MaterialChunk a, MaterialChunk b, double contact_area, double energy){
        
        // - calculate contact stress -- the higher the energy, lower the contact 
        // area, and lower the volume, the higher the stress.
        
        double c_factor = compressionFactor(contact_area);
        double sh_factor = 1 - c_factor;
        
        double stress_a = energy/a.volume/c_factor; // NOT THE PROPER CALCULATION MUST FIX EVENTUALLY
        double stress_b = energy/b.volume/c_factor; // NOT THE PROPER CALCULATION MUST FIX EVENTUALLY
        
        // calculate effective elastic moduli
        double a_elastmod = sh_factor*a.shearModulus + c_factor*a.bulkModulus;
        double b_elastmod = sh_factor*b.shearModulus + c_factor*b.bulkModulus;
        a.applyStress(stress_a, a_elastmod);
        b.applyStress(stress_b, b_elastmod);
    }
    
    public static final double ELASTIC_WEAR_RATE = 0.1;
    public static final double PLASTIC_WEAR_RATE = 0.4;
    
    
    /**
     * Applies a stress to the object, calculating some wear.
     * @param stress
     * @param modulus 
     */
    public void applyStress(double stress, double modulus){
        if (stress < yieldPoint / wear){
            double strain = stress / modulus * wear; 
            
            // YAY WRONG BUT WORKABLE EQUATIONS
            wear += stress/yieldPoint * ELASTIC_WEAR_RATE;
            
        } else {
            // fancy plastic deformation calculations go here
            // currently just another "elastic" section
            double strain = stress/modulus * wear;
            // currently simple, could be better
            wear += strain * PLASTIC_WEAR_RATE;
        }
    }
    
    
    public double getMass(){
        return this.volume*this.density;
    }
}
