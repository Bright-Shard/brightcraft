package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.*;
import dev.brightshard.brightcraft.patches.*;
import static dev.brightshard.brightcraft.BrightCraft.*;
import dev.brightshard.brightcraft.lib.Event.EventType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements PatchedPlayer {
    private Vec3d velocity = Vec3d.ZERO;
    public boolean blockMovement = false;
    @Shadow protected abstract boolean wouldCollideAt(BlockPos pos);

    // Tickable events
    @Override
    public void movePlayer() {
        if (this.blockMovement) {
            this.velocity = Vec3d.ZERO;
            return;
        }

        if (Hack.getHack(Hack.HackType.Fly).enabled) {
            this.setVel(this.velocity);
        } else if (this.velocity != Vec3d.ZERO) {
            this.setVel(this.velocity);
        }

        this.velocity = Vec3d.ZERO;
    }

    // Movement properties
    @Override
    public Vec3d getRot() {
        return CLIENT.getPlayerRaw().getRotationVector();
    }
    @Override
    public Vec3d getVel() {
        return CLIENT.getPlayerRaw().getVelocity();
    }
    @Override
    public void setVel(Vec3d velocity) {
        if (this.blockMovement) return;
        CLIENT.getPlayerRaw().setVelocity(velocity);
    }
    @Override
    public boolean sneaking() {
        return CLIENT.getPlayerRaw().isSneaking();
    }
    @Override
    public void sneaking(boolean isSneaking) {
        CLIENT.getPlayerRaw().setSneaking(isSneaking);
    }
    @Override
    public boolean noclip() {
        return CLIENT.getPlayerRaw().noClip;
    }
    @Override
    public void noclip(boolean enabled) {
        CLIENT.getPlayerRaw().noClip = enabled;
    }

    // Move player
    @Override
    public void moveForwards(double amount) {
        this.velocity = MathTools.addVectors(this.velocity, this.getRot().multiply(amount));
    }
    @Override
    public void moveBackwards(double amount) {
        this.velocity = MathTools.addVectors(this.velocity, this.getRot().negate().multiply(amount));
    }
    @Override
    public void moveLeft(double amount) {
        Vec3d vel = this.getRot().rotateY((float) Math.toRadians(90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.velocity.y, vel.z));
    }
    @Override
    public void moveRight(double amount) {
        Vec3d vel = this.getRot().rotateY((float) Math.toRadians(-90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.velocity.y, vel.z));
    }
    @Override
    public void moveForwardsFlat(double amount) {
        Vec3d vel = this.getRot().multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.getVel().y, vel.z));
    }
    @Override
    public void moveBackwardsFlat(double amount) {
        Vec3d vel = this.getRot().negate().multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.getVel().y, vel.z));
    }
    @Override
    public void moveUp(double amount) {
        this.velocity = new Vec3d(this.velocity.x, amount, this.velocity.z);
    }
    @Override
    public void moveDown(double amount) {
        this.velocity = new Vec3d(this.velocity.x, -amount, this.velocity.z);
    }

    // Flight
    @Override
    public void setFallDistance(float distance) {
        CLIENT.getPlayerRaw().fallDistance = distance;
    }
    @Override
    public void setAirStrafingSpeed(float speed) {
        CLIENT.getPlayerRaw().airStrafingSpeed = speed;
    }
    @Override
    public boolean blockMovement() {
        return this.blockMovement;
    }
    @Override
    public void blockMovement(boolean blockMovement) {
        this.blockMovement = blockMovement;
    }

    // Other functions
    @Override
    public boolean playerWouldCollide(BlockPos pos) {
        return this.wouldCollideAt(pos);
    }
    @Override
    public void sendPacket(PlayerMoveC2SPacket packet) {
        CLIENT.getPlayerRaw().networkHandler.sendPacket(packet);
    }
    @Override
    public void setCurrentBreakingProgress(int progress) {
        this.getIM().setCurrentBreakingProgress(progress);
    }
    @Override
    public PatchedPlayerInteraction getIM() {
        return (PatchedPlayerInteraction) MinecraftClient.getInstance().interactionManager;
    }
    @Override
    public void hiddenChat(String message) {
        CLIENT.getPlayerRaw().sendMessage(Text.of(message));
    }

    // Events
    @Inject(method = "getUnderwaterVisibility", at = @At("HEAD"), cancellable = true)
    public void getUnderwaterVisibility(CallbackInfoReturnable<Float> cir) {
        Float visibility = EVENTS.fireReturnable(EventType.MaxUnderwaterVisibility);
        if (visibility != null) {
            cir.setReturnValue(visibility);
        }
    }
}
