// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import java.awt.Color;
import club.novola.zori.Zori;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class SkyColor extends Module
{
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Boolean> sync;
    
    public SkyColor() {
        super("SkyColor", Category.RENDER);
        this.red = (Setting<Integer>)this.register("Red", 255, 0, 255);
        this.green = (Setting<Integer>)this.register("Green", 255, 0, 255);
        this.blue = (Setting<Integer>)this.register("Blue", 255, 0, 255);
        this.sync = (Setting<Boolean>)this.register("Sync", false);
    }
    
    @SubscribeEvent
    public void fogColors(final EntityViewRenderEvent.FogColors event) {
        event.setRed(this.red.getValue() / 255.0f);
        event.setBlue(this.blue.getValue() / 255.0f);
        event.setGreen(this.green.getValue() / 255.0f);
    }
    
    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
    
    @Override
    public void onUpdate() {
        if (this.sync.getValue()) {
            final Color c = Zori.getInstance().clientSettings.getColorr(255);
            this.red.setValue(c.getRed());
            this.green.setValue(c.getGreen());
            this.blue.setValue(c.getBlue());
        }
    }
}
