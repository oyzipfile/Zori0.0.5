// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import net.minecraft.util.ResourceLocation;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class ShaderLoader extends Module
{
    private Setting<Mode> shader;
    
    public ShaderLoader() {
        super("ShaderLoader", Category.RENDER);
        this.shader = (Setting<Mode>)this.register("Shader", Mode.notch);
    }
    
    @Override
    public void onUpdate() {
        if (OpenGlHelper.shadersSupported && Wrapper.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
                Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            try {
                Wrapper.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (Wrapper.mc.entityRenderer.getShaderGroup() != null && Wrapper.mc.currentScreen == null) {
            Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    public void onDisable() {
        if (Wrapper.mc.entityRenderer.getShaderGroup() != null) {
            Wrapper.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    public enum Mode
    {
        notch, 
        antialias, 
        art, 
        bits, 
        blobs, 
        blobs2, 
        blur, 
        bumpy, 
        color_convolve, 
        creeper, 
        deconverge, 
        desaturate, 
        entity_outline, 
        flip, 
        fxaa, 
        green, 
        invert, 
        ntsc, 
        outline, 
        pencil, 
        phosphor, 
        scan_pincusion, 
        sobel, 
        spider, 
        wobble;
    }
}
