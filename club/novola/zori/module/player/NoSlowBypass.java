// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class NoSlowBypass extends Module
{
    private boolean sneaking;
    
    public NoSlowBypass() {
        super("NoSlowBypass", Category.PLAYER);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.world != null) {
            final Item item = Wrapper.getPlayer().getActiveItemStack().getItem();
            if (this.sneaking && ((!Wrapper.getPlayer().isHandActive() && item instanceof ItemFood) || item instanceof ItemBow || item instanceof ItemPotion || !(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion))) {
                Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.getPlayer(), CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneaking = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final LivingEntityUseItemEvent event) {
        if (!this.sneaking) {
            Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.getPlayer(), CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }
}
