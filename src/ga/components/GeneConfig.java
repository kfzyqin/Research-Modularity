package ga.components;

/**
 * Created by david on 26/08/16.
 */
public final class GeneConfig<T extends Comparable<T>> {
    private final T upperBound;
    private final T lowerBound;

    public GeneConfig(final T lowerBound, final T upperBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public boolean isValid(T object) {
        return object.compareTo(upperBound) <= 0 &&
                object.compareTo(lowerBound) >= 0;
    }

}
