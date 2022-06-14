package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.managers.GameOptionsManager;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.option.GameOptions.class)
public class GameOptionsMixin implements GameOptionsManager {
    @Final
    @Shadow
    private SimpleOption<Double> gamma;

    @Override
    public void setGamma(double gamma) {
        this.gamma.setValue(gamma);
    }

    @Override
    public double getGamma() {
        return this.gamma.getValue();
    }
}
