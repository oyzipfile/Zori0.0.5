// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import club.novola.zori.util.Wrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class NoRender extends Module
{
    private Setting<Boolean> armorBar;
    private Setting<Boolean> rain;
    private Setting<Boolean> antifog;
    private Setting<Boolean> nobob;
    
    public NoRender() {
        super("NoRender", Category.RENDER);
        this.armorBar = (Setting<Boolean>)this.register("ArmorBar", false);
        this.rain = (Setting<Boolean>)this.register("Rain", true);
        this.antifog = (Setting<Boolean>)this.register("AntiFog", true);
        this.nobob = (Setting<Boolean>)this.register("NoBob", true);
    }
    
    @SubscribeEvent
    public void preRenderGameOverlay(final RenderGameOverlayEvent.Pre event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.ARMOR) && this.armorBar.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world == null) {
            return;
        }
        if (this.rain.getValue()) {
            final Wrapper instance = Wrapper.INSTANCE;
            Wrapper.getWorld().setRainStrength(0.0f);
        }
        if (this.nobob.getValue()) {
            Wrapper.mc.gameSettings.viewBobbing = false;
        }
    }
    
    public void onEnable() {
        if (this.nobob.getValue()) {
            Wrapper.mc.gameSettings.viewBobbing = false;
        }
    }
    
    public void onDisable() {
        Wrapper.mc.gameSettings.viewBobbing = true;
    }
}
