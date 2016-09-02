package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public abstract class Haploid<M extends GeneticMaterial, G extends GeneticMaterial> extends Chromosome<M,G> {

    public Haploid(@NotNull final M strand) {
        super(1, strand.getSize());
        List<M> strands = new ArrayList<>(1);
        strands.add(strand);
        materials = FixedSizeList.fixedSizeList(strands);
    }

    @Override
    public String toString() {
        return "Genotype: " + materials.get(0).toString();
    }
}
