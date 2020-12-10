// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.Zori;
import club.novola.zori.command.Command;

public class StatusCommand extends Command
{
    public StatusCommand() {
        super(new String[] { "status", "s", "st" }, "status <Player> <Friend | Enemy | Neutral>");
    }
    
    @Override
    public void exec(final String args) throws Exception {
        final String[] split = args.split(" ");
        if (split.length <= 0) {
            Command.sendErrorMessage(this.getSyntax(), false);
            return;
        }
        final String name = split[0];
        if (name.equals("") || name.equals(" ")) {
            Command.sendErrorMessage(this.getSyntax(), false);
            return;
        }
        Label_0918: {
            switch (split.length) {
                case 1: {
                    Command.sendClientMessage(name + ": " + this.getStatusString(name), false);
                    return;
                }
                case 2: {
                    final String lowerCase = split[1].toLowerCase();
                    switch (lowerCase) {
                        case "":
                        case " ": {
                            Command.sendErrorMessage(this.getSyntax(), false);
                            return;
                        }
                        case "friend":
                        case "f":
                        case "fr": {
                            switch (Zori.getInstance().playerStatus.getStatus(name)) {
                                case 1: {
                                    Command.sendErrorMessage(name + " is already a friend", false);
                                    return;
                                }
                                case -1: {
                                    Zori.getInstance().playerStatus.delEnemy(name);
                                    Zori.getInstance().playerStatus.addFriend(name);
                                    Command.sendClientMessage(name + " is an enemy, changing to " + ChatFormatting.GREEN + "friend", false);
                                    return;
                                }
                                case 0: {
                                    Zori.getInstance().playerStatus.addFriend(name);
                                    Command.sendClientMessage("Set status of " + name + " to " + ChatFormatting.GREEN + "friend", false);
                                    return;
                                }
                                default: {
                                    break Label_0918;
                                }
                            }
                            break;
                        }
                        case "enemy":
                        case "e":
                        case "en": {
                            switch (Zori.getInstance().playerStatus.getStatus(name)) {
                                case -1: {
                                    Command.sendErrorMessage(name + " is already an enemy", false);
                                    return;
                                }
                                case 1: {
                                    Zori.getInstance().playerStatus.delFriend(name);
                                    Zori.getInstance().playerStatus.addEnemy(name);
                                    Command.sendClientMessage(name + " is a friend, changing to " + ChatFormatting.RED + "enemy", false);
                                    return;
                                }
                                case 0: {
                                    Zori.getInstance().playerStatus.addEnemy(name);
                                    Command.sendClientMessage("Set status of " + name + " to " + ChatFormatting.RED + "enemy", false);
                                    return;
                                }
                                default: {
                                    break Label_0918;
                                }
                            }
                            break;
                        }
                        case "neutral":
                        case "n": {
                            switch (Zori.getInstance().playerStatus.getStatus(name)) {
                                case -1: {
                                    Zori.getInstance().playerStatus.delEnemy(name);
                                    Command.sendClientMessage("Set status of " + name + " to neutral", false);
                                    return;
                                }
                                case 1: {
                                    Zori.getInstance().playerStatus.delFriend(name);
                                    Command.sendClientMessage("Set status of " + name + " to neutral", false);
                                    return;
                                }
                                case 0: {
                                    Command.sendErrorMessage(name + "is already neutral", false);
                                    return;
                                }
                                default: {
                                    break Label_0918;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
        Command.sendErrorMessage(this.getSyntax(), false);
    }
    
    private String getStatusString(final String name) {
        switch (Zori.getInstance().playerStatus.getStatus(name)) {
            case 1: {
                return ChatFormatting.GREEN + "Friend";
            }
            case -1: {
                return ChatFormatting.RED + "Enemy";
            }
            case 0: {
                return "Neutral";
            }
            default: {
                return "";
            }
        }
    }
}
