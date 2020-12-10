// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.client;

import club.novola.zori.module.Module;

public class Arraylist extends Module
{
    public static Arraylist INSTANCE;
    
    public Arraylist() {
        super("Arraylist", Category.CLIENT);
        Arraylist.INSTANCE = this;
    }
}
