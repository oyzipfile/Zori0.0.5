// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button;

import java.io.IOException;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import club.novola.zori.gui.button.sub.BindButton;
import club.novola.zori.gui.button.sub.EnumButton;
import club.novola.zori.gui.button.sub.SliderButton;
import club.novola.zori.gui.button.sub.BooleanButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.Zori;
import java.util.ArrayList;
import club.novola.zori.util.Wrapper;
import java.util.List;
import club.novola.zori.module.Module;
import club.novola.zori.gui.Panel;
import club.novola.zori.gui.IGuiComponent;

public class ModuleButton implements IGuiComponent
{
    private Panel panel;
    private Module module;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean extended;
    private List<SubButton> buttons;
    
    public ModuleButton(final Module module, final int x, final int y, final Panel panel) {
        this.panel = panel;
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = panel.getWidth();
        this.height = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        this.extended = false;
        this.buttons = new ArrayList<SubButton>();
        int subButtonY = 0;
        for (final Setting s : Zori.getInstance().settingManager.getSettingsForMod(module)) {
            SubButton button = null;
            if (s.getValue() instanceof Boolean) {
                button = new BooleanButton(s, x, y + this.height + subButtonY, this);
            }
            else if (s.getValue() instanceof Float) {
                button = new SliderButton.FloatSlider(s, x, y + this.height + subButtonY, this);
            }
            else if (s.getValue() instanceof Double) {
                button = new SliderButton.DoubleSlider(s, x, y + this.height + subButtonY, this);
            }
            else if (s.getValue() instanceof Integer) {
                button = new SliderButton.IntSlider(s, x, y + this.height + subButtonY, this);
            }
            else if (s.getValue() instanceof Enum) {
                button = new EnumButton(s, x, y + this.height + subButtonY, this);
            }
            if (button != null) {
                this.buttons.add(button);
                subButtonY += button.getHeight();
            }
        }
        final BindButton bindButton = new BindButton(x, y + this.height + subButtonY, this);
        this.buttons.add(bindButton);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + Wrapper.getFontRenderer().FONT_HEIGHT + 3, this.getColor(mouseX, mouseY));
        Gui.drawRect(this.x, this.y + Wrapper.getFontRenderer().FONT_HEIGHT + 3, this.x + this.width, this.y + Wrapper.getFontRenderer().FONT_HEIGHT + 4, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(this.module.getName(), (float)(this.x + 2), (float)(this.y + 2), -1);
        this.height = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        if (this.extended) {
            for (final SubButton b : this.buttons) {
                if (b.getX() != this.x) {
                    b.setX(this.x);
                }
                if (b.getY() != this.y + this.height) {
                    b.setY(this.y + this.height);
                }
                b.draw(mouseX, mouseY);
                this.height += b.getHeight();
            }
        }
    }
    
    private int getColor(final int mouseX, final int mouseY) {
        final Color color = this.module.isEnabled() ? Zori.getInstance().clientSettings.getColorr(200) : new Color(50, 50, 50, 200);
        final boolean hovered = mouseX > this.x && mouseY > this.y && mouseX < this.x + this.width && mouseY < this.y + Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        return hovered ? (this.module.isEnabled() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + Wrapper.getFontRenderer().FONT_HEIGHT + 4) {
            if (button == 0) {
                this.module.toggle();
            }
            else if (button == 1) {
                this.extended = !this.extended;
            }
            return;
        }
        if (this.extended) {
            for (final SubButton b : this.buttons) {
                b.mouseClicked(mouseX, mouseY, button);
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.extended) {
            for (final SubButton b : this.buttons) {
                b.mouseReleased(mouseX, mouseY, state);
            }
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (this.extended) {
            for (final SubButton b : this.buttons) {
                b.keyTyped(typedChar, keyCode);
            }
        }
    }
    
    public Panel getPanel() {
        return this.panel;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean isExtended() {
        return this.extended;
    }
}
