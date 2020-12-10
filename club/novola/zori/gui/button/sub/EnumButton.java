// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button.sub;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.gui.button.SubButton;

public class EnumButton extends SubButton
{
    private Setting<Enum> setting;
    
    public EnumButton(final Setting<Enum> setting, final int x, final int y, final ModuleButton parent) {
        super(x, y, parent.getWidth(), Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.setting = setting;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 2, this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(this.getX() + 2, this.getY(), this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight() - 1, this.getColor(mouseX, mouseY));
        Gui.drawRect(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 4), (float)(this.getY() + 2), -1);
        final String val = "" + ChatFormatting.GRAY + this.setting.getValue().name();
        int valX = this.getX() + this.getWidth() - Wrapper.getFontRenderer().getStringWidth(val) - 1;
        if (this.getX() + Wrapper.getFontRenderer().getStringWidth(this.setting.getName()) + 4 > valX) {
            valX = this.getX() + Wrapper.getFontRenderer().getStringWidth(this.setting.getName()) + 5;
        }
        Wrapper.getFontRenderer().drawStringWithShadow(val, (float)valX, (float)(this.getY() + 2), -1);
    }
    
    private int getColor(final int mouseX, final int mouseY) {
        final Color color = new Color(50, 50, 50, 200);
        final boolean hovered = mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1 && button == 0) {
            final List<Enum> list = (List<Enum>)Arrays.asList(this.getEnum().getEnumConstants());
            final int index = list.indexOf(this.setting.getValue());
            if (index + 1 < list.size()) {
                this.setting.setValue(list.get(index + 1));
            }
            else {
                this.setting.setValue(list.get(0));
            }
        }
    }
    
    private Class<Enum> getEnum() {
        return (Class<Enum>)this.setting.getValue().getDeclaringClass();
    }
}
