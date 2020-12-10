// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Offhand extends Module
{
    private Setting<Integer> health;
    private Setting<Boolean> soft;
    private Setting<Mode> mode;
    Item item;
    private boolean dragging;
    private int totems;
    
    public Offhand() {
        super("OffhandNew", Category.COMBAT);
        this.health = (Setting<Integer>)this.register("Health", 16, 0, 40);
        this.soft = (Setting<Boolean>)this.register("Soft", false);
        this.mode = (Setting<Mode>)this.register("Mode", Mode.TOTEM);
        this.dragging = false;
        this.totems = 0;
    }
    
    @Override
    public String getHudInfo() {
        return this.totems + "";
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
        if (this.mode.getValue().equals(Mode.TOTEM)) {
            this.totems = 0;
            for (final ItemStack stack : player.inventory.mainInventory) {
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    this.totems += stack.getCount();
                }
            }
            if (player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                this.totems += player.getHeldItemOffhand().getCount();
                return;
            }
            if (this.soft.getValue() && !player.getHeldItemOffhand().isEmpty()) {
                return;
            }
            if (this.dragging) {
                Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)player);
                this.dragging = false;
                return;
            }
            for (int i = 0; i < 45; ++i) {
                if (player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    final int slot = (i < 9) ? (i + 36) : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)player);
                    this.dragging = true;
                    return;
                }
            }
        }
        else {
            this.totems = ((player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) ? player.getHeldItemOffhand().getCount() : 0);
            for (final ItemStack stack : player.inventory.mainInventory) {
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    this.totems += stack.getCount();
                }
            }
            if (this.mode.getValue().equals(Mode.CRYSTAL)) {
                this.item = (this.shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.END_CRYSTAL);
            }
            else {
                this.item = (this.shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.GOLDEN_APPLE);
            }
            if (player.getHeldItemOffhand().getItem() == this.item) {
                return;
            }
            if (this.dragging) {
                Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)player);
                this.dragging = false;
                return;
            }
            for (int i = 0; i < 45; ++i) {
                if (player.inventory.getStackInSlot(i).getItem() == this.item) {
                    final int slot = (i < 9) ? (i + 36) : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)player);
                    this.dragging = true;
                    return;
                }
            }
        }
    }
    
    private boolean shouldTotem() {
        final boolean hp = Wrapper.getPlayer().getHealth() + Wrapper.getPlayer().getAbsorptionAmount() <= this.health.getValue();
        final boolean totemCount = this.totems > 0 || this.dragging || !Wrapper.getPlayer().inventory.getItemStack().isEmpty();
        return hp && totemCount;
    }
    
    public enum Mode
    {
        TOTEM, 
        CRYSTAL, 
        GAP;
    }
}
