// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockUtil
{
    public static void placeBlock(final BlockPos pos, final EnumFacing side, final boolean packet) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!Wrapper.mc.player.isSneaking()) {
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (packet) {
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, side, EnumHand.MAIN_HAND, (float)hitVec.x - pos.getX(), (float)hitVec.y - pos.getY(), (float)hitVec.z - pos.getZ()));
        }
        else {
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        }
        Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}
