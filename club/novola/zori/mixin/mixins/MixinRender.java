// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.mixin.mixins;

import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;

@Mixin({ Render.class })
public abstract class MixinRender<T extends Entity>
{
    @Shadow
    @Final
    protected RenderManager renderManager;
    
    @Inject(method = { "renderLivingLabel" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLivingLabel(final T entityIn, final String str, final double x, final double y, final double z, final int maxDistance, final CallbackInfo ci) {
    }
    
    private void drawNameplate(final FontRenderer fontRendererIn, final String str, final float x, final float y, final float z, final int verticalShift, final float viewerYaw, final float viewerPitch, final boolean isThirdPersonFrontal, final boolean isSneaking) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0f, 0.0f, 0.0f);
        float scale = 0.025f;
        if (Wrapper.mc.getRenderViewEntity() != null && Wrapper.mc.getRenderViewEntity().getDistance((double)x, (double)y, (double)z) / -10.0 < -1.0) {
            scale = (float)(0.02500000037252903 * (Wrapper.mc.getRenderViewEntity().getDistance((double)x, (double)y, (double)z) / -10.0));
        }
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        fontRendererIn.drawStringWithShadow(str, -fontRendererIn.getStringWidth(str) / 2.0f, (float)verticalShift, isSneaking ? 16776960 : -1);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
}
