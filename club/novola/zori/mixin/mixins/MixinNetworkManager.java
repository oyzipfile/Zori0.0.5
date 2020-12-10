// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import club.novola.zori.event.PacketSendEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.Packet;
import io.netty.channel.SimpleChannelInboundHandler;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<Packet<?>>
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void prePacketSent(final Packet<?> packetIn, final CallbackInfo ci) {
        final PacketSendEvent event = new PacketSendEvent(packetIn);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("TAIL") }, cancellable = true)
    private void postPacketSent(final Packet<?> packetIn, final CallbackInfo ci) {
        final PacketSendEvent.Post event = new PacketSendEvent.Post(packetIn);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
