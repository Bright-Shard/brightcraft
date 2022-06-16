package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.XRayBlockGroup;
import net.minecraft.block.Block;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class XRay extends Hack {
    private boolean fullBrightWasOff;
    private final Hack fullbright = Hack.getHackById("FullBright");

    public XRay() {
        super("XRay", "XRay", "Only show important blocks (ores, monsters, lava, etc)\nAlso enables Fullbright", GLFW.GLFW_KEY_X);
        XRay instance = this;
        this.handlers.add(new EventHandler(instance.id, "BlockShouldRender") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                CallbackInfoReturnable<Boolean> cir = data.getCIR();

                for (XRayBlockGroup group : CONFIG.XRayBlocks) {
                    if (group.enabled()) {
                        if (group.blocks.contains(data.<Block>getData())) {
                            cir.setReturnValue(true);
                            return;
                        }
                    }
                }

                cir.setReturnValue(false);
            }
        });
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.fullBrightWasOff = !this.fullbright.enabled();
        this.fullbright.enable();

        CLIENT.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.fullBrightWasOff) {
            this.fullbright.disable();
        }
        CLIENT.worldRenderer.reload();
    }
}
