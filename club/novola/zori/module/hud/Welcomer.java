// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Welcomer extends Module
{
    public Setting<Mode> welcomeMode;
    public static Welcomer INSTANCE;
    
    public Welcomer() {
        super("Welcomer", Category.HUD);
        this.welcomeMode = (Setting<Mode>)this.register("Mode", Mode.NORMAL);
        Welcomer.INSTANCE = this;
    }
    
    public enum Mode
    {
        NORMAL, 
        DOX, 
        FAKENAME;
    }
}
