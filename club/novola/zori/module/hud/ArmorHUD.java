// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class ArmorHUD extends Module
{
    public Setting<Boolean> damageA;
    public Setting<Boolean> extraInfo;
    public static ArmorHUD INSTANCE;
    
    public ArmorHUD() {
        super("ArmorHUD", Category.HUD);
        this.damageA = (Setting<Boolean>)this.register("Damage", true);
        this.extraInfo = (Setting<Boolean>)this.register("ExtraInfo", false);
        ArmorHUD.INSTANCE = this;
    }
}
