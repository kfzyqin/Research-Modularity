package ga.components.chromosome;

import ga.components.hotspots.Hotspot;

/**
 * Created by david on 29/09/16.
 */
public interface Coupleable<V> {
    boolean isMasculine();
    Hotspot<V> getHotspot();
}
