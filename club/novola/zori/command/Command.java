// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.command;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.util.Wrapper;

public abstract class Command
{
    private final String[] aliases;
    private final String syntax;
    
    public Command(final String[] aliases, final String syntax) {
        this.aliases = aliases;
        this.syntax = syntax;
    }
    
    public abstract void exec(final String p0) throws Exception;
    
    public String[] getAliases() {
        return this.aliases;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
    
    public static void sendClientMessage(final String message, final boolean forcePermanent) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        try {
            final TextComponentString component = new TextComponentString(ChatFormatting.AQUA + "<Zori.club> " + ChatFormatting.RED + message);
            final int i = forcePermanent ? 0 : 12076;
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, i);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendErrorMessage(final String message, final boolean forcePermanent) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        try {
            final TextComponentString component = new TextComponentString(ChatFormatting.DARK_RED + "<Zori.club> " + ChatFormatting.RED + message);
            final int i = forcePermanent ? 0 : 12076;
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, i);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendCustomPrefixMessage(final String message, final boolean forcePermanent) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        try {
            final TextComponentString component = new TextComponentString(ChatFormatting.RED + message);
            final int i = forcePermanent ? 0 : 12076;
            Wrapper.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)component, i);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
