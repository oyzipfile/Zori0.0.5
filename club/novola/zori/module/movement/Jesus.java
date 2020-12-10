// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.movement;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Jesus extends Module
{
    private Setting<Mode> mode;
    
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        this.mode = (Setting<Mode>)this.register("Mode", Mode.SOLID);
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals(Mode.SOLID) && Wrapper.mc.world != null) {
            if (isInWater((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking()) {
                Wrapper.mc.player.motionY = 0.1;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    Wrapper.mc.player.getRidingEntity().motionY = 0.2;
                }
            }
            if (isAboveWater((Entity)Wrapper.getPlayer()) && !isInWater((Entity)Wrapper.getPlayer()) && !isAboveLand((Entity)Wrapper.getPlayer()) && !Wrapper.mc.player.isSneaking()) {
                Wrapper.mc.player.motionY = 0.0;
                Wrapper.mc.player.onGround = true;
            }
        }
        if (this.mode.getValue().equals(Mode.DOLPHIN) && Wrapper.mc.world != null && isInWater((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking()) {
            Wrapper.mc.player.motionY = 0.1;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                Wrapper.mc.player.getRidingEntity().motionY = 0.2;
            }
        }
    }
    
    public static boolean isInWater(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY + 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Wrapper.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean isAboveWater(final Entity entity) {
        final double y = entity.posY - 0.03;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean isAboveLand(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY - 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Wrapper.mc.world.getBlockState(pos).getBlock().isFullBlock(Wrapper.mc.world.getBlockState(pos))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public enum Mode
    {
        SOLID, 
        DOLPHIN;
    }
}
