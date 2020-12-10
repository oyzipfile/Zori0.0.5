// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button.sub;

import java.io.IOException;
import club.novola.zori.Zori;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.gui.button.SubButton;

public class BooleanButton extends SubButton
{
    private Setting<Boolean> setting;
    
    public BooleanButton(final Setting<Boolean> setting, final int x, final int y, final ModuleButton parent) {
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.setting = setting;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 2, this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(this.getX() + 2, this.getY(), this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight() - 1, this.getColor(mouseX, mouseY));
        Gui.drawRect(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 4), (float)(this.getY() + 2), -1);
    }
    
    private int getColor(final int mouseX, final int mouseY) {
        final Color color = this.setting.getValue() ? Zori.getInstance().clientSettings.getColorr(200) : new Color(50, 50, 50, 200);
        final boolean hovered = mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1;
        return hovered ? (this.setting.getValue() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1 && button == 0) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}
