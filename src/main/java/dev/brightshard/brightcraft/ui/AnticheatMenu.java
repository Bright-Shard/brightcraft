package dev.brightshard.brightcraft.ui;

import dev.brightshard.brightcraft.ui.widgets.BrightScreen;
import static dev.brightshard.brightcraft.BrightCraft.*;
import net.minecraft.text.Text;

public class AnticheatMenu extends BrightScreen {
    private static final AnticheatMenu instance = new AnticheatMenu();
    public AnticheatMenu() {
        super("AntiCheat Settings");
    }
    public static AnticheatMenu getInstance() {
        return instance;
    }

    @Override
    public void init() {
        super.init();
        //boolean enabled = CONFIG.getConfig("Instabreak.Bypass").equals("true");
        boolean enabled = true;
        this.addButton("Instant Mine: "+(enabled ? "On" : "Off"),
                "If Instant Mine doesn't work, try enabling this\nNote: This bypass slows down Instant Mine", (btn) -> {
            CONFIG.setConfig("InstantMine.Bypass", String.valueOf(!enabled));
            btn.setMessage(Text.of("Instant Mine: "+(!enabled ? "On" : "Off")));
            this.refresh();
        }, false);
        this.addButton("<- Back", "Go back to the main screen", (btn) -> CLIENT.setScreen(SettingsMenu.getInstance()), true);
    }
}
