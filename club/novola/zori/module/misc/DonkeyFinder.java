// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import java.util.Iterator;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import club.novola.zori.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class DonkeyFinder extends Module
{
    private int timer;
    
    public DonkeyFinder() {
        super("DonkeyFinder", Category.MISC);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.mc.player != null && Wrapper.mc.world != null) {
            ++this.timer;
            for (final Entity e : Wrapper.mc.world.loadedEntityList) {
                if (e instanceof EntityDonkey && this.timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Donkey! X:" + (int)e.posX + " Z:" + (int)e.posZ, true);
                    this.timer = -150;
                }
                if (e instanceof EntityMule && this.timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Mule! X:" + (int)e.posX + " Z:" + (int)e.posZ, true);
                    this.timer = -150;
                }
                if (e instanceof EntityLlama && this.timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Llama! X:" + (int)e.posX + " Z:" + (int)e.posZ, true);
                    this.timer = -150;
                }
            }
        }
    }
}
