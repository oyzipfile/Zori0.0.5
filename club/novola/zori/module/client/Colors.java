// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.client;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Colors extends Module
{
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Boolean> rainbow;
    public Setting<Integer> rainbowSpeed;
    
    public Colors() {
        super("Colors", Category.CLIENT);
        this.red = (Setting<Integer>)this.register("Red", 255, 0, 255);
        this.green = (Setting<Integer>)this.register("Green", 255, 0, 255);
        this.blue = (Setting<Integer>)this.register("Blue", 255, 0, 255);
        this.rainbow = (Setting<Boolean>)this.register("Rainbow", true);
        this.rainbowSpeed = (Setting<Integer>)this.register("RainbowSpeed", 2, 1, 10);
    }
}
