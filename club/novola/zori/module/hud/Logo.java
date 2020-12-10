// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Logo extends Module
{
    public Setting<Integer> imageWidth;
    public Setting<Integer> imageHeight;
    public Setting<Boolean> zoriLogo;
    public static Logo INSTANCE;
    
    public Logo() {
        super("Logo", Category.HUD);
        this.imageWidth = (Setting<Integer>)this.register("ImageWidth", 100, 0, 1000);
        this.imageHeight = (Setting<Integer>)this.register("ImageHeight", 100, 0, 1000);
        this.zoriLogo = (Setting<Boolean>)this.register("ZoriLogo", true);
        Logo.INSTANCE = this;
    }
}
