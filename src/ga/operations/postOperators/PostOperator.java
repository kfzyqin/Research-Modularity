package ga.operations.postOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;

/**
 * This interface provides an abstraction for any possible post-operation after reproduction.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public interface PostOperator<C extends Chromosome> {
    void postOperate(@NotNull final Population<C> population);
}
