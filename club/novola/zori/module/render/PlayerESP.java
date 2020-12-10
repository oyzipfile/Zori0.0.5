// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import java.awt.Color;
import java.util.Iterator;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.EntityUtils;
import net.minecraft.entity.player.EntityPlayer;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class PlayerESP extends Module
{
    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
    }
    
    @Override
    public void onRender3D() {
        for (final EntityPlayer player : Wrapper.getWorld().playerEntities) {
            if (player != Wrapper.getPlayer()) {
                final Color c = EntityUtils.INSTANCE.getColoredHPB(player);
                RenderUtils.INSTANCE.drawBoundingBox(player.getRenderBoundingBox(), (float)(c.getRed() / 255), (float)(c.getGreen() / 255), (float)(c.getBlue() / 255), 1.0f, 1.0f);
            }
        }
    }
}
