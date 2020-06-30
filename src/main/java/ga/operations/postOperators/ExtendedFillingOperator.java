package ga.operations.postOperators;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleHaploid;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectors.ExtendedTournamentSelector;
import ga.operations.selectionOperators.selectors.Selector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtendedFillingOperator<T extends Chromosome> implements PostOperator<T> {


    private ExtendedTournamentSelector selector;

    public ExtendedFillingOperator(Selector<SimpleHaploid> haploidSelector) {
        this.selector = (ExtendedTournamentSelector) haploidSelector;
    }

    @Override
    public void postOperate(@NotNull Population<T> population) {
        final int amount = population.getSize() - population.getNextGenSize();
        List<T> individuals = selector.select(amount);
        population.addCandidateChromosomes(individuals);

    }


}
