package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.managers.ClientPlayerEntityManager;
import dev.brightshard.brightcraft.events.EventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements ClientPlayerEntityManager {
    private final MinecraftClient client;
    @Shadow protected abstract boolean wouldCollideAt(BlockPos pos);

    protected ClientPlayerEntityMixin(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public ClientPlayerEntity getEntity() {
        return this.client.player;
    }
    @Override
    public boolean playerWouldCollide(BlockPos pos) {
        return this.wouldCollideAt(pos);
    }

    @Inject(method = "getUnderwaterVisibility", at = @At("HEAD"), cancellable = true)
    public void getUnderwaterVisibility(CallbackInfoReturnable<Float> cir) {
        EventManager.fireEvent("UnderwaterVisibility", new EventData<>(cir));
    }
}
