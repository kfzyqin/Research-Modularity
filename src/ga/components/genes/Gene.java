package ga.components.genes;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

/**
 * Created by david on 26/08/16.
 */
public interface Gene<V> extends Copyable<Gene<V>> {
    V getValue();
    void setValue(@NotNull final V value);
    // static Gene<V> generateGene();
}
