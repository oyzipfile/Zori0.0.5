// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button.sub;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.gui.button.SubButton;

public class BindButton extends SubButton
{
    private boolean listening;
    
    public BindButton(final int x, final int y, final ModuleButton parent) {
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.listening = false;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 2, this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(this.getX() + 2, this.getY(), this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight() - 1, this.getColor(mouseX, mouseY));
        Gui.drawRect(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow("Bind", (float)(this.getX() + 4), (float)(this.getY() + 2), -1);
        final String val = "" + ChatFormatting.GRAY + (this.listening ? "Listening" : Keyboard.getKeyName(this.getParent().getModule().getBind()));
        Wrapper.getFontRenderer().drawStringWithShadow(val, (float)(this.getX() + this.getWidth() - Wrapper.getFontRenderer().getStringWidth(val)), (float)(this.getY() + 2), -1);
    }
    
    private int getColor(final int mouseX, final int mouseY) {
        final Color color = new Color(50, 50, 50, 200);
        final boolean hovered = mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1 && button == 0) {
            this.listening = !this.listening;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (this.listening) {
            if (keyCode != 0 && keyCode != 1) {
                if (keyCode == 211) {
                    this.getParent().getModule().setBind(0);
                }
                else {
                    this.getParent().getModule().setBind(keyCode);
                }
            }
            this.listening = false;
        }
    }
}
