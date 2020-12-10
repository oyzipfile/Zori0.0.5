// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class InventoryPreview extends Module
{
    public static InventoryPreview INSTANCE;
    public Setting<Background> background;
    
    public InventoryPreview() {
        super("Inventory", Category.HUD);
        this.background = (Setting<Background>)this.register("Background", Background.TRANS);
        InventoryPreview.INSTANCE = this;
    }
    
    public enum Background
    {
        NONE, 
        CLEAR, 
        NORMAL, 
        TRANS;
    }
}
