// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import net.minecraft.util.EnumHand;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class OffhandSwing extends Module
{
    public OffhandSwing() {
        super("OffhandSwing", Category.RENDER);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world == null) {
            return;
        }
        Wrapper.getPlayer().swingingHand = EnumHand.OFF_HAND;
    }
}
