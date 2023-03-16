package dev.brightshard.brightcraft.ui.widgets;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class Button extends ButtonWidget {
    public Button(String text, String tooltip, ButtonWidget.PressAction func, BrightScreen parent, boolean large) {
        super(
                parent.getX(large),
                parent.getY(),
                (large ? 210 : 100),
                20,
                Text.of(text),
                func,
                ButtonWidget.DEFAULT_NARRATION_SUPPLIER
        );
        super.setTooltip(Tooltip.of(Text.of(tooltip)));
    }
}
