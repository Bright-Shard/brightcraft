package dev.brightshard.brightcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import dev.brightshard.brightcraft.ui.SettingsMenu;

@Mixin(net.minecraft.client.gui.screen.GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
	public GameMenuScreenMixin(Text currentTitle) {
		super(currentTitle);
	}

	@Inject(at = @At("HEAD"), method = "init")
	private void init(CallbackInfo info) {
		this.addDrawableChild(new ButtonWidget(
				5, 5, 110, 20,
				Text.of("BrightCraft Settings"),
				button -> {
					assert client != null;
					client.setScreen(SettingsMenu.getInstance());
				}
		));
	}
}
