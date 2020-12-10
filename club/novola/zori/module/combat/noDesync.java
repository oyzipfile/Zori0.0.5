// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import club.novola.zori.event.PacketReceivedEvent;
import club.novola.zori.module.Module;

public class noDesync extends Module
{
    public noDesync() {
        super("noDesync", Category.COMBAT);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketReceivedEvent event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                try {
                    for (final Entity e : Wrapper.getWorld().loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                            e.setDead();
                        }
                    }
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
