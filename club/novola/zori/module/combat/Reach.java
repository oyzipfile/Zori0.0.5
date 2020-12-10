// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Reach extends Module
{
    public Setting<Float> distance;
    
    public Reach() {
        super("Reach", Category.COMBAT);
        this.distance = (Setting<Float>)this.register("Distance", 5.5f, 0.0f, 10.0f);
    }
}
