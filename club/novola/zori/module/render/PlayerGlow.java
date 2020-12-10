// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class PlayerGlow extends Module
{
    public PlayerGlow() {
        super("PlayerGlow", Category.RENDER);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world != null) {
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (!entity.isGlowing() && entity instanceof EntityPlayer) {
                    entity.setGlowing(true);
                }
            }
        }
    }
    
    public void onDisable() {
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity.isGlowing() && entity instanceof EntityPlayer) {
                entity.setGlowing(false);
            }
        }
        super.onDisable();
    }
}
