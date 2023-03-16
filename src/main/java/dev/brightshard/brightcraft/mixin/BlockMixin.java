package dev.brightshard.brightcraft.mixin;

import static dev.brightshard.brightcraft.BrightCraft.EVENTS;

import dev.brightshard.brightcraft.lib.Event.EventType;

// MC imports
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

// Mixin bs
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "shouldDrawSide(Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;" +
            "Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    private static void shouldDrawSide(
            BlockState state,
            BlockView view,
            BlockPos pos,
            Direction dir,
            BlockPos otherPos,
            CallbackInfoReturnable<Boolean> cir
    ) {
        // Fire the BlockShouldRender event; if it returns false, prevent the block from rendering
        Boolean render = EVENTS.fireReturnable(
            EventType.BlockShouldRender,
            state.getBlock()
        );
        if (render != null && !render) {
            cir.cancel();
        }
    }
}
