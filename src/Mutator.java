/**
 * Created by david on 26/08/16.
 */
public interface Mutator<T extends Chromosome> {
    T mutate(Chromosome c);
}
