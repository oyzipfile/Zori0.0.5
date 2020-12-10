// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori;

import java.io.IOException;
import club.novola.zori.capes.CapeUtil;
import club.novola.zori.util.Config;
import club.novola.zori.util.KillEventHelper;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.DiscordRpc;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import club.novola.zori.managers.RotationManager;
import club.novola.zori.gui.ClickGUI;
import club.novola.zori.managers.HudComponentManager;
import club.novola.zori.util.PlayerStatus;
import club.novola.zori.managers.SettingManager;
import club.novola.zori.managers.ClientSettings;
import club.novola.zori.managers.RainbowManager;
import club.novola.zori.managers.CommandManager;
import club.novola.zori.managers.ModuleManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "zori", name = "Zori", version = "v0.0.5")
public class Zori
{
    public static final String MODID = "zori";
    public static final String MODNAME = "Zori";
    public static final String MODVER = "v0.0.5";
    public Logger log;
    private static Zori instance;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public RainbowManager rainbow;
    public ClientSettings clientSettings;
    public SettingManager settingManager;
    public PlayerStatus playerStatus;
    public HudComponentManager hudComponentManager;
    public ClickGUI clickGUI;
    public RotationManager rotationManager;
    
    public Zori() {
        this.log = LogManager.getLogger("Zori");
        Zori.instance = this;
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        new DiscordRpc();
        this.playerStatus = new PlayerStatus();
        this.rainbow = new RainbowManager();
        this.settingManager = new SettingManager();
        this.moduleManager = new ModuleManager();
        this.clientSettings = new ClientSettings();
        this.commandManager = new CommandManager();
        this.hudComponentManager = new HudComponentManager();
        this.clickGUI = new ClickGUI();
        this.rotationManager = new RotationManager();
        new RenderUtils();
        new EntityUtils();
        new KillEventHelper();
        final Config config = new Config();
        CapeUtil.getUsersCape();
        try {
            config.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread("Zori shutdown hook") {
            @Override
            public void run() {
                try {
                    config.save();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.log.info(this.toString() + " initialized");
    }
    
    public static Zori getInstance() {
        return Zori.instance;
    }
    
    @Override
    public String toString() {
        return "Zori v0.0.5";
    }
}
