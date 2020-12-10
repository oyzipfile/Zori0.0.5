// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class FPS extends Module
{
    public static FPS INSTANCE;
    
    public FPS() {
        super("FPS", Category.HUD);
        FPS.INSTANCE = this;
    }
}
