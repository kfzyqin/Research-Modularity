package ga.operations.hotspotMutators;

import ga.components.hotspots.Hotspot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class RandomHotspotMutator implements HotspotMutator {

    private double hotspotMutationProbability;

    public RandomHotspotMutator(final double hotspotMutatorProbability) {
        setHotspotMutationProbability(hotspotMutatorProbability);
    }

    public void setHotspotMutationProbability(double probability) {
        if (probability > 1 || probability < 0) throw new IllegalArgumentException("Invalid probability value.");
        this.hotspotMutationProbability = probability;
    }

    public double getHotspotMutationProbability() {
        return this.hotspotMutationProbability;
    }

    @Override
    public void mutate(Hotspot hotspot) {
        Set<Integer> hotspotPositions = hotspot.getRecombinationRateMap().keySet();
        Map<Integer, Double> unnormalizedMap = new HashMap<>(hotspot.getSize());
        for (Integer e : hotspotPositions) {
            if (Math.random() < hotspotMutationProbability) {
                unnormalizedMap.put(e, Math.random());
            } else {
                unnormalizedMap.put(e, hotspot.getRecombinationRateMap().get(e));
            }
        }
        double unnormalizedRateSum = 0;
        for (Double e : unnormalizedMap.values()) {
            unnormalizedRateSum += e;
        }
        for (Integer e : unnormalizedMap.keySet()) {
            hotspot.setRecombinationRateAtPosition(e, (unnormalizedMap.get(e) / unnormalizedRateSum));
        }
    }
}
