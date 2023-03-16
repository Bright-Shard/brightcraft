package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.EventData;
import dev.brightshard.brightcraft.lib.EventManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Thx WurstClient for finding this mixin :P
// Essentially XRay events for Sodium/Iris
@Pseudo
@Mixin(targets="me.jellysquid.mods.sodium.client.render.occlusion.BlockOcclusionCache", remap=false)
public class BlockOcclusionCacheMixin {
    @Inject(method="shouldDrawSide", at=@At("HEAD"), cancellable=true, remap=false)
    private void ShouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing,
                                  CallbackInfoReturnable<Boolean> cir) {
        EventManager.fireEvent("BlockShouldRender", new EventData<>(state.getBlock(), cir));
    }
}
