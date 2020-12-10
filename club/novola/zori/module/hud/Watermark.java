// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class Watermark extends Module
{
    public static Watermark INSTANCE;
    
    public Watermark() {
        super("Watermark", Category.HUD);
        Watermark.INSTANCE = this;
    }
}
