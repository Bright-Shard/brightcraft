package dev.brightshard.brightcraft.managers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.WorldRenderer;

public interface ClientManager {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
    GameOptionsManager getOptions();
    void setScreen(Screen screen);
}
