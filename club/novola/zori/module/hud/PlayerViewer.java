// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class PlayerViewer extends Module
{
    public Setting<Integer> scale;
    public static PlayerViewer INSTANCE;
    
    public PlayerViewer() {
        super("PlayerViewer", Category.HUD);
        this.scale = (Setting<Integer>)this.register("Scale", 30, 1, 100);
        PlayerViewer.INSTANCE = this;
    }
}
