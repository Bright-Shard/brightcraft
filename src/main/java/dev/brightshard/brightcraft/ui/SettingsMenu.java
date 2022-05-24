package dev.brightshard.brightcraft.ui;

import dev.brightshard.brightcraft.ui.widgets.BrightScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class SettingsMenu extends BrightScreen {
    private static final SettingsMenu instance = new SettingsMenu();

    public SettingsMenu() {
        super("BrightCraft Settings");
    }

    @Override
    public void init() {
        super.init();
        for (Hack hack : Hack.getHacks()) {
            addButton(hack, (btn) -> {
                boolean enabled = config.toggleHack(hack.id);
                btn.setMessage(Text.of(hack.name + ": " + (enabled ? "On" : "Off")));

                assert client != null;
                this.refresh();
            });
        }

        double flySpeed;
        try {
            flySpeed = Double.parseDouble(config.getConfig("FlySpeed"));
        } catch (NumberFormatException e) {
            flySpeed = 1;
            config.setConfig("FlySpeed", String.valueOf(flySpeed));
        }
        LOGGER.info(String.valueOf(flySpeed));

        this.addSlider("Fly Speed", flySpeed, 0.5, 5, (slider) -> {
            double newFlySpeed = slider.getValue();
            config.setConfig("FlySpeed", String.valueOf(newFlySpeed));
        }, this);

        this.addButton("AntiCheat Config", "Change settings to avoid anticheat", (btn) -> client.setScreen(AnticheatMenu.getInstance()), true);
    }

    private void addButton (Hack hack, ButtonWidget.PressAction func) {
        this.addButton(hack.name+": "+(hack.enabled() ? "On" : "Off"), hack.tooltip, func, false);
    }

    public static SettingsMenu getInstance() {
        return instance;
    }
}
