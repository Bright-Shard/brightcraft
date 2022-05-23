package dev.brightshard.brightcraft.ui;

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
        boolean enabled = config.getConfig("Instabreak.Bypass").equals("true");
        this.addButton("Instant Mine: "+(enabled ? "On" : "Off"),
                "If Instant Mine doesn't work, try enabling this\nNote: This bypass slows down Instant Mine", (btn) -> {
            config.setConfig("InstantMine.Bypass", String.valueOf(!enabled));
            btn.setMessage(Text.of("Instant Mine: "+(!enabled ? "On" : "Off")));
            this.refresh();
        }, false);
        this.addButton("<- Back", "Go back to the main screen", (btn) -> client.setScreen(SettingsMenu.getInstance()), true);
    }
}
