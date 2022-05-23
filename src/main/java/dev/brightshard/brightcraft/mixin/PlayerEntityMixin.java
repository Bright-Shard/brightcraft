package dev.brightshard.brightcraft.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static dev.brightshard.brightcraft.Main.LOGGER;
import dev.brightshard.brightcraft.lib.Hack;

@Mixin(net.minecraft.entity.player.PlayerEntity.class)
public class PlayerEntityMixin {
    private static Hack noclip = Hack.getHackById("NoClip");

    @Redirect(method = "tick", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    public boolean isSpectator(PlayerEntity instance) {
        if (noclip == null) {
            noclip = Hack.getHackById("NoClip");
            return instance.isSpectator();
        } else if (noclip.enabled()) {
            return true;
        } else {
            return instance.isSpectator();
        }
    }
}
