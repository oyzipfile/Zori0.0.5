// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import java.util.function.ToIntFunction;
import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.RenderHelper;
import club.novola.zori.util.ColorUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.ArmorHUD;
import club.novola.zori.hud.HudComponent;

public class ArmorHUDComponent extends HudComponent<ArmorHUD>
{
    private int offHandHeldItemCount;
    private int armourCompress;
    private int armourSpacing;
    
    public ArmorHUDComponent() {
        super("ArmorHUD", 300, 300, ArmorHUD.INSTANCE);
        this.width = 0;
        this.height = 0;
    }
    
    @Override
    public void render() {
        final ScaledResolution resolution = new ScaledResolution(Wrapper.mc);
        final RenderItem itemRender = Wrapper.mc.getRenderItem();
        GlStateManager.enableTexture2D();
        final int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        final int y = resolution.getScaledHeight() - 55 - (Wrapper.mc.player.isInWater() ? 10 : 0);
        for (final ItemStack is : Wrapper.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * this.armourSpacing + this.armourCompress;
            GlStateManager.enableDepth();
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, is, x, y, "");
            itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            Wrapper.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - Wrapper.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 16777215);
            if (((ArmorHUD)this.module).damageA.getValue()) {
                final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
                final float red = 1.0f - green;
                final int dmg = 100 - (int)(red * 100.0f);
                Wrapper.mc.fontRenderer.drawStringWithShadow(dmg + "", (float)(x + 8 - Wrapper.mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColorUtil.toRGBA((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
        if (((ArmorHUD)this.module).extraInfo.getValue()) {
            for (final ItemStack is : Wrapper.mc.player.inventory.offHandInventory) {
                final Item helfInOffHand = Wrapper.mc.player.getHeldItemOffhand().getItem();
                this.offHandHeldItemCount = this.getItemsOffHand(helfInOffHand);
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GlStateManager.enableBlend();
                GlStateManager.pushAttrib();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableDepth();
                Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(is, 572, y);
                itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, is, 572, y, String.valueOf(this.offHandHeldItemCount));
                GlStateManager.enableDepth();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                GlStateManager.disableBlend();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
            }
        }
        if (((ArmorHUD)this.module).extraInfo.getValue()) {
            final Item currentHeldItem = Wrapper.mc.player.inventory.getCurrentItem().getItem();
            final int currentHeldItemCount = Wrapper.mc.player.inventory.getCurrentItem().getCount();
            final ItemStack stackHeld = new ItemStack(currentHeldItem, 1);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            GlStateManager.pushAttrib();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableDepth();
            Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(stackHeld, 556, y);
            itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, stackHeld, 556, y, String.valueOf(currentHeldItemCount));
            GlStateManager.enableDepth();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.disableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        if (((ArmorHUD)this.module).extraInfo.getValue()) {
            this.armourCompress = 14;
            this.armourSpacing = 17;
        }
        else {
            this.armourCompress = 2;
            this.armourSpacing = 20;
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
    
    int getItemsOffHand(final Item i) {
        return Wrapper.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::func_190916_E).sum();
    }
}
