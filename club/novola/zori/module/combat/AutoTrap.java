// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import club.novola.zori.util.EntityUtils;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import java.util.Comparator;
import club.novola.zori.Zori;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class AutoTrap extends Module
{
    private Setting<Double> range;
    private Setting<Integer> bpt;
    private Setting<Boolean> rotateS;
    private int blocksPlaced;
    private boolean switchDelay;
    private List<EntityPlayer> list;
    private int oldSlot;
    
    public AutoTrap() {
        super("AutoTrap", Category.COMBAT);
        this.range = (Setting<Double>)this.register("Range", 5.0, 0.0, 7.0);
        this.bpt = (Setting<Integer>)this.register("BlocksPerTick", 4, 0, 13);
        this.rotateS = (Setting<Boolean>)this.register("Rotate", true);
        this.blocksPlaced = 0;
        this.switchDelay = false;
        this.oldSlot = -1;
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.getWorld().playerEntities.isEmpty()) {
            return;
        }
        if (!this.switchDelay) {
            this.list = new ArrayList<EntityPlayer>();
            for (final EntityPlayer player : Wrapper.getWorld().playerEntities) {
                if (player == Wrapper.getPlayer()) {
                    continue;
                }
                if (Wrapper.getPlayer().getDistance((Entity)player) > this.range.getValue()) {
                    continue;
                }
                if (player.getHealth() <= 0.0f) {
                    continue;
                }
                if (player.isDead) {
                    continue;
                }
                if (Zori.getInstance().playerStatus.getStatus(player.getName()) == 1) {
                    continue;
                }
                this.list.add(player);
            }
            if (this.list.isEmpty()) {
                return;
            }
            this.list.sort(Comparator.comparing(p -> Zori.getInstance().playerStatus.isEnemyInRange(this.range.getValue()) ? ((float)Zori.getInstance().playerStatus.getStatus(((EntityPlayer)p).getName())) : Wrapper.getPlayer().getDistance(p)));
            boolean mainHand = this.obbyOrEchest(Wrapper.getPlayer().getHeldItemMainhand());
            if (!mainHand) {
                this.oldSlot = Wrapper.getPlayer().inventory.currentItem;
                for (int i = 0; i < 9; ++i) {
                    if (Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                        final ItemBlock item = (ItemBlock)Wrapper.getPlayer().inventory.getStackInSlot(i).getItem();
                        if (item.getBlock() instanceof BlockObsidian || item.getBlock() instanceof BlockEnderChest) {
                            Wrapper.getPlayer().inventory.currentItem = i;
                            this.switchDelay = true;
                            return;
                        }
                    }
                }
                mainHand = this.obbyOrEchest(Wrapper.getPlayer().getHeldItemMainhand());
            }
            if (!mainHand) {
                return;
            }
        }
        this.switchDelay = false;
        for (final EntityPlayer player : this.list) {
            final EntityUtils instance = EntityUtils.INSTANCE;
            final BlockPos playerPos = new BlockPos(EntityUtils.getInterpolatedPos((Entity)player, 0.0));
            final BlockPos[] offsets = { playerPos.add(1, 0, 0), playerPos.add(-1, 0, 0), playerPos.add(0, 0, 1), playerPos.add(0, 0, -1), playerPos.add(0, 2, 0), playerPos.add(1, 1, 0), playerPos.add(-1, 1, 0), playerPos.add(0, 1, 1), playerPos.add(0, 1, -1), playerPos.add(1, 2, 0), playerPos.add(-1, 2, 0), playerPos.add(0, 2, 1), playerPos.add(0, 2, -1) };
            this.blocksPlaced = 0;
            for (final BlockPos blockPos : offsets) {
                if (this.blocksPlaced > this.bpt.getValue()) {
                    if (Wrapper.getPlayer().inventory.currentItem != this.oldSlot) {
                        Wrapper.getPlayer().inventory.currentItem = this.oldSlot;
                    }
                    return;
                }
                if (this.shouldPlace(blockPos)) {
                    Surround.placeBlockScaffold(blockPos, this.rotateS.getValue());
                    ++this.blocksPlaced;
                }
            }
        }
        if (Wrapper.getPlayer().inventory.currentItem != this.oldSlot && this.oldSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.oldSlot;
        }
        this.oldSlot = -1;
    }
    
    private boolean obbyOrEchest(final ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock) {
            final ItemBlock item = (ItemBlock)stack.getItem();
            return item.getBlock() instanceof BlockObsidian || item.getBlock() instanceof BlockEnderChest;
        }
        return false;
    }
    
    private boolean shouldPlace(final BlockPos pos) {
        final List<Entity> entities = (List<Entity>)Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        final boolean a = entities.isEmpty();
        final boolean b = Wrapper.getWorld().getBlockState(pos).getMaterial().isReplaceable();
        final boolean c = this.blocksPlaced < this.bpt.getValue();
        return a && b && c;
    }
}
