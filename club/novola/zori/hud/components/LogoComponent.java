// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.ResourceLocation;
import club.novola.zori.module.hud.Logo;
import club.novola.zori.hud.HudComponent;

public class LogoComponent extends HudComponent<Logo>
{
    private ResourceLocation logo;
    
    public LogoComponent() {
        super("Logo", 100, 100, Logo.INSTANCE);
        this.onLoad();
    }
    
    @Override
    public void render() {
        Wrapper.mc.renderEngine.bindTexture(this.logo);
        this.width = ((Logo)this.module).imageWidth.getValue();
        this.height = ((Logo)this.module).imageHeight.getValue();
        GlStateManager.color(255.0f, 255.0f, 255.0f);
        Gui.drawScaledCustomSizeModalRect(this.x + 4, this.y + 4, 7.0f, 7.0f, this.width - 7, this.height - 7, this.width, this.height, (float)this.width, (float)this.height);
    }
    
    private void onLoad() {
        try {
            if (((Logo)this.module).zoriLogo.getValue()) {
                this.logo = new ResourceLocation("textures/zori.png");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
