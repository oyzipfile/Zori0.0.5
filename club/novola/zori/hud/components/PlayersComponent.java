// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.Zori;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import joptsimple.internal.Strings;
import java.util.ArrayList;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import club.novola.zori.util.Wrapper;
import net.minecraftforge.common.MinecraftForge;
import java.util.List;
import club.novola.zori.module.hud.Players;
import club.novola.zori.hud.HudComponent;

public class PlayersComponent extends HudComponent<Players>
{
    private List<String> players;
    
    public PlayersComponent() {
        super("Players", 0, 100, Players.INSTANCE);
        this.width = 0;
        this.height = 10;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void render() {
        super.render();
        if (Wrapper.getWorld() == null || Wrapper.getPlayer() == null || this.players == null || this.players.isEmpty()) {
            return;
        }
        int yy = 0;
        for (final String text : this.players) {
            this.drawString(text, this.x, this.y + yy);
            yy += Wrapper.getFontRenderer().FONT_HEIGHT;
        }
        this.height = Math.max(yy, 10);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.getWorld() == null || Wrapper.getPlayer() == null || this.isInvisible()) {
            return;
        }
        this.players = new ArrayList<String>();
        final boolean isOnCpvp = Wrapper.mc.getCurrentServerData() != null && !Strings.isNullOrEmpty(Wrapper.mc.getCurrentServerData().serverIP) && Wrapper.mc.getCurrentServerData().serverIP.equalsIgnoreCase("crystalpvp.cc");
        for (final EntityPlayer player : Wrapper.getWorld().playerEntities) {
            if (player == Wrapper.getPlayer()) {
                continue;
            }
            if (isOnCpvp && player.posY > 110.0) {
                continue;
            }
            final String name = player.getName();
            ChatFormatting color = ChatFormatting.GRAY;
            switch (Zori.getInstance().playerStatus.getStatus(name)) {
                case 1: {
                    color = ChatFormatting.AQUA;
                    break;
                }
                case -1: {
                    color = ChatFormatting.RED;
                    break;
                }
            }
            final String strength = this.hasStrength(player) ? (ChatFormatting.DARK_RED + "[ST] ") : "";
            final StringBuilder sb = new StringBuilder(strength).append(color).append(name).append(" ").append(EntityUtils.INSTANCE.getColoredHP(player));
            this.players.add(sb.toString());
            switch (((Players)this.module).align.getValue()) {
                case LEFT: {
                    final int w = Wrapper.getFontRenderer().getStringWidth(sb.toString());
                    if (w > this.width) {
                        this.width = w;
                        continue;
                    }
                    continue;
                }
                case RIGHT: {
                    final int w = -Wrapper.getFontRenderer().getStringWidth(sb.toString());
                    if (w < this.width) {
                        this.width = w;
                        continue;
                    }
                    continue;
                }
            }
        }
        if (this.width == 0) {
            switch (((Players)this.module).align.getValue()) {
                case LEFT: {
                    this.width = 10;
                    break;
                }
                case RIGHT: {
                    this.width = -10;
                    break;
                }
            }
        }
    }
    
    private boolean hasStrength(final EntityPlayer player) {
        return player.isPotionActive(MobEffects.STRENGTH);
    }
    
    private void drawString(final String text, final int x, final int y) {
        switch (((Players)this.module).align.getValue()) {
            case LEFT: {
                Wrapper.getFontRenderer().drawStringWithShadow(text, (float)x, (float)y, -1);
                break;
            }
            case RIGHT: {
                Wrapper.getFontRenderer().drawStringWithShadow(text, (float)(x - Wrapper.getFontRenderer().getStringWidth(text)), (float)y, -1);
                break;
            }
        }
    }
}
