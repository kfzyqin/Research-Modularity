package ga.operations.mutation;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;

import java.util.List;

/**
 * Created by david on 28/09/16.
 */
public interface HotspotMutationOperator<V> {
    void mutate(@NotNull final Hotspot<V> hotspotMap);
}
