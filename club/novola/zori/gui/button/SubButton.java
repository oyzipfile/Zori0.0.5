// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button;

import java.io.IOException;
import club.novola.zori.gui.IGuiComponent;

public class SubButton implements IGuiComponent
{
    private int x;
    private int y;
    private int width;
    private int height;
    private ModuleButton parent;
    
    public SubButton(final int x, final int y, final int width, final int height, final ModuleButton parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
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
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public ModuleButton getParent() {
        return this.parent;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
}
