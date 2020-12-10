// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import net.minecraft.network.Packet;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import javax.annotation.Nullable;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    public static final Minecraft mc;
    public static volatile Wrapper INSTANCE;
    
    @Nullable
    public static EntityPlayerSP getPlayer() {
        return Wrapper.mc.player;
    }
    
    @Nullable
    public static WorldClient getWorld() {
        return Wrapper.mc.world;
    }
    
    public static FontRenderer getFontRenderer() {
        return Wrapper.mc.fontRenderer;
    }
    
    public void sendPacket(final Packet packet) {
        getPlayer().connection.sendPacket(packet);
    }
    
    static {
        mc = Minecraft.getMinecraft();
        Wrapper.INSTANCE = new Wrapper();
    }
}
