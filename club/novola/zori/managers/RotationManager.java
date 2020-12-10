// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import net.minecraft.entity.player.EntityPlayer;
import club.novola.zori.util.Wrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import club.novola.zori.event.PacketSendEvent;
import net.minecraftforge.common.MinecraftForge;

public class RotationManager
{
    private float yaw;
    private float pitch;
    private boolean shouldRotate;
    
    public RotationManager() {
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.shouldRotate = false;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            if (this.shouldRotate) {
                packet.yaw = this.yaw;
                packet.pitch = this.pitch;
            }
        }
    }
    
    public void rotate(final double x, final double y, final double z) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        final Double[] v = this.calculateLookAt(x, y, z, (EntityPlayer)Wrapper.getPlayer());
        this.shouldRotate = true;
        this.yaw = v[0].floatValue();
        this.pitch = v[1].floatValue();
    }
    
    public void reset() {
        this.shouldRotate = false;
        if (Wrapper.getPlayer() == null) {
            return;
        }
        this.yaw = Wrapper.getPlayer().rotationYaw;
        this.pitch = Wrapper.getPlayer().rotationPitch;
    }
    
    private Double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new Double[] { yaw, pitch };
    }
}
