// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import joptsimple.internal.Strings;
import club.novola.zori.util.Wrapper;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.module.combat.AutoCrystal;
import club.novola.zori.Zori;
import club.novola.zori.util.DiscordRpc;
import club.novola.zori.module.Module;

public class DiscordRpcModule extends Module
{
    private int ticksSinceLastInput;
    
    public DiscordRpcModule() {
        super("DiscordRPC", Category.MISC);
        this.ticksSinceLastInput = 0;
    }
    
    @Override
    public void onUpdate() {
        final DiscordRpc rpc = DiscordRpc.INSTANCE;
        ++this.ticksSinceLastInput;
        if (this.ticksSinceLastInput / 20 > 120) {
            rpc.state = "AFK";
            return;
        }
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        final AutoCrystal autoCrystal = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
        if (autoCrystal.target != null) {
            rpc.state = "Fighting " + autoCrystal.target.getName();
            return;
        }
        if (Wrapper.mc.isIntegratedServerRunning()) {
            rpc.state = "Playing Singleplayer";
            return;
        }
        if (Wrapper.mc.getCurrentServerData() != null && !Strings.isNullOrEmpty(Wrapper.mc.getCurrentServerData().serverIP)) {
            rpc.state = "Playing " + Wrapper.mc.getCurrentServerData().serverIP;
            return;
        }
        rpc.state = "Main Menu";
    }
    
    @SubscribeEvent
    public void onInput(final InputEvent event) {
        this.ticksSinceLastInput = 0;
    }
    
    @Override
    protected void onEnable() {
        DiscordRpc.INSTANCE.start();
    }
    
    @Override
    protected void onDisable() {
        DiscordRpc.INSTANCE.stop();
    }
}
