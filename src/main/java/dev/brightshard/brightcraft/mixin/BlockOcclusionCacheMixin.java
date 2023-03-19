package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Event.FullEvent;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import net.minecraft.block.Block;
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
        // Fire the BlockShouldRender event; if it returns false, prevent the block from rendering
        try (LockedBuffer<FullEvent<Block, Boolean>>.Lock lock = Events.BlockShouldRender.lock()) {
            Boolean render = lock.readBuffer().fire(state.getBlock());
            if (render != null && !render) {
                cir.cancel();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
