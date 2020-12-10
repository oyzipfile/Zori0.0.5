// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;
import club.novola.zori.module.Module;

public class CustomFOV extends Module
{
    Minecraft mc;
    public float defaultFov;
    private Setting<Mode> mode;
    private Setting<Integer> fov;
    
    public CustomFOV() {
        super("CustomFOV", Category.RENDER);
        this.mc = Minecraft.getMinecraft();
        this.mode = (Setting<Mode>)this.register("Mode", Mode.FOV);
        this.fov = (Setting<Integer>)this.register("FOV", 130, 0, 170);
    }
    
    @SubscribeEvent
    public void fovOn(final EntityViewRenderEvent.FOVModifier e) {
        if (this.mode.getValue().equals(Mode.VMC)) {
            e.setFOV((float)this.fov.getValue());
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals(Mode.FOV)) {
            this.mc.gameSettings.fovSetting = this.fov.getValue();
        }
    }
    
    public void onEnable() {
        this.defaultFov = this.mc.gameSettings.fovSetting;
    }
    
    public void onDisable() {
        this.mc.gameSettings.fovSetting = this.defaultFov;
    }
    
    public enum Mode
    {
        FOV, 
        VMC;
    }
}
