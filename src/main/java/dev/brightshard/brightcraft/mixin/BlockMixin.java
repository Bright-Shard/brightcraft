package dev.brightshard.brightcraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.Config;
import static dev.brightshard.brightcraft.Main.LOGGER;

import java.util.Arrays;

@Mixin(net.minecraft.block.Block.class)
public class BlockMixin {
    private static Hack xray = Hack.getHackById("XRay");

    @Inject(at = @At("RETURN"), method = "shouldDrawSide(Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;" +
            "Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction dir, BlockPos otherPos,
                                       CallbackInfoReturnable<Boolean> cir) {
        if (xray == null) {
            xray = Hack.getHackById("XRay");
        } else if (xray.enabled()) {
            if (Arrays.asList(Config.getInstance().visibleBlocks).contains(state.getBlock())) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }
}
