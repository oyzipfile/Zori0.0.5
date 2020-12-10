// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class ArmorWarning extends Module
{
    public static ArmorWarning INSTANCE;
    
    public ArmorWarning() {
        super("ArmorWarning", Category.HUD);
        ArmorWarning.INSTANCE = this;
    }
}
