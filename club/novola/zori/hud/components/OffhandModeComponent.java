// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import club.novola.zori.managers.ModuleManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import club.novola.zori.module.hud.OffhandMode;
import club.novola.zori.hud.HudComponent;

public class OffhandModeComponent extends HudComponent<OffhandMode>
{
    private int totems;
    
    public OffhandModeComponent() {
        super("OffhandMode", 0, 190, OffhandMode.INSTANCE);
        this.totems = 0;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void render() {
        super.render();
        final String mode = this.isEnabled("AutoTotem") ? "TOTEM " : (this.isEnabled("OffhandCrystal") ? "CRYSTAL " : "NONE ");
        final String s = mode + ChatFormatting.GRAY + this.totems;
        Wrapper.getFontRenderer().drawStringWithShadow(s, (float)this.x, (float)this.y, Zori.getInstance().clientSettings.getColor());
        this.width = Wrapper.getFontRenderer().getStringWidth(s);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        this.totems = ((Wrapper.getPlayer().getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) ? Wrapper.getPlayer().getHeldItemOffhand().getCount() : 0);
        for (final ItemStack stack : Wrapper.getPlayer().inventory.mainInventory) {
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                this.totems += stack.getCount();
            }
        }
    }
    
    private boolean isEnabled(final String name) {
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        return ModuleManager.getModuleByName(name).isEnabled();
    }
}
