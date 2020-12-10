// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import club.novola.zori.Zori;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordRPC;

public class DiscordRpc
{
    private Thread thread;
    public String state;
    public static DiscordRpc INSTANCE;
    private DiscordRPC lib;
    private DiscordRichPresence presence;
    private DiscordEventHandlers handlers;
    
    public DiscordRpc() {
        this.thread = null;
        this.state = "";
        DiscordRpc.INSTANCE = this;
        this.lib = DiscordRPC.INSTANCE;
        this.presence = new DiscordRichPresence();
        this.handlers = new DiscordEventHandlers();
    }
    
    private void init() {
        this.lib.Discord_Initialize("770454455627022366", this.handlers, true, "");
        this.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        this.presence.details = "";
        this.presence.state = this.state;
        this.presence.largeImageKey = "zori";
        this.presence.largeImageText = "Zori v0.0.2-beta";
        this.lib.Discord_UpdatePresence(this.presence);
    }
    
    public void start() {
        this.init();
        (this.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.lib.Discord_RunCallbacks();
                if (!this.presence.state.equals(this.state)) {
                    this.presence.state = this.state;
                    this.lib.Discord_UpdatePresence(this.presence);
                }
                try {
                    Thread.sleep(500L);
                    continue;
                }
                catch (InterruptedException e) {}
                break;
            }
            return;
        }, "RPC-Callback-Handler")).start();
        Zori.getInstance().log.info("Started Discord RPC");
    }
    
    public void stop() {
        if (this.thread != null && this.thread.isAlive() && !this.thread.isInterrupted()) {
            this.thread.interrupt();
            this.lib.Discord_Shutdown();
            this.thread = null;
            Zori.getInstance().log.info("Stopped Discord RPC");
        }
    }
}
