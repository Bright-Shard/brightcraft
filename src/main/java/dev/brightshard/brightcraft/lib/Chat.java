package dev.brightshard.brightcraft.lib;

import net.minecraft.text.Text;

public class Chat {
    private String longMessage = "";
    public static String divider = "§kbruhbruhbruhbruhbruhbruhbruhbruh§r";
    public static String label = "[§aBrightCraft§r] ";
    public static String on = "[§2ON§r]";
    public static String off = "[§4OFF§r]";

    public Chat() {}
    public Chat(Text text) {
        PlayerManager.getInstance().getPlayer().sendSystemMessage(text, null);
    }
    public Chat(String text) {
        new Chat(Text.of(label + text));
    }

    public Chat(Hack hack) {
        new Chat(Text.of(label + hack.name + ": " + (hack.enabled() ? on : off)));
    }

    public void addToMessage(String text) {
        this.longMessage = this.longMessage.concat(text);
    }
    public void sendLongMessage() {
        PlayerManager.getInstance().getPlayer().sendSystemMessage(Text.of(this.longMessage), null);
    }
}
