// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemExpBottle;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class FastXP extends Module
{
    public FastXP() {
        super("FastXP", Category.PLAYER);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemExpBottle || this.offhand()) {
            Wrapper.mc.rightClickDelayTimer = 0;
        }
    }
    
    private boolean offhand() {
        final boolean item = Wrapper.getPlayer().getHeldItemOffhand().getItem() instanceof ItemExpBottle;
        final boolean block = Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemBlock;
        return item && !block;
    }
}
