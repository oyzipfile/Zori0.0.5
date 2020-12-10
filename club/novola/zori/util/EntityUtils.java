// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import java.awt.Color;
import java.text.DecimalFormat;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

public class EntityUtils
{
    public static EntityUtils INSTANCE;
    
    public EntityUtils() {
        EntityUtils.INSTANCE = this;
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final double ticks) {
        final double d1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks;
        final double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks;
        final double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks;
        return new Vec3d(d1, d2, d3);
    }
    
    public Vec3d getInterpolatedPos(final Entity entity) {
        final double d1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Wrapper.mc.getRenderPartialTicks();
        final double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Wrapper.mc.getRenderPartialTicks();
        final double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Wrapper.mc.getRenderPartialTicks();
        return new Vec3d(d1, d2, d3);
    }
    
    public Vec3d getInterpolateOffset(final Entity entity) {
        final double d1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Wrapper.mc.getRenderPartialTicks();
        final double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Wrapper.mc.getRenderPartialTicks();
        final double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Wrapper.mc.getRenderPartialTicks();
        return new Vec3d(-d1, -d2, -d3);
    }
    
    public String getColoredHP(final EntityPlayer player) {
        final int hp = (int)(player.getHealth() + player.getAbsorptionAmount());
        ChatFormatting cf = ChatFormatting.DARK_RED;
        if (hp >= 6 && hp <= 8) {
            cf = ChatFormatting.RED;
        }
        else if (hp >= 9 && hp <= 12) {
            cf = ChatFormatting.GOLD;
        }
        else if (hp >= 13 && hp <= 16) {
            cf = ChatFormatting.YELLOW;
        }
        else if (hp >= 17 && hp <= 19) {
            cf = ChatFormatting.DARK_GREEN;
        }
        else if (hp >= 20) {
            cf = ChatFormatting.GREEN;
        }
        return cf + new DecimalFormat("00").format(hp);
    }
    
    public Color getColoredHPB(final EntityPlayer player) {
        final int hp = (int)(player.getHealth() + player.getAbsorptionAmount());
        Color c = Color.RED;
        if (hp >= 6 && hp <= 8) {
            c = Color.RED;
        }
        else if (hp >= 9 && hp <= 12) {
            c = Color.ORANGE;
        }
        else if (hp >= 13 && hp <= 16) {
            c = Color.YELLOW;
        }
        else if (hp >= 17 && hp <= 19) {
            c = Color.GREEN;
        }
        else if (hp >= 20) {
            c = Color.GREEN;
        }
        return c;
    }
}
