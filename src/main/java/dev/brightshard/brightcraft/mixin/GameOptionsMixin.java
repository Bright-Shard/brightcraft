package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.patches.PatchedGameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

@Mixin(net.minecraft.client.option.GameOptions.class)
public class GameOptionsMixin implements PatchedGameOptions {
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