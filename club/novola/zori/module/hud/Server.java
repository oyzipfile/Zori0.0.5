// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class Server extends Module
{
    public static Server INSTANCE;
    
    public Server() {
        super("Server", Category.HUD);
        Server.INSTANCE = this;
    }
}
