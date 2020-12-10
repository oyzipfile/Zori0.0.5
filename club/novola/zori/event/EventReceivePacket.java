// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventReceivePacket extends Event
{
    private Packet packet;
    
    public EventReceivePacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
