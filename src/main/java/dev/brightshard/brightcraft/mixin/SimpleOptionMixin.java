package dev.brightshard.brightcraft.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(net.minecraft.client.option.SimpleOption.class)
public class SimpleOptionMixin<T> {
    @Shadow
    T value;
    @Shadow
    @Final
    private Consumer<T> changeCallback;

    /**
     * @author BrightShard
     */
    @Overwrite
    public void setValue(T value) {
        if (!MinecraftClient.getInstance().isRunning()) {
            this.value = value;
            return;
        }
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            this.changeCallback.accept(this.value);
        }
    }
}
