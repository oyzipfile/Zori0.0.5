// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui;

import java.io.IOException;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import java.util.ArrayList;
import club.novola.zori.gui.button.ModuleButton;
import java.util.List;
import club.novola.zori.module.Module;

public class Panel implements IGuiComponent
{
    private Module.Category category;
    private int x;
    private int y;
    private int width;
    private int height;
    private int titleHeight;
    private boolean extended;
    private boolean dragging;
    private int dragX;
    private int dragY;
    private List<ModuleButton> modules;
    
    public Panel(final Module.Category category, final int x, final int y) {
        this.extended = true;
        this.dragging = false;
        this.dragX = 0;
        this.dragY = 0;
        this.category = category;
        this.x = x;
        this.y = y;
        this.modules = new ArrayList<ModuleButton>();
        this.titleHeight = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        int buttonY = y + this.titleHeight;
        this.width = 90;
        for (final Module m : Zori.getInstance().moduleManager.getModulesInCategory(category)) {
            final ModuleButton button = new ModuleButton(m, this.x, buttonY, this);
            this.modules.add(button);
            buttonY += button.getHeight();
            this.height += button.getHeight();
        }
        this.height = buttonY;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final int color = Zori.getInstance().clientSettings.getColor();
        if (this.dragging) {
            this.x = this.dragX + mouseX;
            this.y = this.dragY + mouseY;
        }
        Gui.drawRect(this.x - 2, this.y - 2, this.x + this.width + 2, this.y + this.titleHeight - 1, color);
        Gui.drawRect(this.x, this.y + this.titleHeight - 1, this.x + this.width, this.y + this.titleHeight, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(this.category.name(), (float)(this.x + 1), (float)(this.y + 2), -1);
        if (!this.extended) {
            Gui.drawRect(this.x - 2, this.y + this.titleHeight - 1, this.x + this.width + 2, this.y + this.titleHeight + 2, color);
            return;
        }
        this.height = 0;
        for (final ModuleButton b : this.modules) {
            if (b.getX() != this.x) {
                b.setX(this.x);
            }
            if (b.getY() != this.y + this.titleHeight + this.height) {
                b.setY(this.y + this.titleHeight + this.height);
            }
            b.draw(mouseX, mouseY);
            this.height += b.getHeight();
        }
        Gui.drawRect(this.x - 2, this.y + this.titleHeight - 1, this.x, this.y + this.titleHeight + this.height + 2, color);
        Gui.drawRect(this.x + this.width, this.y + this.titleHeight - 1, this.x + this.width + 2, this.y + this.titleHeight + this.height + 2, color);
        Gui.drawRect(this.x, this.y + this.titleHeight + this.height, this.x + this.width, this.y + this.titleHeight + this.height + 2, color);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.titleHeight) {
            if (button == 0) {
                this.dragX = this.x - mouseX;
                this.dragY = this.y - mouseY;
                this.dragging = true;
            }
            else if (button == 1) {
                this.extended = !this.extended;
            }
            return;
        }
        for (final ModuleButton b : this.modules) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.dragging) {
            this.dragging = false;
        }
        for (final ModuleButton b : this.modules) {
            b.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        for (final ModuleButton b : this.modules) {
            b.keyTyped(typedChar, keyCode);
        }
    }
    
    public Module.Category getCategory() {
        return this.category;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
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
    
    public List<ModuleButton> getModules() {
        return this.modules;
    }
}
