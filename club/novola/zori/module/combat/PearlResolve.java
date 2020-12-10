// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.command.Command;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class PearlResolve extends Module
{
    public Boolean enabled;
    public Setting<Boolean> zoriprefix;
    ConcurrentHashMap<UUID, Integer> uuid;
    
    public PearlResolve() {
        super("PearlResolver", Category.COMBAT);
        this.enabled = false;
        this.zoriprefix = (Setting<Boolean>)this.register("ZoriPrefix", true);
        this.uuid = new ConcurrentHashMap<UUID, Integer>();
    }
    
    public void onEnable() {
        this.enabled = true;
    }
    
    @Override
    public void onUpdate() {
        for (final Entity entity : Wrapper.getWorld().loadedEntityList) {
            if (entity instanceof EntityEnderPearl) {
                EntityPlayer closest = null;
                for (final EntityPlayer p : Wrapper.getWorld().playerEntities) {
                    if (closest == null || entity.getDistance((Entity)p) < entity.getDistance((Entity)closest)) {
                        closest = p;
                    }
                }
                if (closest == null || closest.getDistance(entity) >= 2.0f || this.uuid.containsKey(entity.getUniqueID()) || closest.getName().equalsIgnoreCase(Wrapper.getPlayer().getName())) {
                    continue;
                }
                this.uuid.put(entity.getUniqueID(), 200);
                if (this.zoriprefix.getValue()) {
                    Command.sendClientMessage(closest.getName() + " pearled " + this.getTitle(entity.getHorizontalFacing().getName()) + "!", false);
                }
                else {
                    Command.sendCustomPrefixMessage(ChatFormatting.RED + "<PealResolver>" + closest.getName() + " pearled " + this.getTitle(entity.getHorizontalFacing().getName()) + "!", false);
                }
            }
        }
        this.uuid.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.uuid.remove(name);
            }
            else {
                this.uuid.put(name, timeout - 1);
            }
        });
    }
    
    public String getTitle(final String in) {
        if (in.equalsIgnoreCase("west")) {
            return "east";
        }
        if (in.equalsIgnoreCase("east")) {
            return "west";
        }
        return in;
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (this.enabled) {
            this.disable();
        }
    }
}
