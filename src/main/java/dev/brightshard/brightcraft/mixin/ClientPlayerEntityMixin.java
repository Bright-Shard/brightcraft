package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.MathTools;
import dev.brightshard.brightcraft.managers.InteractionManager;
import dev.brightshard.brightcraft.managers.PlayerManager;
import dev.brightshard.brightcraft.events.EventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
public abstract class ClientPlayerEntityMixin implements PlayerManager {
    private boolean flying;
    private Vec3d velocity = Vec3d.ZERO;
    @Shadow protected abstract boolean wouldCollideAt(BlockPos pos);

    private ClientPlayerEntity getPlayerRaw() {
        return MinecraftClient.getInstance().player;
    }

    // Tickable events
    @Override
    public void movePlayer() {
        if (Hack.getHackById("Fly").enabled()) {
            this.setVel(this.velocity);
        } else if (this.velocity != Vec3d.ZERO) {
            this.setVel(this.velocity);
        }

        this.velocity = Vec3d.ZERO;
    }

    // Movement properties
    @Override
    public Vec3d getRot() {
        return this.getPlayerRaw().getRotationVector();
    }
    @Override
    public Vec3d getVel() {
        return this.getPlayerRaw().getVelocity();
    }
    @Override
    public void setVel(Vec3d velocity) {
        this.getPlayerRaw().setVelocity(velocity);
    }
    @Override
    public boolean sneaking() {
        return this.getPlayerRaw().isSneaking();
    }
    @Override
    public void sneaking(boolean isSneaking) {
        this.getPlayerRaw().setSneaking(isSneaking);
    }
    @Override
    public boolean noclip() {
        return this.getPlayerRaw().noClip;
    }
    @Override
    public void noclip(boolean enabled) {
        this.getPlayerRaw().noClip = enabled;
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
        this.velocity = new Vec3d(vel.x, this.getVel().y, vel.z);
    }
    @Override
    public void moveRight(double amount) {
        Vec3d vel = this.getRot().rotateY((float) Math.toRadians(-90)).multiply(amount);
        this.velocity = new Vec3d(vel.x, this.getVel().y, vel.z);
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
    public boolean flying() {
        return this.flying;
    }
    @Override
    public void flying(boolean isFlying) {
        this.flying = isFlying;
    }
    @Override
    public void setFallDistance(float distance) {
        this.getPlayerRaw().fallDistance = distance;
    }
    @Override
    public void setAirStrafingSpeed(float speed) {
        this.getPlayerRaw().airStrafingSpeed = speed;
    }

    // Other functions
    @Override
    public boolean playerWouldCollide(BlockPos pos) {
        return this.wouldCollideAt(pos);
    }
    @Override
    public void sendPacket(PlayerMoveC2SPacket packet) {
        this.getPlayerRaw().networkHandler.sendPacket(packet);
    }
    @Override
    public void setCurrentBreakingProgress(int progress) {
        this.getIM().setCurrentBreakingProgress(progress);
    }
    @Override
    public InteractionManager getIM() {
        return (InteractionManager) MinecraftClient.getInstance().interactionManager;
    }
    @Override
    public void hiddenChat(String message) {
        this.getPlayerRaw().sendMessage(Text.of(message));
    }

    // Events
    @Inject(method = "getUnderwaterVisibility", at = @At("HEAD"), cancellable = true)
    public void getUnderwaterVisibility(CallbackInfoReturnable<Float> cir) {
        EventManager.fireEvent("UnderwaterVisibility", new EventData<>(cir));
    }
}
