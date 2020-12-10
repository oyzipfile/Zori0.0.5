// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module;

import club.novola.zori.Zori;
import net.minecraftforge.common.MinecraftForge;
import club.novola.zori.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.module.client.Settings;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.setting.Setting;

public abstract class Module
{
    private final String name;
    private final Category category;
    private int bind;
    private boolean enabled;
    private Setting<Boolean> drawn;
    private Setting<BindBehaviour> bindBehaviour;
    
    public Module(final String name, final Category category) {
        this.name = name;
        this.category = category;
        this.bind = 0;
        this.enabled = false;
        this.bindBehaviour = (Setting<BindBehaviour>)this.register("BindMode", BindBehaviour.TOGGLE);
        this.drawn = (Setting<Boolean>)this.register("Drawn", true);
        ModuleManager.register(this, name);
    }
    
    public Module(final String name, final Category category, final boolean enabled, final BindBehaviour bindBehaviourIn) {
        this.name = name;
        this.category = category;
        this.bind = 0;
        this.enabled = enabled;
        this.bindBehaviour = (Setting<BindBehaviour>)this.register("BindMode", bindBehaviourIn);
        ModuleManager.register(this, name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public String getHudInfo() {
        return "";
    }
    
    public int setBind(final int newBind) {
        return this.bind = newBind;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void enable() {
        this.enabled = true;
        if (Settings.togglemsgs) {
            Command.sendClientMessage(this.getName() + ChatFormatting.GREEN + " ON", false);
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        ModuleManager.getEnabledModules().remove(this);
        final ModuleManager moduleManager2 = Zori.getInstance().moduleManager;
        ModuleManager.getEnabledModules().add(this);
        this.onEnable();
    }
    
    public void disable() {
        this.enabled = false;
        if (Settings.togglemsgs) {
            Command.sendClientMessage(this.getName() + ChatFormatting.RED + " OFF", false);
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        ModuleManager.getEnabledModules().remove(this);
        this.onDisable();
    }
    
    public boolean toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else {
            this.enable();
        }
        return this.isEnabled();
    }
    
    public Boolean getDrawnBool() {
        return this.drawn.getValue();
    }
    
    public BindBehaviour getBindBehaviour() {
        return this.bindBehaviour.getValue();
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    public void onUpdate() {
    }
    
    public void preRender2D() {
    }
    
    public void postRender2D() {
    }
    
    public void onRender3D() {
    }
    
    protected Setting register(final String name, final Object value) {
        return Zori.getInstance().settingManager.register(new Setting(name, (T)value, this));
    }
    
    protected Setting register(final String name, final Object value, final Object min, final Object max) {
        return Zori.getInstance().settingManager.register(new Setting(name, (T)value, (T)min, (T)max, this));
    }
    
    public enum Category
    {
        COMBAT, 
        RENDER, 
        PLAYER, 
        MOVEMENT, 
        HUD, 
        MISC, 
        CLIENT;
    }
    
    public enum BindBehaviour
    {
        TOGGLE, 
        HOLD;
    }
}
