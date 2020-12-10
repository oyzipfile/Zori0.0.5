// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import net.minecraftforge.event.world.WorldEvent;
import java.util.function.BiConsumer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import club.novola.zori.event.PlayerKillEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.Zori;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import club.novola.zori.event.PacketSendEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
import java.util.concurrent.ConcurrentHashMap;

public class KillEventHelper
{
    public static KillEventHelper INSTANCE;
    private ConcurrentHashMap<EntityPlayer, Integer> targets;
    
    public KillEventHelper() {
        this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
        MinecraftForge.EVENT_BUS.register((Object)this);
        KillEventHelper.INSTANCE = this;
    }
    
    public void addTarget(final EntityPlayer player) {
        this.targets.put(player, 20);
    }
    
    public ConcurrentHashMap<EntityPlayer, Integer> getTargets() {
        return this.targets;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketSendEvent event) {
        if (Wrapper.getWorld() == null) {
            return;
        }
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (packet.getAction().equals((Object)CPacketUseEntity.Action.ATTACK) && packet.getEntityFromWorld((World)Wrapper.getWorld()) != null && packet.getEntityFromWorld((World)Wrapper.getWorld()) instanceof EntityPlayer && Zori.getInstance().playerStatus.getStatus(packet.getEntityFromWorld((World)Wrapper.getWorld()).getName()) != 1) {
                this.addTarget((EntityPlayer)packet.getEntityFromWorld((World)Wrapper.getWorld()));
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && this.targets.containsKey(event.getEntityLiving())) {
            this.targets.remove(event.getEntityLiving());
            MinecraftForge.EVENT_BUS.post((Event)new PlayerKillEvent((EntityPlayer)event.getEntityLiving()));
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.getPlayer() == null || Wrapper.getPlayer().getHealth() <= 0.0f || Wrapper.getPlayer().isDead) {
            this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
            return;
        }
        final ConcurrentHashMap<EntityPlayer, Integer> targetsCopy = new ConcurrentHashMap<EntityPlayer, Integer>();
        this.targets.forEach(targetsCopy::put);
        targetsCopy.forEach((player, ticks) -> {
            this.targets.remove(player, ticks);
            if (player.getHealth() <= 0.0f || player.isDead) {
                MinecraftForge.EVENT_BUS.post((Event)new PlayerKillEvent(player));
            }
            else if (ticks > 0) {
                this.targets.put(player, ticks - 1);
            }
        });
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
    }
    
    @SubscribeEvent
    public void onWorldUnLoad(final WorldEvent.Unload event) {
        this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
    }
}
