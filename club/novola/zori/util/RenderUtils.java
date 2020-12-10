// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class RenderUtils
{
    private Tessellator tessellator;
    private BufferBuilder buffer;
    public static RenderUtils INSTANCE;
    
    public RenderUtils() {
        this.tessellator = Tessellator.getInstance();
        this.buffer = this.tessellator.getBuffer();
        RenderUtils.INSTANCE = this;
    }
    
    public void prepare() {
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    public void finish() {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void drawBoundingBox(final BlockPos blockPos, final int argb, final float width) {
        final Color color = new Color(argb);
        this.drawBoundingBox(blockPos, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, width);
    }
    
    public void drawBoundingBox(final BlockPos blockPos, final float red, final float green, final float blue, final float alpha, final float width) {
        this.drawBoundingBox(Wrapper.getWorld().getBlockState(blockPos).getSelectedBoundingBox((World)Wrapper.getWorld(), blockPos), red, green, blue, alpha, width);
    }
    
    public void drawBoundingBox(final AxisAlignedBB bb, final int argb, final float width) {
        final Color color = new Color(argb);
        this.drawBoundingBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, width);
    }
    
    public void drawBoundingBox(final AxisAlignedBB bb, final float red, final float green, final float blue, final float alpha, final float width) {
        final AxisAlignedBB box = bb.offset(this.getRenderOffset());
        this.drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, width);
    }
    
    public void drawBoundingBox(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final float red, final float green, final float blue, final float alpha, final float width) {
        this.prepare();
        GlStateManager.glLineWidth(width);
        this.buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        this.buffer.pos(minX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
        this.buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(minX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
        this.buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
        this.buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0f).endVertex();
        this.buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(maxX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
        this.tessellator.draw();
        this.finish();
    }
    
    public void drawBox(final BlockPos blockPos, final int argb) {
        final Color c = new Color(argb);
        this.drawBox(blockPos, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }
    
    public void drawBox(final BlockPos blockPos, final float red, final float green, final float blue, final float alpha) {
        this.drawBox(Wrapper.getWorld().getBlockState(blockPos).getSelectedBoundingBox((World)Wrapper.getWorld(), blockPos), red, green, blue, alpha);
    }
    
    public void drawBox(final AxisAlignedBB bb, final int argb) {
        final Color c = new Color(argb);
        this.drawBox(bb, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }
    
    public void drawBox(final AxisAlignedBB bb, final float red, final float green, final float blue, final float alpha) {
        final AxisAlignedBB box = bb.offset(this.getRenderOffset());
        this.drawBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }
    
    public void drawBox(final double p_189693_1_, final double p_189693_3_, final double p_189693_5_, final double p_189693_7_, final double p_189693_9_, final double p_189693_11_, final float red, final float green, final float blue, final float alpha) {
        this.prepare();
        this.buffer.begin(5, DefaultVertexFormats.POSITION_COLOR);
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.buffer.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(red, green, blue, alpha).endVertex();
        this.tessellator.draw();
        this.finish();
    }
    
    public Vec3d getRenderOffset() {
        return EntityUtils.INSTANCE.getInterpolateOffset(Objects.requireNonNull(Wrapper.mc.getRenderViewEntity()));
    }
}
