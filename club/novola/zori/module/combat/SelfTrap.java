// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import club.novola.zori.util.EntityUtils;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class SelfTrap extends Module
{
    private Setting<Boolean> full;
    private Setting<Integer> bpt;
    private Setting<Boolean> rotateS;
    private int blocksPlaced;
    private boolean switchDelay;
    private int oldSlot;
    
    public SelfTrap() {
        super("SelfTrap", Category.COMBAT);
        this.full = (Setting<Boolean>)this.register("Full", false);
        this.bpt = (Setting<Integer>)this.register("BlocksPerTick", 4, 0, 13);
        this.rotateS = (Setting<Boolean>)this.register("Rotate", true);
        this.blocksPlaced = 0;
        this.switchDelay = false;
        this.oldSlot = -1;
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || this.isSolid(new BlockPos(this.getPlayerPos()).up())) {
            return;
        }
        BlockPos[] offsets;
        if (this.full.getValue()) {
            offsets = new BlockPos[0];
        }
        else {
            offsets = new BlockPos[] { new BlockPos(this.getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()).down(), new BlockPos(this.getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()), new BlockPos(this.getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()).up(), new BlockPos(this.getPlayerPos()).up() };
        }
        this.blocksPlaced = 0;
        for (final BlockPos blockPos : offsets) {
            if (this.blocksPlaced >= this.bpt.getValue()) {
                return;
            }
            if (this.canPlace(blockPos)) {
                this.placeBlock(blockPos);
                ++this.blocksPlaced;
            }
        }
    }
    
    private void placeBlock(final BlockPos blockPos) {
    }
    
    private boolean canPlace(final BlockPos blockPos) {
        final List<Entity> entities = new ArrayList<Entity>();
        for (final Entity e : Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (!(e instanceof EntityItem)) {
                if (e instanceof EntityXPOrb) {
                    continue;
                }
                entities.add(e);
            }
        }
        return !this.isSolid(blockPos) && entities.isEmpty();
    }
    
    private Vec3d getPlayerPos() {
        return EntityUtils.INSTANCE.getInterpolatedPos((Entity)Wrapper.getPlayer());
    }
    
    private boolean isSolid(final BlockPos blockPos) {
        return !Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable();
    }
}
