// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class OffhandMode extends Module
{
    public static OffhandMode INSTANCE;
    
    public OffhandMode() {
        super("OffhandMode", Category.HUD);
        OffhandMode.INSTANCE = this;
    }
}
