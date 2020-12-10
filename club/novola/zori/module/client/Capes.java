// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.client;

import club.novola.zori.module.Module;

public class Capes extends Module
{
    public static Boolean enabled;
    
    public Capes() {
        super("Capes", Category.CLIENT);
    }
    
    public void onEnable() {
        Capes.enabled = true;
    }
    
    public void onDisable() {
        Capes.enabled = false;
    }
    
    static {
        Capes.enabled = false;
    }
}
