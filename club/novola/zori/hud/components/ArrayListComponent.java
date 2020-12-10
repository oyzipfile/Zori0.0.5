// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import java.util.Iterator;
import java.awt.Color;
import club.novola.zori.module.Module;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.util.ColorUtil;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.client.Arraylist;
import club.novola.zori.hud.HudComponent;

public class ArrayListComponent extends HudComponent<Arraylist>
{
    public ArrayListComponent() {
        super("Arraylist", 2, 100, Arraylist.INSTANCE);
        this.width = 70;
        this.height = Wrapper.mc.fontRenderer.FONT_HEIGHT;
    }
    
    @Override
    public void render() {
        super.render();
        if (Wrapper.mc.player != null && Wrapper.mc.world != null) {
            int currY = Wrapper.mc.fontRenderer.FONT_HEIGHT + 2;
            final Color c = Zori.getInstance().clientSettings.getColorr(255);
            final int nonrainbow = ColorUtil.toRGBA(c.getRed(), c.getGreen(), c.getBlue(), 255);
            for (final Module m : ModuleManager.getEnabledModules()) {
                if (m.isEnabled() && m.getDrawnBool()) {
                    Wrapper.getFontRenderer().drawStringWithShadow(m.getName(), (float)this.x, (float)(this.y + currY), nonrainbow);
                    currY += Wrapper.mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }
    }
}
