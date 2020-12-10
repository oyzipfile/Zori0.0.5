// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import club.novola.zori.module.Module;

public class ChatSuffix extends Module
{
    public ChatSuffix() {
        super("ChatSuffix", Category.PLAYER);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        final String ZoriChat = " \u23d0 \u1d22\u1d0f\u0280\u026a";
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".") || event.getMessage().startsWith(",") || event.getMessage().startsWith("-") || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) {
            return;
        }
        event.setMessage(event.getMessage() + ZoriChat);
    }
}
