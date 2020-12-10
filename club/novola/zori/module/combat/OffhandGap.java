// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class OffhandGap extends Module
{
    private boolean dragging;
    private int totems;
    private Setting<Integer> health;
    private Setting<Boolean> totemOnDisable;
    private Setting<Boolean> disableOthers;
    
    public OffhandGap() {
        super("OffhandGap", Category.COMBAT);
        this.dragging = false;
        this.totems = 0;
        this.health = (Setting<Integer>)this.register("Health", 15, 0, 40);
        this.totemOnDisable = (Setting<Boolean>)this.register("TotemOnDisable", false);
        this.disableOthers = (Setting<Boolean>)this.register("DisableOthers", false);
    }
    
    @Override
    public String getHudInfo() {
        return this.totems + "";
    }
    
    public void onEnable() {
        if (this.disableOthers.getValue()) {
            final ModuleManager moduleManager = Zori.getInstance().moduleManager;
            final AutoTotem autoTotem = (AutoTotem)ModuleManager.getModuleByName("AutoTotem");
            if (autoTotem.isEnabled()) {
                autoTotem.disable();
            }
            final ModuleManager moduleManager2 = Zori.getInstance().moduleManager;
            final OffhandCrystal offhandCrystal = (OffhandCrystal)ModuleManager.getModuleByName("OffhandCrystal");
            if (offhandCrystal.isEnabled()) {
                offhandCrystal.disable();
            }
        }
    }
    
    public void onDisable() {
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        final AutoTotem autoTotem = (AutoTotem)ModuleManager.getModuleByName("AutoTotem");
        if (this.totemOnDisable.getValue()) {}
        autoTotem.enable();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.isEnabled()) {
            return;
        }
        if (!this.totemOnDisable.getValue() || Wrapper.mc.currentScreen instanceof GuiContainer || Wrapper.getPlayer() == null) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            return;
        }
        if (!Wrapper.getPlayer().inventory.getItemStack().isEmpty() && !this.dragging) {
            for (int i = 0; i < 45; ++i) {
                if (Wrapper.getPlayer().inventory.getStackInSlot(i).isEmpty() || Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() == Items.AIR) {
                    final int slot = (i < 9) ? (i + 36) : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.getPlayer());
                    return;
                }
            }
        }
        if (Wrapper.getPlayer().getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.dragging = false;
            return;
        }
        if (this.dragging) {
            Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.getPlayer());
            this.dragging = false;
            return;
        }
        for (int i = 0; i < 45; ++i) {
            if (Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                final int slot = (i < 9) ? (i + 36) : i;
                Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.getPlayer());
                this.dragging = true;
                return;
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        final EntityPlayerSP player = Wrapper.getPlayer();
        if (player == null) {
            return;
        }
        if (!player.inventory.getItemStack().isEmpty() && !this.dragging) {
            for (int i = 0; i < 45; ++i) {
                if (player.inventory.getStackInSlot(i).isEmpty() || player.inventory.getStackInSlot(i).getItem() == Items.AIR) {
                    final int slot = (i < 9) ? (i + 36) : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)player);
                    return;
                }
            }
        }
        this.totems = ((player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) ? player.getHeldItemOffhand().getCount() : 0);
        for (final ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                this.totems += stack.getCount();
            }
        }
        final Item item = this.shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.GOLDEN_APPLE;
        if (player.getHeldItemOffhand().getItem() == item) {
            return;
        }
        if (this.dragging) {
            Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)player);
            this.dragging = false;
            return;
        }
        for (int j = 0; j < 45; ++j) {
            if (player.inventory.getStackInSlot(j).getItem() == item) {
                final int slot2 = (j < 9) ? (j + 36) : j;
                Wrapper.mc.playerController.windowClick(0, slot2, 0, ClickType.PICKUP, (EntityPlayer)player);
                this.dragging = true;
                return;
            }
        }
    }
    
    private boolean shouldTotem() {
        final boolean hp = Wrapper.getPlayer().getHealth() + Wrapper.getPlayer().getAbsorptionAmount() <= this.health.getValue();
        final boolean totemCount = this.totems > 0 || this.dragging || !Wrapper.getPlayer().inventory.getItemStack().isEmpty();
        return hp && totemCount;
    }
}
