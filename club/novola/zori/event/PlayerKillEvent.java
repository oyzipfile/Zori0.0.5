// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerKillEvent extends Event
{
    private final EntityPlayer player;
    
    public PlayerKillEvent(final EntityPlayer player) {
        this.player = player;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
}
