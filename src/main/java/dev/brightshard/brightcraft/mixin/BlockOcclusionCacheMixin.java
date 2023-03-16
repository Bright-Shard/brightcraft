package dev.brightshard.brightcraft.mixin;

import static dev.brightshard.brightcraft.BrightCraft.EVENTS;

import dev.brightshard.brightcraft.lib.Event.EventType;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// BlockShouldRender event, for Sodium
@Pseudo
@Mixin(targets="me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache", remap=false)
public class BlockOcclusionCacheMixin {
    @Inject(method="shouldDrawSide", at=@At("HEAD"), cancellable=true, remap=false)
    private void ShouldDrawSide(
            BlockState state,
            BlockView view,
            BlockPos pos,
            Direction facing,
            CallbackInfoReturnable<Boolean> cir
    ) {
        Boolean shouldRender = EVENTS.fireReturnable(
                EventType.BlockShouldRender,
                state.getBlock()
        );
        if (shouldRender != null && !shouldRender) {
            cir.cancel();
        }
    }
}
