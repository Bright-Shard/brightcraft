package dev.brightshard.brightcraft.ui;

import dev.brightshard.brightcraft.ui.widgets.BrightScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import dev.brightshard.brightcraft.lib.Hack;

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
                boolean enabled = CONFIG.toggleHack(hack.id);
                btn.setMessage(Text.of(hack.name + ": " + (enabled ? "On" : "Off")));

                this.refresh();
            });
        }

        double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));
        double speedHackSpeed = Double.parseDouble(CONFIG.getConfig("SpeedAmount", "1.0"));

        this.addSlider("Fly Speed", flySpeed, 0.2, 2,
                (slider) -> CONFIG.setConfig("FlySpeed", String.valueOf(slider.getValue())));
        this.addSlider("Speed Hack Speed", speedHackSpeed, 0.2, 2,
                (slider) -> CONFIG.setConfig("SpeedAmount", String.valueOf(slider.getValue())));

        this.addButton("AntiCheat Config", "Change settings to avoid anticheat", (btn) -> CLIENT.setScreen(AnticheatMenu.getInstance()), false);
        this.addButton("XRay Config", "Change what blocks are shown with XRay", (btn) -> CLIENT.setScreen(XRayMenu.getInstance()), false);
    }

    private void addButton (Hack hack, ButtonWidget.PressAction func) {
        this.addButton(hack.name+": "+(hack.enabled() ? "On" : "Off"), hack.tooltip, func, false);
    }

    public static SettingsMenu getInstance() {
        return instance;
    }
}
