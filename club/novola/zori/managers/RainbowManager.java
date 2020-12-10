// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.Color;
import club.novola.zori.Zori;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;

public class RainbowManager
{
    private float hue;
    private int hex;
    
    public RainbowManager() {
        this.hue = 0.0f;
        this.hex = -1;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        this.hue += Zori.getInstance().clientSettings.colors.rainbowSpeed.getValue() / 2000.0f;
        this.hex = Color.getHSBColor(this.hue, 1.0f, 1.0f).getRGB();
    }
    
    public int getHex() {
        return this.hex;
    }
    
    public Color getColor() {
        return new Color(this.hex);
    }
    
    public Color getColor(final int alpha) {
        final Color c = new Color(this.hex);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }
}
