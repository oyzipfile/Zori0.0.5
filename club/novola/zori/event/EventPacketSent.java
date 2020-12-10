// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPacketSent extends Event
{
    private boolean cancel;
    private Packet packet;
    
    public EventPacketSent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
