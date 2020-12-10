// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui;

import club.novola.zori.module.hud.ClickGuiModule;
import java.io.IOException;
import java.util.Iterator;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    private List<Panel> panels;
    
    public ClickGUI() {
        this.panels = new ArrayList<Panel>();
        int x = 10;
        for (final Module.Category c : Module.Category.values()) {
            final Panel panel = new Panel(c, x, 10);
            this.panels.add(panel);
            x += panel.getWidth() + 10;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final Panel p : this.panels) {
            p.draw(mouseX, mouseY);
        }
        for (final HudComponent c : Zori.getInstance().hudComponentManager.getComponents()) {
            c.renderInGui(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        for (final Panel p : this.panels) {
            p.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final Panel p : this.panels) {
            p.mouseClicked(mouseX, mouseY, mouseButton);
        }
        for (final HudComponent c : Zori.getInstance().hudComponentManager.getComponents()) {
            c.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Panel p : this.panels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
        for (final HudComponent c : Zori.getInstance().hudComponentManager.getComponents()) {
            c.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    public void onGuiClosed() {
        for (final HudComponent c : Zori.getInstance().hudComponentManager.getComponents()) {
            c.onGuiClosed();
        }
        super.onGuiClosed();
        ClickGuiModule.enabledGui = false;
    }
    
    public void drawDefaultBackground() {
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
}
