package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.entity.player.PlayerEntity.class)
public class PlayerEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    public boolean isSpectator(PlayerEntity playerEntity) {
        if (Hack.getHack(Hack.HackType.NoClip).enabled) {
            return true;
        } else {
            return playerEntity.isSpectator();
        }
    }
}

