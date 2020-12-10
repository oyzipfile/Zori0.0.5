// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import net.minecraft.client.entity.EntityPlayerSP;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class ReverseStep extends Module
{
    public ReverseStep() {
        super("ReverseStep", Category.MOVEMENT);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isInLava()) {
            return;
        }
        if (Wrapper.getPlayer().onGround) {
            final EntityPlayerSP player = Wrapper.getPlayer();
            --player.motionY;
        }
    }
}
