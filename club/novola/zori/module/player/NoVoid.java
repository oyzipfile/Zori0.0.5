// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class NoVoid extends Module
{
    private Setting<Integer> height;
    
    public NoVoid() {
        super("NoVoid", Category.PLAYER);
        this.height = (Setting<Integer>)this.register("Height", 3, 0, 256);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world != null) {
            if (Wrapper.mc.player.noClip || Wrapper.mc.player.posY > this.height.getValue()) {
                return;
            }
            final RayTraceResult trace = Wrapper.mc.world.rayTraceBlocks(Wrapper.mc.player.getPositionVector(), new Vec3d(Wrapper.mc.player.posX, 0.0, Wrapper.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
    }
}
