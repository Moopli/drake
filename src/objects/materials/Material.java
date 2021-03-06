/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.materials;

/**
 * "Material" objects store all data pertaining to the intrinsic properties of a 
 * material.
 * @author filip
 */
public class Material {
    
    /* 
     * -- Some physics definitions --
     * Stress: the amount of force per unit area being applied on the INTERIOR 
     * of a solid -- a solid in free-fall is under zero stress.
     * Strain: A measure of the deformation of a material. Can be broken into 
     * two components, radial (shear) and axial (compressive/tensile) 
     * deformation. 
     */
    
    /**
     * The slope of the elastic portion of a tensile stress-strain curve.
     * A measure of tensile strength, the higher the stronger. SI units.
     */
    public double youngsModulus;
    /**
     * The slope of the elastic portion of a shear stress-strain curve.
     * A measure of shear strength, the higher the stronger. SI units.
     */
    public double shearModulus;
    /**
     * The slope of the elastic portion of a compressive stress-strain curve.
     * A measure of compressive strength, the higher the stronger. 
     * SI units.
     */
    public double bulkModulus;
    /**
     * SI units.
     */
    public double density;
    /**
     * The stress at which the material starts to deform plastically.
     * The higher the stronger. 
     */
    public double yieldPoint;
    
    /*
     * We need some values to store material damage --
     * Strain is probably the best one, as from it and the above data, we can
     * calculate stuff like wear and whatnot. 
     * However, we need some value that stores the total amount of wear.
     * Wear, perhaps? Or perhaps we can calculate wear by adjusting the above 
     * values -- as overall wear exerts its effect through these...naw, it's 
     * probably better to store above values at no-wear values, then store wear 
     * and use it to make the necessary adjustments.
     */
    
    
    public double wear = 1;
    
   /*
     * You might note that the rupture point and other similar values are 
     * neglected. That isn't a problem, as we calculate breakage and plastic 
     * changes through wear.
     * 
     * 
     */
    
    
    
}
