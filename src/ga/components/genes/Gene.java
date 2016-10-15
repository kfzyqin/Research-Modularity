package ga.components.genes;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

/**
 * Gene represents a gene, a container of a single value of generic type V.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/2016
 * @param <V> Class/Type of value.
 */
public abstract class Gene<V> implements Copyable<Gene<V>> {

    protected V value;

    public Gene(@NotNull V value) {
        this.value = value;
    }

    /**
     * @return value of the gene
     */
    public V getValue(){
        return value;
    }

    /**
     * @param value value to be assigned to the gene, may be subject to restrictions according to implementations.
     */
    public abstract void setValue(@NotNull final V value);
}
