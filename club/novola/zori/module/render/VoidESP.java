// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import java.awt.Color;
import club.novola.zori.Zori;
import net.minecraft.client.renderer.RenderGlobal;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;
import club.novola.zori.module.Module;

public class VoidESP extends Module
{
    Minecraft mc;
    private Setting<Integer> range;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Boolean> sync;
    public final List<BlockPos> void_blocks;
    private final ICamera camera;
    
    public VoidESP() {
        super("VoidESP", Category.RENDER);
        this.mc = Minecraft.getMinecraft();
        this.range = (Setting<Integer>)this.register("Range", 10, 1, 20);
        this.red = (Setting<Integer>)this.register("Red", 10, 0, 255);
        this.green = (Setting<Integer>)this.register("Green", 100, 0, 255);
        this.blue = (Setting<Integer>)this.register("Blue", 200, 0, 255);
        this.sync = (Setting<Boolean>)this.register("Sync", false);
        this.void_blocks = new ArrayList<BlockPos>();
        this.camera = (ICamera)new Frustum();
    }
    
    @Override
    public void onUpdate() {
        if (this.mc.player == null) {
            return;
        }
        this.void_blocks.clear();
        final Vec3i player_pos = new Vec3i(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        for (int x = player_pos.getX() - this.range.getValue(); x < player_pos.getX() + this.range.getValue(); ++x) {
            for (int z = player_pos.getZ() - this.range.getValue(); z < player_pos.getZ() + this.range.getValue(); ++z) {
                for (int y = player_pos.getY() + this.range.getValue(); y > player_pos.getY() - this.range.getValue(); --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    if (this.is_void_hole(blockPos)) {
                        this.void_blocks.add(blockPos);
                    }
                }
            }
        }
    }
    
    public boolean is_void_hole(final BlockPos blockPos) {
        return blockPos.getY() == 0 && this.mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
    }
    
    @Override
    public void onRender3D() {
        final AxisAlignedBB bb;
        Color c;
        new ArrayList(this.void_blocks).forEach(pos -> {
            bb = new AxisAlignedBB(pos.getX() - this.mc.getRenderManager().viewerPosX, pos.getY() - this.mc.getRenderManager().viewerPosY, pos.getZ() - this.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
            this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
            if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                GL11.glLineWidth(1.5f);
                if (!this.sync.getValue()) {
                    RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, 0.5f);
                    RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, 0.22f);
                }
                else {
                    c = Zori.getInstance().clientSettings.getColorr(255);
                    RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 0.5f);
                    RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 0.22f);
                }
                GL11.glDisable(2848);
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        });
    }
}
