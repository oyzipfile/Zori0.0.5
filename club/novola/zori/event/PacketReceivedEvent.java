// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketReceivedEvent extends Event
{
    private Packet packet;
    
    public PacketReceivedEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public static class Post extends PacketReceivedEvent
    {
        public Post(final Packet packet) {
            super(packet);
        }
    }
}
