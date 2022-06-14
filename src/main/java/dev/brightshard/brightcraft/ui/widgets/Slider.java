package dev.brightshard.brightcraft.ui.widgets;

import dev.brightshard.brightcraft.lib.MathTools;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class Slider extends SliderWidget {
    private final valueUpdateAction onValueUpdate;
    private double mappedValue;
    private final double min;
    private final double max;
    public Slider(String text, double value, double min, double max, valueUpdateAction onValueUpdate, BrightScreen parent) {
        super(
                parent.getX(true),
                parent.getY(),
                210,
                20,
                Text.of(text),
                MathTools.map(value, min, max, 0, 1)
        );
        this.onValueUpdate = onValueUpdate;
        this.min = min;
        this.max = max;
    }

    public interface valueUpdateAction {
        void onValueUpdate(Slider slider);
    }

    public double getValue() {
        return this.mappedValue;
    }

    // updateMessage - Called every time the slider moves
    @Override
    protected void updateMessage() {}
    // applyValue - Called when the slider moves AND has a new value
    @Override
    protected void applyValue() {
        this.mappedValue = MathTools.map(this.value, 0, 1, this.min, this.max);
        this.onValueUpdate.onValueUpdate(this);
    }
}
