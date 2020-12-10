// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui.button.sub;

import java.text.DecimalFormat;
import java.io.IOException;
import club.novola.zori.Zori;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.gui.button.SubButton;

public class SliderButton<T> extends SubButton
{
    private Setting<T> setting;
    protected boolean dragging;
    protected int sliderWidth;
    
    SliderButton(final int x, final int y, final ModuleButton parent, final Setting<T> setting) {
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.dragging = false;
        this.sliderWidth = 0;
        this.setting = setting;
    }
    
    protected void updateSlider(final int mouseX) {
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.updateSlider(mouseX);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 2, this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(this.getX() + 2 + this.sliderWidth, this.getY(), this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight() - 1, this.getColor(mouseX, mouseY));
        Gui.drawRect(this.getX() + 2, this.getY(), this.getX() + 2 + this.sliderWidth, this.getY() + this.getHeight() - 1, this.getSliderColor(mouseX, mouseY));
        Gui.drawRect(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + 2 + this.getWidth(), this.getY() + this.getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 4), (float)(this.getY() + 2), -1);
        final String val = "" + ChatFormatting.GRAY + this.setting.getValue();
        Wrapper.getFontRenderer().drawStringWithShadow(val, (float)(this.getX() + this.getWidth() - Wrapper.getFontRenderer().getStringWidth(val)), (float)(this.getY() + 2), -1);
    }
    
    private int getColor(final int mouseX, final int mouseY) {
        final Color color = new Color(50, 50, 50, 200);
        final boolean hovered = mouseX > this.getX() + 2 + this.sliderWidth && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }
    
    private int getSliderColor(final int mouseX, final int mouseY) {
        final Color color = Zori.getInstance().clientSettings.getColorr(200);
        final boolean hovered = mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.sliderWidth && mouseY < this.getY() + this.getHeight() - 1;
        return hovered ? color.darker().darker().getRGB() : color.getRGB();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        if (mouseX > this.getX() + 2 && mouseY > this.getY() && mouseX < this.getX() + 2 + this.getWidth() && mouseY < this.getY() + this.getHeight() - 1 && button == 0) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.dragging) {
            this.dragging = false;
        }
    }
    
    public static class IntSlider extends SliderButton
    {
        private Setting<Integer> settingI;
        
        public IntSlider(final Setting<Integer> setting, final int x, final int y, final ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingI = setting;
        }
        
        @Override
        protected void updateSlider(final int mouseX) {
            final double diff = Math.min(this.getWidth(), Math.max(0, mouseX - this.getX()));
            final double min = this.settingI.getMin();
            final double max = this.settingI.getMax();
            this.sliderWidth = (int)(this.getWidth() * (this.settingI.getValue() - min) / (max - min));
            if (this.dragging) {
                if (diff == 0.0) {
                    this.settingI.setValue(this.settingI.getMin());
                }
                else {
                    final DecimalFormat format = new DecimalFormat("##");
                    final String newValue = format.format(diff / this.getWidth() * (max - min) + min);
                    this.settingI.setValue(Integer.parseInt(newValue));
                }
            }
        }
    }
    
    public static class FloatSlider extends SliderButton
    {
        private Setting<Float> settingF;
        
        public FloatSlider(final Setting<Float> setting, final int x, final int y, final ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingF = setting;
        }
        
        @Override
        protected void updateSlider(final int mouseX) {
            final float diff = (float)Math.min(this.getWidth(), Math.max(0, mouseX - this.getX()));
            final float min = this.settingF.getMin();
            final float max = this.settingF.getMax();
            this.sliderWidth = (int)(this.getWidth() * (this.settingF.getValue() - min) / (max - min));
            if (this.dragging) {
                if (diff == 0.0f) {
                    this.settingF.setValue(this.settingF.getMin());
                }
                else {
                    final DecimalFormat format = new DecimalFormat("##.0");
                    final String newValue = format.format(diff / this.getWidth() * (max - min) + min);
                    this.settingF.setValue(Float.parseFloat(newValue));
                }
            }
        }
    }
    
    public static class DoubleSlider extends SliderButton
    {
        private Setting<Double> settingD;
        
        public DoubleSlider(final Setting<Double> setting, final int x, final int y, final ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingD = setting;
        }
        
        @Override
        protected void updateSlider(final int mouseX) {
            final float diff = (float)Math.min(this.getWidth(), Math.max(0, mouseX - this.getX()));
            final double min = this.settingD.getMin();
            final double max = this.settingD.getMax();
            this.sliderWidth = (int)(this.getWidth() * (this.settingD.getValue() - min) / (max - min));
            if (this.dragging) {
                if (diff == 0.0f) {
                    this.settingD.setValue(this.settingD.getMin());
                }
                else {
                    final DecimalFormat format = new DecimalFormat("##.0");
                    final String newValue = format.format(diff / this.getWidth() * (max - min) + min);
                    this.settingD.setValue(Double.parseDouble(newValue));
                }
            }
        }
    }
}
