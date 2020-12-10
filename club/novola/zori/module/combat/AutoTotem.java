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
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class AutoTotem extends Module
{
    private Setting<Boolean> soft;
    private Setting<Boolean> disableOthers;
    private boolean dragging;
    private int totems;
    
    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
        this.soft = (Setting<Boolean>)this.register("Soft", false);
        this.disableOthers = (Setting<Boolean>)this.register("DisableOthers", false);
        this.dragging = false;
        this.totems = 0;
    }
    
    @Override
    public String getHudInfo() {
        return this.totems + "";
    }
    
    public void onEnable() {
        if (this.disableOthers.getValue()) {
            final ModuleManager moduleManager = Zori.getInstance().moduleManager;
            final OffhandCrystal offhandCrystal = (OffhandCrystal)ModuleManager.getModuleByName("OffhandCrystal");
            final ModuleManager moduleManager2 = Zori.getInstance().moduleManager;
            final OffhandGap offhandGap = (OffhandGap)ModuleManager.getModuleByName("OffhandGap");
            if (offhandCrystal.isEnabled()) {
                offhandCrystal.disable();
            }
            if (offhandGap.isEnabled()) {
                offhandGap.disable();
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
}
