package dev.brightshard.brightcraft.ui.widgets;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class Button extends ButtonWidget {

    public Button(String text, String tooltip, ButtonWidget.PressAction func, BrightScreen parent, boolean large) {
        super(parent.getX(large),
            parent.getY(),
            (large ? 210 : 100),
            20,
            Text.of(text),
            func,
            (button, matrices, mouseX, mouseY) -> { // The tooltip
                parent.renderOrderedTooltip( // Show the tooltip
                        matrices, // idfk tbh, just copied this part
                        BrightScreen.CLIENT.textRenderer.wrapLines(StringVisitable.plain(tooltip), // The text
                                Math.max(parent.width / 2 - 50, 150)), // Whichever is bigger - half the screen + 50 pixels or 150 pixels
                        mouseX, // Mouse X coord
                        mouseY // Mouse Y coord
                );
            }
        );
    }
}
