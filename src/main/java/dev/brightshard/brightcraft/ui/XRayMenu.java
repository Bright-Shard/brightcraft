package dev.brightshard.brightcraft.ui;

import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.XRayBlockGroup;
import dev.brightshard.brightcraft.ui.widgets.BrightScreen;
import dev.brightshard.brightcraft.hacks.XRay;
import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

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
        for (XRayBlockGroup group : XRay.BLOCKS) {
            String label = group.name;

            this.addButton(label + ": " + (group.enabled() ? "On" : "Off"),
                    group.tooltip,
                    (btn) -> {
                            boolean enabled = !group.enabled();
                            //CONFIG.setConfig("XRay."+label, (enabled ? "true" : "false"));
                            btn.setMessage(Text.of(label + ": " + (enabled ? "On" : "Off")));

                            if (Hack.getHack(Hack.HackType.XRay).enabled) {
                                CLIENT.worldRenderer.reload();
                            }

                            this.refresh();
            }, false);
        }
    }
}
