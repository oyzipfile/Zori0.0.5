// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.movement;

import club.novola.zori.managers.ModuleManager;
import club.novola.zori.module.render.HoleESP;
import club.novola.zori.Zori;
import net.minecraft.util.math.BlockPos;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.Module;

public class HoleStep extends Module
{
    public HoleStep() {
        super("HoleStep", Category.MOVEMENT);
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null) {
            return;
        }
        if (this.isHole(this.getPlayerPos().down()) && Wrapper.getPlayer().motionY <= 0.0 && Wrapper.getPlayer().fallDistance <= 1.0f && !Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
            Wrapper.getPlayer().addVelocity(0.0, -3.0, 0.0);
        }
    }
    
    private boolean isHole(final BlockPos blockPos) {
        final ModuleManager moduleManager = Zori.getInstance().moduleManager;
        final HoleESP holeESP = (HoleESP)ModuleManager.getModuleByName("HoleESP");
        return holeESP.isBedrock(blockPos) || holeESP.isObby(blockPos);
    }
    
    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Wrapper.getPlayer().posX), Math.floor(Wrapper.getPlayer().posY), Math.floor(Wrapper.getPlayer().posZ));
    }
}
