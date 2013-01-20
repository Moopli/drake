/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.anatomy;

/**
 * i hereby direct all inquiries to Wikipedia's excellent article on the 
 * Disjoint-set data structure.
 * @author filip
 */
public class DisjointSet<T> {
    
    DisjointSet parent = this;
    public T datum;
    private int rank = 0;
    
    public DisjointSet(T datum){
        this.datum = datum;
    }
    
    public DisjointSet root(){
        if (this != parent){
            parent = parent.root();
        }
        return parent;
    }
    
    public DisjointSet merge(DisjointSet other){
        DisjointSet this_root = this.root();
        DisjointSet other_root= other.root();
        if (this_root == other_root) {
            return this_root;
        }
        if (this_root.rank < other_root.rank){
            this_root.parent = other_root;
        } else if (this_root.rank > other_root.rank){
            other_root.parent = this_root;
        } else {
            this_root.parent = other_root;
            other_root.rank += 1;
        }
        return this_root.parent;
    }
}
