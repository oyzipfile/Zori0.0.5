// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import java.awt.Color;
import club.novola.zori.Zori;
import club.novola.zori.module.client.Settings;
import club.novola.zori.module.client.Colors;

public class ClientSettings
{
    public Colors colors;
    public Settings settings;
    
    public ClientSettings() {
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        this.colors = (Colors)ModuleManager.getModuleByName("Colors");
        final ModuleManager moduleManager2 = Zori.getInstance().moduleManager;
        this.settings = (Settings)ModuleManager.getModuleByName("Settings");
    }
    
    public Color getColorr(final int alpha) {
        if (this.colors.rainbow.getValue()) {
            return Zori.getInstance().rainbow.getColor(alpha);
        }
        return new Color(this.colors.red.getValue(), this.colors.green.getValue(), this.colors.blue.getValue(), alpha);
    }
    
    public int getColor() {
        if (this.colors.rainbow.getValue()) {
            return Zori.getInstance().rainbow.getHex();
        }
        return new Color(this.colors.red.getValue(), this.colors.green.getValue(), this.colors.blue.getValue()).getRGB();
    }
    
    public int getColor(final int alpha) {
        if (this.colors.rainbow.getValue()) {
            return Zori.getInstance().rainbow.getColor(alpha).getRGB();
        }
        return new Color(this.colors.red.getValue(), this.colors.green.getValue(), this.colors.blue.getValue(), alpha).getRGB();
    }
    
    public String getPrefix() {
        return this.settings.commandPrefix.getValue();
    }
}
