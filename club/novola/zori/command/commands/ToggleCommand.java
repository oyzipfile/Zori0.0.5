// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.command.commands;

import club.novola.zori.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.Zori;
import joptsimple.internal.Strings;
import club.novola.zori.command.Command;

public class ToggleCommand extends Command
{
    public ToggleCommand() {
        super(new String[] { "toggle", "t" }, "toggle <Module>");
    }
    
    @Override
    public void exec(final String args) throws Exception {
        if (Strings.isNullOrEmpty(args)) {
            Command.sendErrorMessage("Module expected", false);
            return;
        }
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        final Module m = ModuleManager.getModuleByName(args);
        if (m == null) {
            Command.sendErrorMessage("Unknown module: " + args, false);
            return;
        }
        Command.sendClientMessage(m.getName() + (m.toggle() ? (ChatFormatting.GREEN + " enabled") : (ChatFormatting.RED + " disabled")), false);
    }
}
