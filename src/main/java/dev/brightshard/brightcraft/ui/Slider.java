package dev.brightshard.brightcraft.ui;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class Slider extends SliderWidget {
    private final valueUpdateAction onValueUpdate;
    public Slider(String text, double value, valueUpdateAction onValueUpdate, BrightScreen parent) {
        super(
                parent.getX(true),
                parent.getY(),
                210,
                20,
                Text.of(text),
                value
        );
        this.onValueUpdate = onValueUpdate;
    }

    public interface valueUpdateAction {
        void onValueUpdate(Slider slider);
    }

    public double getValue() {
        return this.value;
    }

    @Override
    protected void updateMessage() {}
    @Override
    protected void applyValue() {
        this.onValueUpdate.onValueUpdate(this);
    }
}
