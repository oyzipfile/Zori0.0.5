// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.Zori;
import net.minecraft.client.gui.GuiScreen;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class ClickGuiModule extends Module
{
    public static boolean enabledGui;
    
    public ClickGuiModule() {
        super("ClickGUI", Category.HUD);
        this.setBind(25);
    }
    
    public void onEnable() {
        ClickGuiModule.enabledGui = true;
        if (Wrapper.mc.currentScreen != null) {
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
        }
        Wrapper.mc.displayGuiScreen((GuiScreen)Zori.getInstance().clickGUI);
        this.disable();
    }
    
    static {
        ClickGuiModule.enabledGui = false;
    }
}
