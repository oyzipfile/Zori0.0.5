// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.mixin.mixins;

import org.apache.logging.log4j.LogManager;
import net.minecraft.util.text.TextComponentString;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.Zori;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.text.ITextComponent;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.ChatLine;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;

@SideOnly(Side.CLIENT)
@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat extends Gui
{
    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    @Final
    private List<ChatLine> drawnChatLines;
    @Shadow
    private int scrollPos;
    @Shadow
    private boolean isScrolled;
    
    public MixinGuiNewChat() {
        this.drawnChatLines = (List<ChatLine>)Lists.newArrayList();
    }
    
    @Shadow
    private void setChatLine(final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
    }
    
    @Inject(method = { "drawChat" }, at = { @At("HEAD") }, cancellable = true)
    private void drawChat(final int updateCounter, final CallbackInfo ci) {
        if (this.mc.ingameGUI != null) {
            final ModuleManager moduleManager = Zori.getInstance().moduleManager;
            if (ModuleManager.getModuleByName("Chat").isEnabled()) {
                final GuiNewChat chat = this.mc.ingameGUI.getChatGUI();
                ci.cancel();
                if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                    final int i = chat.getLineCount();
                    final int j = this.drawnChatLines.size();
                    final float f = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
                    if (j > 0) {
                        boolean flag = false;
                        if (chat.getChatOpen()) {
                            flag = true;
                        }
                        final float f2 = chat.getChatScale();
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(2.0f, 8.0f, 0.0f);
                        GlStateManager.scale(f2, f2, 1.0f);
                        int l = 0;
                        for (int i2 = 0; i2 + this.scrollPos < this.drawnChatLines.size() && i2 < i; ++i2) {
                            final ChatLine chatline = this.drawnChatLines.get(i2 + this.scrollPos);
                            if (chatline != null) {
                                final int j2 = updateCounter - chatline.getUpdatedCounter();
                                if (j2 < 200 || flag) {
                                    double d0 = j2 / 200.0;
                                    d0 = 1.0 - d0;
                                    d0 *= 10.0;
                                    d0 = MathHelper.clamp(d0, 0.0, 1.0);
                                    d0 *= d0;
                                    int l2 = (int)(255.0 * d0);
                                    if (flag) {
                                        l2 = 255;
                                    }
                                    l2 *= (int)f;
                                    ++l;
                                    if (l2 > 3) {
                                        final int j3 = -i2 * 9;
                                        final String s = chatline.getChatComponent().getFormattedText();
                                        final boolean name = this.mc.player != null && s.toLowerCase().contains(this.mc.player.getName().toLowerCase());
                                        if (name) {
                                            drawRect(this.mc.fontRenderer.getStringWidth("<00:00> ") - 1, j3 - 9, this.mc.fontRenderer.getStringWidth(s) + 1, j3 + 1, Zori.getInstance().clientSettings.getColor(69));
                                        }
                                        GlStateManager.enableBlend();
                                        final int color = Zori.getInstance().clientSettings.getColor() + (l2 << 24);
                                        if (color < Integer.MAX_VALUE) {
                                            this.mc.fontRenderer.drawStringWithShadow(s.split(" ")[0], 0.0f, (float)(j3 - 8), color);
                                        }
                                        this.mc.fontRenderer.drawStringWithShadow(s.substring(6), (float)this.mc.fontRenderer.getStringWidth("<00:00> "), (float)(j3 - 8), 16777215 + (l2 << 24));
                                        GlStateManager.disableAlpha();
                                        GlStateManager.disableBlend();
                                    }
                                }
                            }
                        }
                        if (flag) {
                            final int k2 = this.mc.fontRenderer.FONT_HEIGHT;
                            GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                            final int l3 = j * k2 + j;
                            final int i3 = l * k2 + l;
                            final int j4 = this.scrollPos * i3 / j;
                            final int k3 = i3 * i3 / l3;
                            if (l3 != i3) {
                                final int k4 = (j4 > 0) ? 170 : 96;
                                final int l4 = this.isScrolled ? 13382451 : 3355562;
                                drawRect(0, -j4, 2, -j4 - k3, l4 + (k4 << 24));
                                drawRect(2, -j4, 1, -j4 - k3, 13421772 + (k4 << 24));
                            }
                        }
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    }
    
    @Inject(method = { "printChatMessageWithOptionalDeletion" }, at = { @At("HEAD") }, cancellable = true)
    private void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, final int chatLineId, final CallbackInfo ci) {
        ci.cancel();
        final String time = new SimpleDateFormat("HH:mm ").format(new Date());
        final ITextComponent orig = chatComponent;
        chatComponent = new TextComponentString(time).appendSibling(orig);
        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        MixinGuiNewChat.LOGGER.info("[CHAT] {}", (Object)chatComponent.getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }
    
    static {
        MixinGuiNewChat.LOGGER = LogManager.getLogger();
    }
}
