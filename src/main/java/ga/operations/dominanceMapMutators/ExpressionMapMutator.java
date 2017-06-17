package ga.operations.dominanceMapMutators;

import com.sun.istack.internal.NotNull;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public interface ExpressionMapMutator {
    void mutate(@NotNull final ExpressionMap expressionMap);

}
