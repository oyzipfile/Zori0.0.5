// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import club.novola.zori.module.hud.Totems;
import club.novola.zori.hud.HudComponent;

public class TotemsComponent extends HudComponent<Totems>
{
    public TotemsComponent() {
        super("Totems", 473, 453, Totems.INSTANCE);
        this.width = 16;
        this.height = 16;
    }
    
    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
    }
    
    private static void postitemrender() {
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void render() {
        int totems = Wrapper.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::func_190916_E).sum();
        if (Wrapper.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        final ItemStack items = new ItemStack(Items.TOTEM_OF_UNDYING, totems);
        this.itemrender(items);
    }
    
    private void itemrender(final ItemStack itemStack) {
        preitemrender();
        Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, this.x, this.y);
        Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.mc.fontRenderer, itemStack, this.x, this.y);
        postitemrender();
    }
}
