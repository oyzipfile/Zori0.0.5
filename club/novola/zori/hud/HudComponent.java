// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class HudComponent<T extends Module>
{
    private final String name;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected final T module;
    private boolean dragging;
    private int dragX;
    private int dragY;
    
    public HudComponent(final String name, final int x, final int y, final T module) {
        this.width = 10;
        this.height = Wrapper.getFontRenderer().FONT_HEIGHT;
        this.dragging = false;
        this.dragX = 0;
        this.dragY = 0;
        this.name = name;
        this.x = x;
        this.y = y;
        this.module = module;
    }
    
    public void renderInGui(final int mouseX, final int mouseY) {
        if (this.isInvisible()) {
            return;
        }
        if (this.dragging) {
            this.x = this.dragX + mouseX;
            this.y = this.dragY + mouseY;
        }
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -1439485133);
        this.render();
    }
    
    public void render() {
        if (Wrapper.getPlayer() != null) {
            final int screenWidth = new ScaledResolution(Wrapper.mc).getScaledWidth();
            final int screenHeight = new ScaledResolution(Wrapper.mc).getScaledHeight();
            if (this.width < 0) {
                if (this.x > screenWidth) {
                    this.x = screenWidth;
                }
                if (this.x + this.width < 0) {
                    this.x = -this.width;
                }
            }
            else {
                if (this.x < 0) {
                    this.x = 0;
                }
                if (this.x + this.width > screenWidth) {
                    this.x = screenWidth - this.width;
                }
            }
            if (this.y < 0) {
                this.y = 0;
            }
            if (this.y + this.height > screenHeight) {
                this.y = screenHeight - this.height;
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isInvisible()) {
            return;
        }
        if (this.width < 0) {
            if (button == 0 && mouseX < this.x && mouseX > this.x + this.width && mouseY > this.y && mouseY < this.y + this.height) {
                this.dragX = this.x - mouseX;
                this.dragY = this.y - mouseY;
                this.dragging = true;
            }
        }
        else if (button == 0 && mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height) {
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
            this.dragging = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.isInvisible()) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }
    
    public void onGuiClosed() {
        this.dragging = false;
    }
    
    public boolean isInvisible() {
        return !this.module.isEnabled();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
}
