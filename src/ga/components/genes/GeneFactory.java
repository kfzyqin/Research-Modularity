package ga.components.genes;

/**
 * GeneFactory generates random genes for initializer of populations.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/2016
 * @param <V> Class/Type of value.
 */
public interface GeneFactory<V> {
    /**
     * Generates a random gene.
     * @return gene with random value in its corresponding domain.
     */
    Gene<V> generateGene();
}
