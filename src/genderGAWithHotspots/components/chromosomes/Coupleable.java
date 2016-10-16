package genderGAWithHotspots.components.chromosomes;

import genderGAWithHotspots.components.hotspots.Hotspot;

/**
 * Created by david on 29/09/16.
 */
public interface Coupleable<H> {
    boolean isMasculine();
    Hotspot<H> getHotspot();
}
