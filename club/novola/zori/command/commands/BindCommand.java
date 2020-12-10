// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.command.commands;

import club.novola.zori.command.Command;

public class BindCommand extends Command
{
    public BindCommand() {
        super(new String[] { "bind", "b" }, "bind <Module> <Key>");
    }
    
    @Override
    public void exec(final String args) throws Exception {
    }
}
