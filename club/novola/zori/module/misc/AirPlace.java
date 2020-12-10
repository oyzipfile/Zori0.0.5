// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import net.minecraft.util.math.BlockPos;
import club.novola.zori.command.Command;
import club.novola.zori.util.BlockUtil;
import net.minecraft.util.EnumFacing;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class AirPlace extends Module
{
    public AirPlace() {
        super("AirPlace (WIP)", Category.MISC);
    }
    
    public void onEnable() {
        final BlockPos pos = Wrapper.mc.player.getPosition().add(0, 1, 1);
        try {
            BlockUtil.placeBlock(pos, EnumFacing.UP, false);
        }
        catch (Exception e) {
            Command.sendErrorMessage("Frick it didnt work.", true);
        }
        this.disable();
    }
}
