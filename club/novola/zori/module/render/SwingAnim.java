// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class SwingAnim extends Module
{
    public SwingAnim() {
        super("SwingAnim", Category.MISC);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand < 1.0f) {
            Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
        }
        if (Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand != Wrapper.getPlayer().getHeldItemMainhand()) {
            Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand = Wrapper.getPlayer().getHeldItemMainhand();
        }
    }
}
