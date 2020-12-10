// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.client;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Settings extends Module
{
    public static Boolean togglemsgs;
    public Setting<Boolean> toggleMsg;
    public Setting<String> commandPrefix;
    
    public Settings() {
        super("Settings", Category.CLIENT);
        this.toggleMsg = (Setting<Boolean>)this.register("ToggleMsgs", false);
        this.commandPrefix = (Setting<String>)this.register("Prefix", ",");
    }
    
    @Override
    public void onUpdate() {
        Settings.togglemsgs = this.toggleMsg.getValue();
    }
    
    static {
        Settings.togglemsgs = false;
    }
}
