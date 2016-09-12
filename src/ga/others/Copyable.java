package ga.others;

/**
 * This interface allows deep copying/cloning of each class that implements it.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface Copyable<T> {
    T copy();
}
