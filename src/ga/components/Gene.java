package ga.components;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

/**
 * Created by david on 26/08/16.
 */
public final class Gene<T extends Comparable<T>> implements Copyable<Gene<T>> {

    private final GeneConfig config;
    private final Class<T> tClass;
    private T value;

    public Gene(final Class<T> tClass, final GeneConfig config, T value){
        this.config = config;
        this.tClass = tClass;
        if (config.isValid(value))
            this.value = value;
        else
            throw new IllegalArgumentException("Value out of bound");
    }

    public GeneConfig getConfig() {
        return config;
    }

    public Class<T> getTClass() {
        return tClass;
    }

    public T getValue() {
        return value;
    }

    /**
     * For mutation use
     * @param value
     */
    public void setValue(@NotNull final T value) {
        if (config.isValid(value))
            this.value = value;
        else
            throw new IllegalArgumentException("Value out of bound");
    }

    @Override
    public Gene<T> copy() {
        return new Gene<T>(tClass, config, value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
