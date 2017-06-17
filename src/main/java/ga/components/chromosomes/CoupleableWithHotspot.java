package ga.components.chromosomes;

import ga.components.hotspots.Hotspot;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public interface CoupleableWithHotspot extends Coupleable {
    Hotspot getHotspot();
}
