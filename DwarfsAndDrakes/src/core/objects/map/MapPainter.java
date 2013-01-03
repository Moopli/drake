/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.objects.map;

/**
 *
 * @author filip
 */
public interface MapPainter {
    
    
    /*
     * Also requires a map object passed in.
     * other data, would be again set at constructor, or another time.
     */
    public void paintMap(MapBrush brush, MapPredicate pred);
    
    
    /*
     * we would do a paintMap something like this:
     * 
     * new FloodFillPainter(player.x, player.y).paintMap(the_map, new PlaceGnoll(difficulty * gnoll_strength), new DistanceRangeFromPlayer(4, 7));
     * 
     */
    
}
