// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class TPS extends Module
{
    public static TPS INSTANCE;
    
    public TPS() {
        super("TPS", Category.HUD);
        TPS.INSTANCE = this;
    }
}
