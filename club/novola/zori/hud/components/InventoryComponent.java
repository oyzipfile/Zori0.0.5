// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.InventoryPreview;
import club.novola.zori.hud.HudComponent;

public class InventoryComponent extends HudComponent<InventoryPreview>
{
    public InventoryComponent() {
        super("Inventory", 0, 0, InventoryPreview.INSTANCE);
        this.width = 146;
        this.height = 50;
    }
    
    @Override
    public void render() {
        super.render();
        if (Wrapper.getPlayer() == null || Wrapper.getPlayer().inventory == null) {
            return;
        }
        switch (((InventoryPreview)this.module).background.getValue()) {
            case CLEAR:
            case NORMAL:
            case TRANS: {
                final int i1 = this.height / 5;
                final int i2 = i1 * 2;
                final int i3 = i1 * 3;
                final int i4 = i1 * 4;
                final int i5 = i1 * 5;
                final int cyan = -1436823814;
                final int pink = -1426740808;
                final int white = -1426063361;
                GlStateManager.enableAlpha();
                Gui.drawRect(this.x, this.y, this.x + this.width, this.y + i1, cyan);
                Gui.drawRect(this.x, this.y + i1, this.x + this.width, this.y + i2, pink);
                Gui.drawRect(this.x, this.y + i2, this.x + this.width, this.y + i3, white);
                Gui.drawRect(this.x, this.y + i3, this.x + this.width, this.y + i4, pink);
                Gui.drawRect(this.x, this.y + i4, this.x + this.width, this.y + i5, cyan);
                GlStateManager.disableAlpha();
                break;
            }
        }
        int slotX = 0;
        int slotY = 0;
        RenderHelper.enableGUIStandardItemLighting();
        for (final ItemStack stack : Wrapper.getPlayer().inventory.mainInventory) {
            if (Wrapper.getPlayer().inventory.mainInventory.indexOf((Object)stack) < 9) {
                continue;
            }
            Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, this.x + slotX, this.y + slotY);
            Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.getFontRenderer(), stack, this.x + slotX, this.y + slotY);
            if (slotX < 128) {
                slotX += 16;
            }
            else {
                slotX = 0;
                slotY += 16;
            }
        }
        RenderHelper.disableStandardItemLighting();
    }
}
