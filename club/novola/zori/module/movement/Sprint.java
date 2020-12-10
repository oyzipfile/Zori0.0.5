// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.movement;

import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Sprint extends Module
{
    private Setting<Boolean> onlyForward;
    
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        this.onlyForward = (Setting<Boolean>)this.register("OnlyForward", false);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (this.onlyForward.getValue()) {
            if (Wrapper.getPlayer().moveForward > 0.0f) {
                Wrapper.getPlayer().setSprinting(true);
            }
        }
        else if (Wrapper.getPlayer().moveForward != 0.0f) {
            Wrapper.getPlayer().setSprinting(true);
        }
    }
}
