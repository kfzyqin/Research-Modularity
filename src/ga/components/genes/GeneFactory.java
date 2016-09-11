package ga.components.genes;

/**
 * Created by david on 11/09/16.
 */
public interface GeneFactory<V> {
    Gene<V> generateGene();
}
