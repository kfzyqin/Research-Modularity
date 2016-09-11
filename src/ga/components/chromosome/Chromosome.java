package ga.components.chromosome;

import ga.components.materials.GeneticMaterial;
import ga.others.Copyable;

import java.util.Collections;
import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public abstract class Chromosome<M extends GeneticMaterial, G extends GeneticMaterial> implements Copyable<Chromosome> {

    protected final int strands;
    protected final int length;
    protected List<M> materials;

    public Chromosome(int strands, int length) {
        this.strands = strands;
        this.length = length;
    }

    public abstract G getPhenotype(final boolean recompute);

    public int getStrands() {
        return strands;
    }

    public int getLength() {
        return length;
    }

    public List<M> getMaterialsView() {
        return Collections.unmodifiableList(materials);
    }
}
