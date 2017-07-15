package ga.frame.states;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public interface DiploidState<C extends Chromosome> {
    public abstract void mutateExpressionMap();
}
