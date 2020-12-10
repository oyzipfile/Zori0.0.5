// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import club.novola.zori.Zori;
import net.minecraft.world.World;
import club.novola.zori.util.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.RayTraceResult;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class BlockHighlight extends Module
{
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Boolean> sync;
    private Setting<Integer> width;
    
    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER);
        this.red = (Setting<Integer>)this.register("Red", 10, 0, 255);
        this.green = (Setting<Integer>)this.register("Green", 100, 0, 255);
        this.blue = (Setting<Integer>)this.register("Blue", 200, 0, 255);
        this.sync = (Setting<Boolean>)this.register("Sync", false);
        this.width = (Setting<Integer>)this.register("Width", 1, 1, 10);
    }
    
    @Override
    public void onRender3D() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.mc.objectMouseOver == null || Wrapper.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        final BlockPos blockpos = Wrapper.mc.objectMouseOver.getBlockPos();
        final IBlockState iblockstate = Wrapper.getWorld().getBlockState(blockpos);
        if (iblockstate.getMaterial() != Material.AIR && this.sync.getValue()) {
            RenderUtils.INSTANCE.drawBoundingBox(iblockstate.getSelectedBoundingBox((World)Wrapper.getWorld(), blockpos), Zori.getInstance().clientSettings.getColor(), this.width.getValue());
        }
        else {
            RenderUtils.INSTANCE.drawBoundingBox(iblockstate.getSelectedBoundingBox((World)Wrapper.getWorld(), blockpos), this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, 0.5f, this.width.getValue());
        }
    }
}
