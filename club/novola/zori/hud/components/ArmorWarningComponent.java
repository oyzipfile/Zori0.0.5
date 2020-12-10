// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.gui.ScaledResolution;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.ArmorWarning;
import club.novola.zori.hud.HudComponent;

public class ArmorWarningComponent extends HudComponent<ArmorWarning>
{
    private boolean isArmorLow;
    
    public ArmorWarningComponent() {
        super("ArmorWarning", new ScaledResolution(Wrapper.mc).getScaledWidth() / 2 - Wrapper.getFontRenderer().getStringWidth("LOW ARMOR") / 2, 10, ArmorWarning.INSTANCE);
        this.isArmorLow = false;
        this.width = Wrapper.getFontRenderer().getStringWidth("Armor low!");
        this.height = Wrapper.getFontRenderer().FONT_HEIGHT;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void renderInGui(final int mouseX, final int mouseY) {
        super.renderInGui(mouseX, mouseY);
        if (!this.isArmorLow) {
            Wrapper.getFontRenderer().drawStringWithShadow("Armor Warning", (float)this.x, (float)this.y, -1);
        }
    }
    
    @Override
    public void render() {
        super.render();
        if (this.isArmorLow) {
            Wrapper.getFontRenderer().drawStringWithShadow(ChatFormatting.BOLD + "Armor low!", (float)this.x, (float)this.y, -65536);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.getPlayer() == null || this.isInvisible()) {
            return;
        }
        this.isArmorLow = false;
        for (final ItemStack stack : Wrapper.getPlayer().inventory.armorInventory) {
            final double dmg = stack.getItemDamage();
            final double max = stack.getMaxDamage();
            if (dmg > 0.0) {
                if (max <= 0.0) {
                    continue;
                }
                double percent = dmg / max;
                percent *= 100.0;
                if (percent > 65.0) {
                    this.isArmorLow = true;
                    break;
                }
                continue;
            }
        }
    }
}
