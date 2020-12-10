// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Players extends Module
{
    public static Players INSTANCE;
    public Setting<Align> align;
    
    public Players() {
        super("Players", Category.HUD);
        this.align = (Setting<Align>)this.register("Align", Align.RIGHT);
        Players.INSTANCE = this;
    }
    
    public enum Align
    {
        LEFT, 
        RIGHT;
    }
}
