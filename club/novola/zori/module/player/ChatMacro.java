// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.player;

import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class ChatMacro extends Module
{
    private Setting<Mode> mode;
    
    public ChatMacro() {
        super("ChatMacro", Category.PLAYER);
        this.mode = (Setting<Mode>)this.register("Mode", Mode.ZORI);
    }
    
    public void onEnable() {
        if (this.mode.getValue().equals(Mode.ZORI)) {
            Wrapper.getPlayer().sendChatMessage("You got fucked on dawg, shoutout Zori! :^)");
        }
        else if (this.mode.getValue().equals(Mode.CHINA)) {
            Wrapper.getPlayer().sendChatMessage("You just 1'd \u901a\u8fc7 Auto32k \u9ad8\u5e73\u6676\u4f53 PVP 2b2t (high ping kill low ping)");
        }
        else if (this.mode.getValue().equals(Mode.CHARD)) {
            Wrapper.getPlayer().sendChatMessage("quickscoped");
        }
    }
    
    public enum Mode
    {
        ZORI, 
        CHINA, 
        CHARD;
    }
}
