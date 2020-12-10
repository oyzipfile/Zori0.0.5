// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import club.novola.zori.module.Module;
import club.novola.zori.managers.ModuleManager;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import club.novola.zori.hud.HudComponent;
import java.io.IOException;
import java.awt.Color;
import club.novola.zori.setting.Setting;
import org.lwjgl.input.Keyboard;
import club.novola.zori.Zori;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import net.minecraft.client.Minecraft;
import java.io.File;

public class Config
{
    private File file;
    
    public Config() {
        this.file = new File(Minecraft.getMinecraft().gameDir.getAbsolutePath(), "ZoriConfig.txt");
    }
    
    public void save() throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
        final Writer writer2;
        final Iterator<Setting> iterator;
        Setting setting;
        Zori.getInstance().moduleManager.getModules().forEach((name, module) -> {
            try {
                writer2.write("MODULE = " + name + "\r\n");
                writer2.write("ENABLED = " + module.isEnabled() + "\r\n");
                writer2.write("BIND = " + Keyboard.getKeyName(module.getBind()) + "\r\n");
                Zori.getInstance().settingManager.getSettingsForMod(module).iterator();
                while (iterator.hasNext()) {
                    setting = iterator.next();
                    writer2.write("SETTING = " + setting.getName() + "\r\n");
                    writer2.write("SETTING VALUE = " + (Object)((setting.getValue() instanceof Color) ? setting.getValue().getRGB() : setting.getValue()) + "\r\n");
                }
            }
            catch (IOException ex) {}
            if (module.isEnabled()) {
                module.disable();
            }
            return;
        });
        for (final HudComponent component : Zori.getInstance().hudComponentManager.getComponents()) {
            writer.write("HUD COMPONENT = " + component.getName() + "\r\n");
            writer.write("COMPONENT X = " + component.getX() + "\r\n");
            writer.write("COMPONENT Y = " + component.getY() + "\r\n");
        }
        for (final String friend : Zori.getInstance().playerStatus.getFriends()) {
            writer.write("FRIEND = " + friend + "\r\n");
        }
        for (final String enemy : Zori.getInstance().playerStatus.getEnemies()) {
            writer.write("ENEMY = " + enemy + "\r\n");
        }
        writer.close();
    }
    
    public void load() throws IOException {
        final FileInputStream fstream = new FileInputStream(this.file.getAbsolutePath());
        final DataInputStream in = new DataInputStream(fstream);
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Module parsingModule = null;
        Setting parsingSetting = null;
        HudComponent parsingComponent = null;
        String line;
        while ((line = br.readLine()) != null) {
            final String[] split = line.split(" = ");
            final String s = split[0];
            switch (s) {
                case "MODULE": {
                    final ModuleManager moduleManager = Zori.getInstance().moduleManager;
                    parsingModule = ModuleManager.getModuleByName(split[1]);
                    continue;
                }
                case "ENABLED": {
                    if (parsingModule != null && Boolean.parseBoolean(split[1])) {
                        parsingModule.enable();
                        continue;
                    }
                    continue;
                }
                case "BIND": {
                    if (parsingModule != null) {
                        parsingModule.setBind(Keyboard.getKeyIndex(split[1]));
                        continue;
                    }
                    continue;
                }
                case "SETTING": {
                    if (parsingModule != null) {
                        parsingSetting = Zori.getInstance().settingManager.getSetting(split[1], parsingModule);
                        continue;
                    }
                    continue;
                }
                case "SETTING VALUE": {
                    if (parsingModule != null && parsingSetting != null) {
                        final Object value = this.parseSettingValue(parsingSetting, split[1]);
                        if (value == null) {
                            continue;
                        }
                        parsingSetting.setValue(value);
                        continue;
                    }
                    continue;
                }
                case "HUD COMPONENT": {
                    parsingComponent = Zori.getInstance().hudComponentManager.getComponentByName(split[1]);
                    continue;
                }
                case "COMPONENT X": {
                    if (parsingComponent != null) {
                        parsingComponent.setX(Integer.parseInt(split[1]));
                        continue;
                    }
                    continue;
                }
                case "COMPONENT Y": {
                    if (parsingComponent != null) {
                        parsingComponent.setY(Integer.parseInt(split[1]));
                        continue;
                    }
                    continue;
                }
                case "FRIEND": {
                    if (Zori.getInstance().playerStatus.getStatus(split[1]) != 1) {
                        Zori.getInstance().playerStatus.addFriend(split[1]);
                        continue;
                    }
                    continue;
                }
                case "ENEMY": {
                    if (Zori.getInstance().playerStatus.getStatus(split[1]) != -1) {
                        Zori.getInstance().playerStatus.addEnemy(split[1]);
                        continue;
                    }
                    continue;
                }
            }
        }
    }
    
    private Object parseSettingValue(final Setting setting, final String text) {
        final Object value = setting.getValue();
        if (value instanceof Integer) {
            return Integer.parseInt(text);
        }
        if (value instanceof Double) {
            return Double.parseDouble(text);
        }
        if (value instanceof Float) {
            return Float.parseFloat(text);
        }
        if (value instanceof Boolean) {
            return Boolean.parseBoolean(text);
        }
        if (value instanceof Color) {
            return new Color(Integer.parseInt(text));
        }
        if (value instanceof Enum) {
            try {
                final Class<Enum> e = (Class<Enum>)((Enum)value).getDeclaringClass();
                return Enum.valueOf((Class<Object>)e, text);
            }
            catch (IllegalArgumentException ex) {}
        }
        return null;
    }
}
