package ga.operations.hotspotMutators;

import ga.components.hotspots.Hotspot;
import org.jetbrains.annotations.NotNull;

/**
 * This interface provides an abstraction for mutation of hotspot encodings.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
 */
public interface HotspotMutator {
    void mutate(@NotNull final Hotspot hotspotMap);
}
