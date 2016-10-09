package ga.operations.mutator;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;

/**
 * Created by david on 28/09/16.
 */
public interface HotspotMutator<H> {
    void mutate(@NotNull final Hotspot<H> hotspotMap);
}
