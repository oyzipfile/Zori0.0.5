// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.managers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.util.Wrapper;
import club.novola.zori.Zori;
import net.minecraftforge.client.event.ClientChatEvent;
import java.util.Iterator;
import net.minecraftforge.common.MinecraftForge;
import com.google.common.collect.Lists;
import club.novola.zori.command.commands.StatusCommand;
import club.novola.zori.command.commands.ToggleCommand;
import club.novola.zori.command.Command;
import java.util.List;

public class CommandManager
{
    private final List<Command> commands;
    
    public CommandManager() {
        this.commands = (List<Command>)Lists.newArrayList((Object[])new Command[] { new ToggleCommand(), new StatusCommand() });
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public List<Command> getCommands() {
        return this.commands;
    }
    
    private void callCommand(String text) {
        if (!text.contains(" ")) {
            text += " ";
        }
        final String[] split = text.split(" ");
        for (final Command c : this.commands) {
            for (final String s : c.getAliases()) {
                if (s.equalsIgnoreCase(split[0])) {
                    try {
                        c.exec(text.substring(split[0].length() + 1));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Command.sendErrorMessage("ERR: " + e.toString(), false);
                    }
                    return;
                }
            }
        }
        Command.sendErrorMessage("Unknown command: " + split[0], false);
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatEvent event) {
        final String prefix = Zori.getInstance().clientSettings.getPrefix();
        if (event.getMessage().startsWith(prefix)) {
            event.setCanceled(true);
            Wrapper.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            if (event.getMessage().equalsIgnoreCase(prefix)) {
                this.callCommand("help");
            }
            else {
                this.callCommand(event.getMessage().substring(prefix.length()));
            }
        }
    }
}
