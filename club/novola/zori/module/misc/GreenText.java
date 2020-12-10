// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import club.novola.zori.module.Module;

public class GreenText extends Module
{
    String GREATERTHAN;
    
    public GreenText() {
        super("GreenText", Category.MISC);
        this.GREATERTHAN = ">";
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".") || event.getMessage().startsWith(",") || event.getMessage().startsWith("-") || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) {
            return;
        }
        event.setMessage(this.GREATERTHAN + event.getMessage());
    }
}
