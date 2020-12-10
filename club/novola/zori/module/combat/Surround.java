// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import club.novola.zori.Zori;
import net.minecraft.util.math.Vec3i;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import club.novola.zori.setting.Setting;
import net.minecraft.block.Block;
import java.util.List;
import club.novola.zori.module.Module;

public class Surround extends Module
{
    private List<Block> whiteList;
    private Setting<Boolean> sneak;
    private Setting<Boolean> rotate;
    private Setting<Integer> bpt;
    
    public Surround() {
        super("Surround", Category.COMBAT);
        this.whiteList = Arrays.asList(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
        this.sneak = (Setting<Boolean>)this.register("SneakOnly", true);
        this.rotate = (Setting<Boolean>)this.register("Rotate", true);
        this.bpt = (Setting<Integer>)this.register("BlocksPerTick", 4, 1, 4);
    }
    
    @Override
    public String getHudInfo() {
        return this.sneak.getValue() ? "Sneak" : "";
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (this.sneak.getValue() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
            return;
        }
        final Vec3d vec3d = getInterpolatedPos((Entity)Wrapper.mc.player, 0.0f);
        BlockPos northBlockPos = new BlockPos(vec3d).north();
        BlockPos southBlockPos = new BlockPos(vec3d).south();
        BlockPos eastBlockPos = new BlockPos(vec3d).east();
        BlockPos westBlockPos = new BlockPos(vec3d).west();
        int blocksPlaced = 0;
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (this.whiteList.contains(block)) {
                        newSlot = i;
                        break;
                    }
                }
            }
        }
        if (newSlot == -1) {
            return;
        }
        final int oldSlot = Wrapper.mc.player.inventory.currentItem;
        Wrapper.mc.player.inventory.currentItem = newSlot;
        Label_0295: {
            if (!hasNeighbour(northBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = northBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        northBlockPos = neighbour;
                        break Label_0295;
                    }
                }
                return;
            }
        }
        Label_0358: {
            if (!hasNeighbour(southBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = southBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        southBlockPos = neighbour;
                        break Label_0358;
                    }
                }
                return;
            }
        }
        Label_0424: {
            if (!hasNeighbour(eastBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = eastBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        eastBlockPos = neighbour;
                        break Label_0424;
                    }
                }
                return;
            }
        }
        Label_0490: {
            if (!hasNeighbour(westBlockPos)) {
                for (final EnumFacing side : EnumFacing.values()) {
                    final BlockPos neighbour = westBlockPos.offset(side);
                    if (hasNeighbour(neighbour)) {
                        westBlockPos = neighbour;
                        break Label_0490;
                    }
                }
                return;
            }
        }
        if (Wrapper.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(northBlockPos)) {
                placeBlockScaffold(northBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(northBlockPos.north()) && Wrapper.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
                placeBlockScaffold(northBlockPos.north(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            Wrapper.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (Wrapper.mc.world.getBlockState(southBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(southBlockPos)) {
                placeBlockScaffold(southBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(southBlockPos.south()) && Wrapper.mc.world.getBlockState(southBlockPos.south()).getMaterial().isReplaceable()) {
                placeBlockScaffold(southBlockPos.south(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            Wrapper.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (Wrapper.mc.world.getBlockState(eastBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(eastBlockPos)) {
                placeBlockScaffold(eastBlockPos, this.rotate.getValue());
                ++blocksPlaced;
            }
            else if (this.isEntitiesEmpty(eastBlockPos.east()) && Wrapper.mc.world.getBlockState(eastBlockPos.east()).getMaterial().isReplaceable()) {
                placeBlockScaffold(eastBlockPos.east(), this.rotate.getValue());
                ++blocksPlaced;
            }
        }
        if (blocksPlaced >= this.bpt.getValue()) {
            Wrapper.mc.player.inventory.currentItem = oldSlot;
            return;
        }
        if (Wrapper.mc.world.getBlockState(westBlockPos).getMaterial().isReplaceable()) {
            if (this.isEntitiesEmpty(westBlockPos)) {
                placeBlockScaffold(westBlockPos, this.rotate.getValue());
            }
            else if (this.isEntitiesEmpty(westBlockPos.west()) && Wrapper.mc.world.getBlockState(westBlockPos.west()).getMaterial().isReplaceable()) {
                placeBlockScaffold(westBlockPos.west(), this.rotate.getValue());
            }
        }
        Wrapper.mc.player.inventory.currentItem = oldSlot;
    }
    
    public static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!Wrapper.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isEntitiesEmpty(final BlockPos pos) {
        final List<Entity> entities = (List<Entity>)Wrapper.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    Zori.getInstance().rotationManager.rotate(hitVec.x, hitVec.y, hitVec.z);
                }
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                if (rotate) {
                    Zori.getInstance().rotationManager.reset();
                }
                return true;
            }
        }
        return false;
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Wrapper.mc.playerController;
    }
    
    private static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return Wrapper.mc.world.getBlockState(pos);
    }
    
    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    private static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Wrapper.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Wrapper.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - Wrapper.mc.player.rotationYaw), Wrapper.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - Wrapper.mc.player.rotationPitch) };
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    private static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    private static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
}
