package genderGAWithHotspots.operations.hotspotMutator;

import com.sun.istack.internal.NotNull;
import genderGAWithHotspots.components.hotspots.DiscreteExpHotspot;
import genderGAWithHotspots.components.hotspots.Hotspot;

import java.util.List;

/**
 * Created by david on 28/09/16.
 */
public class SimpleDiscreteExpHotspotMutator implements HotspotMutator<Integer> {

    private double control;
    private double promoteProbability = 0.5;

    public SimpleDiscreteExpHotspotMutator(final double control) {
        setControl(control);
    }

    public SimpleDiscreteExpHotspotMutator(final double control, final double promoteProbability) {
        this(control);
        setPromoteProbability(promoteProbability);
    }


    private void filter(final double control) {
        if (control < -1) throw new IllegalArgumentException("Control parameter must be at least -1.");
    }

    @Override
    public void mutate(@NotNull final Hotspot<Integer> hotspotMap) {
        DiscreteExpHotspot hotspot = (DiscreteExpHotspot) hotspotMap;
        final int maxLevel = hotspot.getMaxLevel();
        List<Integer> encoding = hotspot.getEncoding();
        int value;
        for (int i = 0; i < encoding.size(); i++) {
            value = encoding.get(i);
            if (Math.random() < Math.exp(-value - control)) {
                if (value == 1) encoding.set(i, 2);
                else if (value == maxLevel) encoding.set(i, maxLevel-1);
                else encoding.set(i, (Math.random() < promoteProbability) ? value+1 : value-1);
            }
        }
    }

    public double getControl() {
        return control;
    }

    public void setControl(final double control) {
        filter(control);
        this.control = control;
    }

    public double getPromoteProbability() {
        return promoteProbability;
    }

    public void setPromoteProbability(final double promoteProbability) {
        if (promoteProbability > 1 || promoteProbability < 0) throw new IllegalArgumentException("Invalid probability value.");
        this.promoteProbability = promoteProbability;
    }
}
