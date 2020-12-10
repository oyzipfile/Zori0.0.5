// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class BlockLag extends Module
{
    public BlockLag() {
        super("BlockLag", Category.COMBAT);
    }
    
    public void onEnable() {
        Wrapper.getPlayer().jump();
        Wrapper.getPlayer().motionY = -0.405;
    }
}
