package dev.brightshard.brightcraft.ui;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class Button {
    public static ButtonWidget button(String name, String tooltip, ButtonWidget.PressAction func, BrightScreen parent, boolean large) {
        int x;
        int buttonWidth;
        if (large) {
            x = (parent.width / 2) - 105;
            buttonWidth = 210;
            parent.column = 1;

        } else {
            x = (parent.width / 2) - (parent.column == 0 ? 105 : -5);
            buttonWidth = 100;
        }

        return new ButtonWidget(
            x,
            50 + (30 * parent.row),
            buttonWidth,
            20,
            Text.of(name), // Text
            func, // OnClick
            (button, matrices, mouseX, mouseY) -> { // The tooltip
                parent.renderOrderedTooltip( // Show the tooltip
                        matrices, // idfk tbh, just copied this part
                        BrightScreen.client.textRenderer.wrapLines(StringVisitable.plain(tooltip), // The text
                                Math.max(parent.width / 2 - 50, 150)), // Whichever is bigger - half the screen + 50 pixels or 150 pixels
                        mouseX, // Mouse X coord
                        mouseY // Mouse Y coord
            );
        });
    }
}
