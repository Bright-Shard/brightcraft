package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.managers.PlayerManager;
import net.minecraft.text.Text;

public class Chat {
    private String longMessage = "";
    public static String divider = "§kbruhbruhbruhbruhbruhbruhbruhbruh§r";
    public static String label = "[§aBrightCraft§r] ";
    public static String on = "[§2ON§r]";
    public static String off = "[§4OFF§r]";

    public Chat() {}
    public Chat(String text) {
        PlayerManager.getInstance().localChat(label + text);
    }

    public Chat(Hack hack) {
        new Chat(hack.name + ": " + (hack.enabled() ? on : off));
    }

    public void addToMessage(String text) {
        this.longMessage = this.longMessage.concat(text);
    }
    public void clear() {
        this.longMessage = "";
    }
    public void send() {
        PlayerManager.getInstance().localChat(this.longMessage);
        this.clear();
    }
}
