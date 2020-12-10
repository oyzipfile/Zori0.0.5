// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import javax.annotation.Nullable;
import java.util.Iterator;
import club.novola.zori.module.Module;
import java.util.ArrayList;
import club.novola.zori.setting.Setting;
import java.util.List;

public class SettingManager
{
    private final List<Setting> settings;
    
    public SettingManager() {
        this.settings = new ArrayList<Setting>();
    }
    
    @Nullable
    public Setting getSetting(final String name, final Module module) {
        for (final Setting s : this.settings) {
            if (s.getName().equalsIgnoreCase(name) && s.getParent().equals(module)) {
                return s;
            }
        }
        return null;
    }
    
    public List<Setting> getSettingsForMod(final Module module) {
        final List<Setting> list = new ArrayList<Setting>();
        for (final Setting s : this.settings) {
            if (s.getParent().equals(module)) {
                list.add(s);
            }
        }
        return list;
    }
    
    public Setting register(final Setting setting) {
        this.settings.add(setting);
        return setting;
    }
}
