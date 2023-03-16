package dev.brightshard.brightcraft.ui.widgets;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

public abstract class BrightScreen extends Screen {
    public int row = 0;
    public int column = 0;

    public BrightScreen(String title) {
        super(Text.of(title));
    }

    @Override
    public void init(){
        this.row = 0;
        this.column = 0;
    }

    protected void addButton(String name, String tooltip, ButtonWidget.PressAction func, boolean large) {
        if (large && column == 1) {
            this.row += 1;
        } else if (large) {
            this.column = 1;
        }
        this.addDrawableChild(new Button(name, tooltip, func, this, large));
        this.updateGrid();
    }

    protected void addSlider(String text, double value, double min, double max, Slider.valueUpdateAction onValueUpdate) {
        this.addDrawableChild(new Slider(text, value, min, max, onValueUpdate, this));
        this.column = 1;
        this.updateGrid();
    }

    protected void refresh() {
        CLIENT.setScreen(this);
    }

    protected void updateGrid() {
        ++column;

        // After two elements have been added, reset the column and move down 1 row
        if (column == 2) {
            column = 0;
            ++row;
        }
    }

    public int getX(boolean large) {
        return (width / 2) - (large ? 105 : (column == 0 ? 105 : -5));
    }
    public int getY() {
        return 50 + (30 * row);
    }
}
