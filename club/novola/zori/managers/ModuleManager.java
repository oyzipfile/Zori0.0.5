// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import javax.annotation.Nullable;
import net.minecraftforge.common.MinecraftForge;
import club.novola.zori.module.render.PlayerESP;
import club.novola.zori.module.render.GUIBlur;
import club.novola.zori.module.render.ShaderLoader;
import club.novola.zori.module.render.PlayerGlow;
import club.novola.zori.module.render.CustomFOV;
import club.novola.zori.module.render.CustomTime;
import club.novola.zori.module.render.SkyColor;
import club.novola.zori.module.render.VoidESP;
import club.novola.zori.module.render.BlockHighlight;
import club.novola.zori.module.render.NoRender;
import club.novola.zori.module.render.HoleESP;
import club.novola.zori.module.render.SwingAnim;
import club.novola.zori.module.render.OffhandSwing;
import club.novola.zori.module.player.AutoRespawn;
import club.novola.zori.module.player.NoVoid;
import club.novola.zori.module.player.NoSlowBypass;
import club.novola.zori.module.player.FastXP;
import club.novola.zori.module.player.ChatMacro;
import club.novola.zori.module.player.ChatSuffix;
import club.novola.zori.module.movement.Jesus;
import club.novola.zori.module.movement.Sprint;
import club.novola.zori.module.misc.ReverseStep;
import club.novola.zori.module.movement.HoleStep;
import club.novola.zori.module.movement.Step;
import club.novola.zori.module.movement.BoatFly;
import club.novola.zori.module.misc.W0M3NMode;
import club.novola.zori.module.misc.DonkeyFinder;
import club.novola.zori.module.misc.GreenText;
import club.novola.zori.module.misc.SoundEffects;
import club.novola.zori.module.misc.FakePlayer;
import club.novola.zori.module.misc.DiscordRpcModule;
import club.novola.zori.module.misc.Chat;
import club.novola.zori.module.hud.Logo;
import club.novola.zori.module.hud.ArmorHUD;
import club.novola.zori.module.hud.Totems;
import club.novola.zori.module.hud.PlayerViewer;
import club.novola.zori.module.hud.Watermark;
import club.novola.zori.module.hud.PvpInfo;
import club.novola.zori.module.hud.Server;
import club.novola.zori.module.hud.OffhandMode;
import club.novola.zori.module.hud.ClickGuiModule;
import club.novola.zori.module.hud.InventoryPreview;
import club.novola.zori.module.hud.Players;
import club.novola.zori.module.hud.TPS;
import club.novola.zori.module.hud.ArmorWarning;
import club.novola.zori.module.hud.Welcomer;
import club.novola.zori.module.hud.FPS;
import club.novola.zori.module.combat.OffhandGap;
import club.novola.zori.module.combat.OffhandCrystal;
import club.novola.zori.module.combat.AutoCrystalTwo;
import club.novola.zori.module.combat.Reach;
import club.novola.zori.module.combat.PearlResolve;
import club.novola.zori.module.combat.Surround;
import club.novola.zori.module.combat.AutoCrystal;
import club.novola.zori.module.combat.noDesync;
import club.novola.zori.module.combat.Aura;
import club.novola.zori.module.combat.AutoTotem;
import club.novola.zori.module.combat.AutoTrap;
import club.novola.zori.module.client.Arraylist;
import club.novola.zori.module.client.Capes;
import club.novola.zori.module.client.Settings;
import club.novola.zori.module.client.Colors;
import java.util.ArrayList;
import java.util.List;
import club.novola.zori.module.Module;
import java.util.HashMap;

public class ModuleManager
{
    private static HashMap<String, Module> modules;
    private static List<Module> enabledModules;
    
    public ModuleManager() {
        ModuleManager.enabledModules = new ArrayList<Module>();
        ModuleManager.modules = new HashMap<String, Module>();
        new Colors();
        new Settings();
        new Capes();
        new Arraylist();
        new AutoTrap();
        new AutoTotem();
        new Aura();
        new noDesync();
        new AutoCrystal();
        new Surround();
        new PearlResolve();
        new Reach();
        new AutoCrystalTwo();
        new OffhandCrystal();
        new OffhandGap();
        new FPS();
        new Welcomer();
        new ArmorWarning();
        new TPS();
        new Players();
        new InventoryPreview();
        new ClickGuiModule();
        new OffhandMode();
        new Server();
        new PvpInfo();
        new Watermark();
        new PlayerViewer();
        new Totems();
        new ArmorHUD();
        new Logo();
        new Chat();
        new DiscordRpcModule();
        new FakePlayer();
        new SoundEffects();
        new GreenText();
        new DonkeyFinder();
        new W0M3NMode();
        new BoatFly();
        new Step();
        new HoleStep();
        new ReverseStep();
        new Sprint();
        new Jesus();
        new ChatSuffix();
        new ChatMacro();
        new FastXP();
        new NoSlowBypass();
        new NoVoid();
        new AutoRespawn();
        new OffhandSwing();
        new SwingAnim();
        new HoleESP();
        new NoRender();
        new BlockHighlight();
        new VoidESP();
        new SkyColor();
        new CustomTime();
        new CustomFOV();
        new PlayerGlow();
        new ShaderLoader();
        new GUIBlur();
        new PlayerESP();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static void register(final Module module, final String name) {
        ModuleManager.modules.put(name.toLowerCase(), module);
    }
    
    @Nullable
    public static Module getModuleByName(final String name) {
        return ModuleManager.modules.getOrDefault(name.toLowerCase(), null);
    }
    
    public ChatFormatting getEnabledColor(final String module) {
        return getModuleByName(module).isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED;
    }
    
    public HashMap<String, Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static List<Module> getEnabledModules() {
        return ModuleManager.enabledModules;
    }
    
    public List<Module> getModulesInCategory(final Module.Category category) {
        final List<Module> list = new ArrayList<Module>();
        final List<Module> list2;
        ModuleManager.modules.forEach((name, module) -> {
            if (module.getCategory().equals(category)) {
                list2.add(module);
            }
            return;
        });
        return list;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        for (final Module m : ModuleManager.enabledModules) {
            m.onUpdate();
        }
    }
    
    @SubscribeEvent
    public void onRenderGameOverLay(final RenderGameOverlayEvent event) {
        if (event instanceof RenderGameOverlayEvent.Pre) {
            for (final Module m : ModuleManager.enabledModules) {
                m.preRender2D();
            }
        }
        else if (event instanceof RenderGameOverlayEvent.Post && event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HOTBAR)) {
            for (final Module m : ModuleManager.enabledModules) {
                m.postRender2D();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        for (final Module m : ModuleManager.enabledModules) {
            m.onRender3D();
        }
    }
    
    @SubscribeEvent
    public void keyPressed(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() != 0) {
            ModuleManager.modules.forEach((name, m) -> {
                if (m.getBind() == Keyboard.getEventKey()) {
                    switch (m.getBindBehaviour()) {
                        case TOGGLE: {
                            if (Keyboard.getEventKeyState()) {
                                m.toggle();
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case HOLD: {
                            m.toggle();
                            break;
                        }
                    }
                }
            });
        }
    }
}
