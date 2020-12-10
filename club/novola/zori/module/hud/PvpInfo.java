// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class PvpInfo extends Module
{
    public static PvpInfo INSTANCE;
    
    public PvpInfo() {
        super("PvpInfo", Category.HUD);
        PvpInfo.INSTANCE = this;
    }
}
