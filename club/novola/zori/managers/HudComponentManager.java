// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.ClickGUI;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import javax.annotation.Nullable;
import java.util.Iterator;
import net.minecraftforge.common.MinecraftForge;
import com.google.common.collect.Lists;
import club.novola.zori.hud.components.LogoComponent;
import club.novola.zori.hud.components.ArrayListComponent;
import club.novola.zori.hud.components.ArmorHUDComponent;
import club.novola.zori.hud.components.TotemsComponent;
import club.novola.zori.hud.components.PlayerViewComponent;
import club.novola.zori.hud.components.ArmorWarningComponent;
import club.novola.zori.hud.components.ServerComponent;
import club.novola.zori.hud.components.WelcomerComponent;
import club.novola.zori.hud.components.TPSComponent;
import club.novola.zori.hud.components.FPSComponent;
import club.novola.zori.hud.components.PlayersComponent;
import club.novola.zori.hud.components.InventoryComponent;
import club.novola.zori.hud.components.OffhandModeComponent;
import club.novola.zori.hud.components.PvpInfoComponent;
import club.novola.zori.hud.components.WatermarkComponent;
import club.novola.zori.hud.HudComponent;
import java.util.List;

public class HudComponentManager
{
    private List<HudComponent> components;
    
    public HudComponentManager() {
        this.components = (List<HudComponent>)Lists.newArrayList((Object[])new HudComponent[] { new WatermarkComponent(), new PvpInfoComponent(), new OffhandModeComponent(), new InventoryComponent(), new PlayersComponent(), new FPSComponent(), new TPSComponent(), new WelcomerComponent(), new ServerComponent(), new ArmorWarningComponent(), new PlayerViewComponent(), new TotemsComponent(), new ArmorHUDComponent(), new ArrayListComponent(), new LogoComponent() });
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public List<HudComponent> getComponents() {
        return this.components;
    }
    
    @Nullable
    public HudComponent getComponentByName(final String name) {
        for (final HudComponent component : this.components) {
            if (component.getName().equalsIgnoreCase(name)) {
                return component;
            }
        }
        return null;
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Post event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HOTBAR) && !(Wrapper.mc.currentScreen instanceof ClickGUI)) {
            for (final HudComponent c : this.components) {
                if (c.isInvisible()) {
                    continue;
                }
                c.render();
            }
        }
    }
}
