package ga.operations.hotspotMutators;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;

/**
 * This interface provides an abstraction for mutation of hotspot encodings.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
 */
public interface HotspotMutator {
    void mutate(@NotNull final Hotspot hotspotMap);
}
