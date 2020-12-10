// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import net.minecraft.client.gui.FontRenderer;
import club.novola.zori.Zori;
import net.minecraft.client.Minecraft;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.FPS;
import club.novola.zori.hud.HudComponent;

public class FPSComponent extends HudComponent<FPS>
{
    public FPSComponent() {
        super("FPS", 2, 2, FPS.INSTANCE);
    }
    
    @Override
    public void renderInGui(final int mouseX, final int mouseY) {
        super.renderInGui(mouseX, mouseY);
        Wrapper.getFontRenderer().drawStringWithShadow("FPS", (float)this.x, (float)this.y, -1);
    }
    
    @Override
    public void render() {
        super.render();
        final FontRenderer fontRenderer = Wrapper.getFontRenderer();
        final StringBuilder append = new StringBuilder().append("FPS: §f");
        final Minecraft mc = Wrapper.mc;
        fontRenderer.drawStringWithShadow(append.append(Minecraft.getDebugFPS()).toString(), (float)this.x, (float)this.y, Zori.getInstance().clientSettings.getColor());
        this.width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}
