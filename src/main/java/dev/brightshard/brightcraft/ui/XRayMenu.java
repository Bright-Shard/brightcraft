package dev.brightshard.brightcraft.ui;

import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.XRayBlockGroup;
import dev.brightshard.brightcraft.ui.widgets.BrightScreen;
import net.minecraft.text.Text;

public class XRayMenu extends BrightScreen {
    private static final XRayMenu instance = new XRayMenu();

    public XRayMenu() {
        super("XRay Settings");
    }

    public static XRayMenu getInstance() {
        return instance;
    }

    @Override
    public void init() {
        super.init();
        for (XRayBlockGroup group : config.XRayBlocks) {
            String label = group.name;

            this.addButton(label + ": " + (group.enabled() ? "On" : "Off"),
                    group.tooltip,
                    (btn) -> {
                            boolean enabled = !group.enabled();
                            config.setConfig("XRay."+label, (enabled ? "true" : "false"));
                            btn.setMessage(Text.of(label + ": " + (enabled ? "On" : "Off")));

                            if (Hack.getHackById("XRay").enabled()) {
                                client.worldRenderer.reload();
                            }

                            this.refresh();
            }, false);
        }
    }
}
