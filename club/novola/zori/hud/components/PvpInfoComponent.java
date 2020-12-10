// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import club.novola.zori.util.Wrapper;
import club.novola.zori.Zori;
import club.novola.zori.module.hud.PvpInfo;
import club.novola.zori.hud.HudComponent;

public class PvpInfoComponent extends HudComponent<PvpInfo>
{
    public PvpInfoComponent() {
        super("PvpInfo", 2, 200, PvpInfo.INSTANCE);
    }
    
    @Override
    public void render() {
        super.render();
        final String s = Zori.getInstance().moduleManager.getEnabledColor("AutoCrystal") + "CA  " + Zori.getInstance().moduleManager.getEnabledColor("Surround") + "SU  " + Zori.getInstance().moduleManager.getEnabledColor("AutoTrap") + "AT";
        Wrapper.getFontRenderer().drawStringWithShadow(s, (float)this.x, (float)this.y, -1);
        this.width = Wrapper.getFontRenderer().getStringWidth(s);
    }
}
