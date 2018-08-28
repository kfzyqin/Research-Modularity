package ga.operations.dominanceMapMutators;

import ga.operations.expressionMaps.ExpressionMap;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public interface ExpressionMapMutator {
    void mutate(@NotNull final ExpressionMap expressionMap);

}
