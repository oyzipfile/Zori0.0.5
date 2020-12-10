// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.movement;

import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class BoatFly extends Module
{
    public BoatFly() {
        super("BoatFly", Category.MOVEMENT);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
            Wrapper.mc.player.getRidingEntity().motionY = 0.1;
        }
    }
}
