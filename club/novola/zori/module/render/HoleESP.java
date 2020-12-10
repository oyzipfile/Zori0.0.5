// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import net.minecraft.init.Blocks;
import net.minecraft.block.BlockAir;
import java.awt.Color;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import club.novola.zori.module.Module;

public class HoleESP extends Module
{
    public int Red;
    public int Green;
    public int Blue;
    private HashMap<BlockPos, Boolean> holes;
    private Setting<Mode> mode;
    private Setting<Integer> range;
    private Setting<Integer> ored;
    private Setting<Integer> ogreen;
    private Setting<Integer> oblue;
    private Setting<Integer> bred;
    private Setting<Integer> bgreen;
    private Setting<Integer> bblue;
    private Setting<Integer> linewidth;
    private Setting<Boolean> sync;
    
    public HoleESP() {
        super("HoleESP", Category.RENDER);
        this.holes = new HashMap<BlockPos, Boolean>();
        this.mode = (Setting<Mode>)this.register("Mode", Mode.FULL);
        this.range = (Setting<Integer>)this.register("Range", 10, 1, 20);
        this.ored = (Setting<Integer>)this.register("ObbyRed", 255, 0, 255);
        this.ogreen = (Setting<Integer>)this.register("ObbyGreen", 0, 0, 255);
        this.oblue = (Setting<Integer>)this.register("ObbyBlue", 0, 0, 255);
        this.bred = (Setting<Integer>)this.register("BedrockRed", 0, 0, 255);
        this.bgreen = (Setting<Integer>)this.register("BedrockGreen", 255, 0, 255);
        this.bblue = (Setting<Integer>)this.register("BedrockBlue", 0, 0, 255);
        this.linewidth = (Setting<Integer>)this.register("LineWidth", 1, 1, 10);
        this.sync = (Setting<Boolean>)this.register("Sync", false);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null) {
            return;
        }
        this.holes = new HashMap<BlockPos, Boolean>();
        this.findHoles();
    }
    
    @Override
    public void onRender3D() {
        if (!this.holes.isEmpty() && Wrapper.getPlayer() != null && Wrapper.getWorld() != null) {
            Color c;
            this.holes.forEach((blockPos, isBedrock) -> {
                if (this.sync.getValue()) {
                    c = Zori.getInstance().clientSettings.getColorr(255);
                    this.Red = c.getRed();
                    this.Green = c.getGreen();
                    this.Blue = c.getBlue();
                }
                else if (isBedrock) {
                    this.Red = this.bred.getValue();
                    this.Green = this.bgreen.getValue();
                    this.Blue = this.bblue.getValue();
                }
                else {
                    this.Red = this.ored.getValue();
                    this.Green = this.ogreen.getValue();
                    this.Blue = this.oblue.getValue();
                }
                if (this.mode.getValue().equals(Mode.FULL)) {
                    RenderUtils.INSTANCE.drawBoundingBox(blockPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.5f, this.linewidth.getValue());
                    RenderUtils.INSTANCE.drawBox(blockPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.22f);
                }
                else if (this.mode.getValue().equals(Mode.OUTLINE)) {
                    RenderUtils.INSTANCE.drawBoundingBox(blockPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.5f, this.linewidth.getValue());
                }
            });
        }
    }
    
    public boolean isBedrock(final BlockPos blockPos) {
        final boolean air = Wrapper.getWorld().getBlockState(blockPos).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.up()).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.add(0, 2, 0)).getBlock() instanceof BlockAir;
        final boolean down = Wrapper.getWorld().getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK;
        final boolean north = Wrapper.getWorld().getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK;
        final boolean south = Wrapper.getWorld().getBlockState(blockPos.south()).getBlock() == Blocks.BEDROCK;
        final boolean west = Wrapper.getWorld().getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK;
        final boolean east = Wrapper.getWorld().getBlockState(blockPos.east()).getBlock() == Blocks.BEDROCK;
        return air && down && north && south && west && east;
    }
    
    public boolean isObby(final BlockPos blockPos) {
        final boolean air = Wrapper.getWorld().getBlockState(blockPos).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.up()).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.add(0, 2, 0)).getBlock() instanceof BlockAir;
        final boolean down = Wrapper.getWorld().getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK || this.obbyOrEchest(blockPos.down());
        final boolean north = Wrapper.getWorld().getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK || this.obbyOrEchest(blockPos.north());
        final boolean south = Wrapper.getWorld().getBlockState(blockPos.south()).getBlock() == Blocks.BEDROCK || this.obbyOrEchest(blockPos.south());
        final boolean west = Wrapper.getWorld().getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK || this.obbyOrEchest(blockPos.west());
        final boolean east = Wrapper.getWorld().getBlockState(blockPos.east()).getBlock() == Blocks.BEDROCK || this.obbyOrEchest(blockPos.east());
        return air && down && north && south && west && east;
    }
    
    private boolean obbyOrEchest(final BlockPos blockPos) {
        return Wrapper.getWorld() != null && (Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST);
    }
    
    private void findHoles() {
        if (Wrapper.mc.getRenderViewEntity() == null) {
            return;
        }
        final BlockPos loc = Wrapper.mc.getRenderViewEntity().getPosition();
        final float r = this.range.getValue();
        final int plus_y = 0;
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = cy - (int)r; y < cy + r; ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        if (this.isBedrock(l)) {
                            this.holes.put(l, true);
                        }
                        else if (this.isObby(l)) {
                            this.holes.put(l, false);
                        }
                    }
                }
            }
        }
    }
    
    public enum Mode
    {
        FULL, 
        OUTLINE;
    }
}
