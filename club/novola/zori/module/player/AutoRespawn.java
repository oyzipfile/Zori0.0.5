// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import club.novola.zori.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.GuiGameOver;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class AutoRespawn extends Module
{
    private Setting<Boolean> deathCoords;
    private Setting<Boolean> respawn;
    private Setting<Boolean> autoCope;
    
    public AutoRespawn() {
        super("AutoRespawn", Category.PLAYER);
        this.deathCoords = (Setting<Boolean>)this.register("DeathCoords", false);
        this.respawn = (Setting<Boolean>)this.register("Respawn", true);
        this.autoCope = (Setting<Boolean>)this.register("AutoCope (WIP)", false);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.currentScreen instanceof GuiGameOver && Wrapper.mc.player != null && Wrapper.mc.world != null) {
            if (this.deathCoords.getValue() && Wrapper.mc.player.getHealth() <= 0.0f) {
                Command.sendClientMessage(ChatFormatting.BLUE + String.format("You died at x %d y %d z %d", (int)Wrapper.mc.player.posX, (int)Wrapper.mc.player.posY, (int)Wrapper.mc.player.posZ), true);
            }
            if (this.respawn.getValue() && Wrapper.mc.player.getHealth() <= 0.0f) {
                Wrapper.mc.displayGuiScreen((GuiScreen)null);
                Wrapper.mc.player.respawnPlayer();
            }
            if (this.autoCope.getValue() && Wrapper.mc.player.getHealth() <= 0.0f) {
                final List<String> copeList = new ArrayList<String>();
                copeList.add("Your just a ping player.");
                copeList.add("My game just froze.");
                copeList.add("My brother was playing.");
                copeList.add("Im using forgehax.");
                copeList.add("Thats photoshop, I didnt actually die.");
                copeList.add("I think im getting DDoSed.");
                copeList.add("My ping is so high today.");
                copeList.add("Im so weed rn.");
                Wrapper.mc.player.sendChatMessage(this.getRandomCope(copeList));
            }
        }
    }
    
    public String getRandomCope(final List<String> list) {
        final Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
