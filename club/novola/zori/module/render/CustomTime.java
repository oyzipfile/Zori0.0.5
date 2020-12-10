// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.render;

import club.novola.zori.util.Wrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import club.novola.zori.event.EventReceivePacket;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class CustomTime extends Module
{
    private long time;
    private Setting<Long> gametime;
    
    public CustomTime() {
        super("CustomTime", Category.RENDER);
        this.time = 0L;
        this.gametime = (Setting<Long>)this.register("Time", 18000L, 0L, 23992L);
    }
    
    @SubscribeEvent
    public void onTime(final EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.time = ((SPacketTimeUpdate)event.getPacket()).getWorldTime();
        }
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world == null) {
            return;
        }
        Wrapper.mc.world.setWorldTime((long)this.gametime.getValue());
    }
    
    public void onEnable() {
        this.time = Wrapper.mc.world.getWorldTime();
    }
    
    public void onDisable() {
        Wrapper.mc.world.setWorldTime(this.time);
    }
}
