// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.Server;
import club.novola.zori.hud.HudComponent;

public class ServerComponent extends HudComponent<Server>
{
    public ServerComponent() {
        super("Server", 2, 2, Server.INSTANCE);
    }
    
    @Override
    public void renderInGui(final int mouseX, final int mouseY) {
        super.renderInGui(mouseX, mouseY);
        Wrapper.getFontRenderer().drawStringWithShadow("Server", (float)this.x, (float)this.y, -1);
    }
    
    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow("Server: §f" + Wrapper.mc.getCurrentServerData().serverIP, (float)this.x, (float)this.y, Zori.getInstance().clientSettings.getColor());
        this.width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}
