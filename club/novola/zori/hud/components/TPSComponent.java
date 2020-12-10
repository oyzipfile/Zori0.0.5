// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.util.TickRate;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.TPS;
import club.novola.zori.hud.HudComponent;

public class TPSComponent extends HudComponent<TPS>
{
    public TPSComponent() {
        super("TPS", 2, 2, TPS.INSTANCE);
    }
    
    @Override
    public void renderInGui(final int mouseX, final int mouseY) {
        super.renderInGui(mouseX, mouseY);
        Wrapper.getFontRenderer().drawStringWithShadow("TPS", (float)this.x, (float)this.y, -1);
    }
    
    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow("TPS: §f" + TickRate.TPS, (float)this.x, (float)this.y, Zori.getInstance().clientSettings.getColor());
        this.width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}
