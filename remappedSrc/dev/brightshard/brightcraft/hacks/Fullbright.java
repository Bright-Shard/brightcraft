package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.EventData;
import dev.brightshard.brightcraft.lib.EventHandler;
import dev.brightshard.brightcraft.lib.EventManager;
import org.lwjgl.glfw.GLFW;

import dev.brightshard.brightcraft.lib.Hack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Thx Wurst Client
public class Fullbright extends Hack {
    private double defaultGamma;
    public Fullbright() {
        super("FullBright", "Full Bright", "Like nightvision, but better", GLFW.GLFW_KEY_B);
        Fullbright instance = this;

        // Disable ambient occlusion / shading
        this.handlers.add(new EventHandler(instance.id, "AmbientOcclusion") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                CallbackInfoReturnable<Boolean> cir = data.getCIR();
                cir.setReturnValue(false);
            }
        });

        // Improve visibility underwater
        this.handlers.add(new EventHandler(instance.id, "UnderwaterVisibility") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                CallbackInfoReturnable<Float> cir = data.getCIR();
                cir.setReturnValue(0f);
            }
        });
    }

    @Override
    public void onEnable() {
        this.defaultGamma = CLIENT.getOptions().getGamma();
        super.onEnable();

        // Smoothly brighten the screen
        Fullbright instance = this;
        EventManager.bindEvent(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                double currentGamma = CLIENT.getOptions().getGamma();
                if (currentGamma < 16) {
                    CLIENT.getOptions().setGamma(currentGamma + 0.5);
                } else {
                    EventManager.releaseEvent(this);
                }
            }
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();

        // Smoothly darken the screen
        Fullbright instance = this;
        EventManager.bindEvent(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                if (CLIENT.getOptions().getGamma() > instance.defaultGamma) {
                    CLIENT.getOptions().setGamma(CLIENT.getOptions().getGamma() - 0.5);
                } else {
                    EventManager.releaseEvent(this);
                }
            }
        });
    }
}
