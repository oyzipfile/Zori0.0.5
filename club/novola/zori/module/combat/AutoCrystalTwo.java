// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.NonNullList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAppleGold;
import java.util.ArrayList;
import java.awt.Color;
import club.novola.zori.util.RenderUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.event.PacketSendEvent;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import club.novola.zori.util.KillEventHelper;
import net.minecraft.item.ItemEndCrystal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import club.novola.zori.Zori;
import net.minecraft.entity.Entity;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.List;
import net.minecraft.entity.item.EntityEnderCrystal;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class AutoCrystalTwo extends Module
{
    public int Red;
    public int Green;
    public int Blue;
    boolean isActive;
    private Setting<Integer> tickDelay;
    private Setting<Boolean> rotateS;
    private Setting<Double> hitRange;
    private Setting<Double> wallsRange;
    private Setting<Integer> hitAttempts;
    private Setting<HitMode> hitMode;
    private Setting<Double> placeRange;
    private Setting<Boolean> autoSwitch;
    private Setting<Boolean> noGappleSwitch;
    private Setting<Double> minDmg;
    private Setting<Double> facePlaceHp;
    private Setting<Double> maxSelfDmg;
    private Setting<Double> enemyRange;
    private Setting<RenderMode> renderMode;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> linewidth;
    private Setting<Boolean> sync;
    private int tickCounter;
    private int attempts;
    private EntityEnderCrystal lastHitCrystal;
    private List<EntityEnderCrystal> placedCrystals;
    private List<AxisAlignedBB> interacted;
    private BlockPos placePos;
    private BlockPos renderPos;
    private boolean switchCooldown;
    public EntityPlayer target;
    private EntityPlayer possibleTarget;
    private EntityPlayer lastTarget;
    private boolean lastTickPlaced;
    
    public AutoCrystalTwo() {
        super("AutoCrystalTwo", Category.COMBAT);
        this.isActive = false;
        this.tickDelay = (Setting<Integer>)this.register("TickDelay", 1, 0, 20);
        this.rotateS = (Setting<Boolean>)this.register("Rotate", false);
        this.hitRange = (Setting<Double>)this.register("HitRange", 6.0, 0.0, 10.0);
        this.wallsRange = (Setting<Double>)this.register("HitWallRange", 3.2, 0.0, 10.0);
        this.hitAttempts = (Setting<Integer>)this.register("HitAttempts", 0, 0, 10);
        this.hitMode = (Setting<HitMode>)this.register("HitMode", HitMode.Any);
        this.placeRange = (Setting<Double>)this.register("PlaceRange", 5.5, 0.0, 10.0);
        this.autoSwitch = (Setting<Boolean>)this.register("AutoSwitch", false);
        this.noGappleSwitch = (Setting<Boolean>)this.register("NoGapSwitch", true);
        this.minDmg = (Setting<Double>)this.register("MinDmg", 4.2, 0.0, 20.0);
        this.facePlaceHp = (Setting<Double>)this.register("FacePlaceHP", 8.0, 0.0, 20.0);
        this.maxSelfDmg = (Setting<Double>)this.register("MaxSelfDmg", 6.0, 0.0, 20.0);
        this.enemyRange = (Setting<Double>)this.register("EnemyRange", 10.0, 0.0, 20.0);
        this.renderMode = (Setting<RenderMode>)this.register("Render Mode", RenderMode.FULL);
        this.red = (Setting<Integer>)this.register("ESP Red", 255, 0, 255);
        this.green = (Setting<Integer>)this.register("ESP Green", 0, 0, 255);
        this.blue = (Setting<Integer>)this.register("ESP Blue", 0, 0, 255);
        this.linewidth = (Setting<Integer>)this.register("LineWidth", 1, 1, 10);
        this.sync = (Setting<Boolean>)this.register("Sync", false);
        this.tickCounter = 0;
        this.attempts = 0;
        this.lastHitCrystal = null;
        this.placePos = null;
        this.renderPos = null;
        this.switchCooldown = false;
        this.target = null;
        this.possibleTarget = null;
        this.lastTarget = null;
        this.lastTickPlaced = false;
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.getPlayer().getHealth() <= 0.0f || Wrapper.getPlayer().isDead) {
            return;
        }
        if (!this.interacted.isEmpty()) {
            for (final AxisAlignedBB bb : this.interacted) {
                for (final Entity e2 : Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity((Entity)null, bb)) {
                    if (e2 instanceof EntityEnderCrystal) {
                        final EntityEnderCrystal crystal = (EntityEnderCrystal)e2;
                        if (!this.placedCrystals.contains(crystal)) {
                            this.placedCrystals.add(crystal);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        if (this.tickDelay.getValue() != 0) {
            if (this.tickCounter < this.tickDelay.getValue()) {
                ++this.tickCounter;
                return;
            }
            this.tickCounter = 0;
        }
        final boolean rotate = this.rotateS.getValue();
        if (this.placeRange.getValue() > 0.0 && !this.lastTickPlaced) {
            if (!this.switchCooldown) {
                this.placePos = null;
                this.renderPos = null;
                this.possibleTarget = null;
                this.target = null;
                float dmg = 0.0f;
                for (final EntityPlayer player : (List)Wrapper.getWorld().playerEntities.stream().filter(p -> !p.equals((Object)Wrapper.getPlayer())).filter(p -> Wrapper.getPlayer().getDistance(p) <= this.enemyRange.getValue()).filter(p -> p.getHealth() > 0.0f).filter(p -> !p.isDead).filter(p -> Zori.getInstance().playerStatus.getStatus(p.getName()) != 1).sorted(Comparator.comparing(p -> Zori.getInstance().playerStatus.isEnemyInRange(this.enemyRange.getValue()) ? ((float)Zori.getInstance().playerStatus.getStatus(((EntityPlayer)p).getName())) : Wrapper.getPlayer().getDistance(p))).collect(Collectors.toList())) {
                    for (final BlockPos pos : this.findCrystalBlocks()) {
                        final float d = this.calculateDamage(pos, (Entity)player);
                        if (d > dmg && (d >= this.minDmg.getValue() || player.getHealth() + player.getAbsorptionAmount() <= this.facePlaceHp.getValue())) {
                            if (this.calculateDamage(pos, (Entity)Wrapper.getPlayer()) > this.maxSelfDmg.getValue()) {
                                continue;
                            }
                            dmg = d;
                            this.placePos = pos;
                            this.possibleTarget = player;
                        }
                    }
                }
            }
            this.switchCooldown = false;
            if (this.placePos != null) {
                final boolean offHand = Wrapper.getPlayer().getHeldItemOffhand().getItem() instanceof ItemEndCrystal;
                final boolean mainHand = Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemEndCrystal;
                if (!offHand && !mainHand && this.autoSwitch.getValue() && (!this.noGappleSwitch.getValue() || !this.isEatingGap())) {
                    for (int i = 0; i < 9; ++i) {
                        if (Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemEndCrystal) {
                            Wrapper.getPlayer().inventory.currentItem = i;
                            this.switchCooldown = true;
                            return;
                        }
                    }
                }
                if (offHand || mainHand) {
                    this.renderPos = this.placePos;
                    if (this.possibleTarget != null) {
                        this.target = this.possibleTarget;
                        this.lastTarget = this.target;
                        KillEventHelper.INSTANCE.addTarget(this.target);
                    }
                    this.isActive = true;
                    if (rotate) {
                        this.rotateTo(this.placePos.getX() + 0.5, this.placePos.getY() - 0.5, this.placePos.getZ() + 0.5);
                    }
                    Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placePos, EnumFacing.UP, offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    final AxisAlignedBB bb2 = new AxisAlignedBB(this.placePos.up());
                    this.interacted.add(bb2);
                    if (rotate) {
                        this.resetRotation();
                    }
                    this.lastTickPlaced = true;
                    this.isActive = false;
                    return;
                }
            }
        }
        this.lastTickPlaced = false;
        if (this.hitRange.getValue() > 0.0) {
            Predicate<? super Entity> predicate = null;
            switch (this.hitMode.getValue()) {
                default: {
                    predicate = (entity -> true);
                    break;
                }
                case OnlyOwn: {
                    predicate = (entity -> this.placedCrystals.contains(entity));
                    break;
                }
                case PreferOwn: {
                    predicate = (entity -> !this.placedValidCrystal() || this.placedCrystals.contains(entity));
                    break;
                }
            }
            final EntityEnderCrystal crystal2 = (EntityEnderCrystal)Wrapper.getWorld().loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(predicate).filter(e -> this.isValid(e)).filter(e -> this.hitAttempts.getValue() <= 0 || this.attempts < this.hitAttempts.getValue() || this.lastHitCrystal == null || e != this.lastHitCrystal).min(Comparator.comparing(e -> (this.lastTarget == null || this.lastTarget.getHealth() <= 0.0f || this.lastTarget.isDead) ? Wrapper.getPlayer().getDistance(e) : this.lastTarget.getDistance(e))).orElse(null);
            if (crystal2 != null) {
                this.isActive = true;
                if (rotate) {
                    this.rotateTo((Entity)crystal2);
                }
                Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.getPlayer(), (Entity)crystal2);
                Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
                if (this.lastHitCrystal == crystal2) {
                    ++this.attempts;
                }
                else {
                    this.lastHitCrystal = crystal2;
                    this.attempts = 1;
                }
                if (rotate) {
                    this.resetRotation();
                }
                this.isActive = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onUseItemOnBlock(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
            final AxisAlignedBB bb = new AxisAlignedBB(packet.getPos().up());
            this.interacted.add(bb);
        }
    }
    
    @Override
    public void onRender3D() {
        if (Wrapper.getPlayer() != null && Wrapper.getWorld() != null) {
            if (this.sync.getValue()) {
                final Color c = Zori.getInstance().clientSettings.getColorr(255);
                this.Red = c.getRed();
                this.Green = c.getGreen();
                this.Blue = c.getBlue();
            }
            else {
                this.Red = this.red.getValue();
                this.Green = this.green.getValue();
                this.Blue = this.blue.getValue();
            }
            if (this.renderPos != null) {
                if (this.renderMode.getValue().equals(RenderMode.FULL)) {
                    RenderUtils.INSTANCE.drawBoundingBox(this.renderPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.5f, this.linewidth.getValue());
                    RenderUtils.INSTANCE.drawBox(this.renderPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.22f);
                }
                else if (this.renderMode.getValue().equals(RenderMode.OUTLINE)) {
                    RenderUtils.INSTANCE.drawBoundingBox(this.renderPos, this.Red / 255.0f, this.Green / 255.0f, this.Blue / 255.0f, 0.5f, this.linewidth.getValue());
                }
            }
        }
    }
    
    public void onEnable() {
        this.attempts = 0;
        this.tickCounter = this.tickDelay.getValue();
        this.lastHitCrystal = null;
        this.placePos = null;
        this.renderPos = null;
        this.switchCooldown = false;
        this.possibleTarget = null;
        this.target = null;
        this.lastTarget = null;
        this.isActive = false;
        this.placedCrystals = new ArrayList<EntityEnderCrystal>();
        this.interacted = new ArrayList<AxisAlignedBB>();
    }
    
    public void onDisable() {
        this.attempts = 0;
        this.tickCounter = this.tickDelay.getValue();
        this.lastHitCrystal = null;
        this.placePos = null;
        this.renderPos = null;
        this.switchCooldown = false;
        this.possibleTarget = null;
        this.target = null;
        this.lastTarget = null;
        this.isActive = false;
        this.placedCrystals = new ArrayList<EntityEnderCrystal>();
        this.interacted = new ArrayList<AxisAlignedBB>();
    }
    
    private boolean isValid(final EntityEnderCrystal crystal) {
        final boolean a = crystal != null && !crystal.isDead;
        return a && (Wrapper.getPlayer().canEntityBeSeen((Entity)crystal) ? (Wrapper.getPlayer().getDistance((Entity)crystal) <= this.hitRange.getValue()) : (Wrapper.getPlayer().getDistance((Entity)crystal) <= this.wallsRange.getValue()));
    }
    
    private boolean placedValidCrystal() {
        if (this.placedCrystals.isEmpty()) {
            return false;
        }
        for (final EntityEnderCrystal crystal : this.placedCrystals) {
            if (this.isValid(crystal)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isEatingGap() {
        return Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemAppleGold && Wrapper.getPlayer().isHandActive();
    }
    
    private void rotateTo(final double x, final double y, final double z) {
        Zori.getInstance().rotationManager.rotate(x, y, z);
    }
    
    private void rotateTo(final Entity target) {
        this.rotateTo(target.posX, target.posY, target.posZ);
    }
    
    private void resetRotation() {
        Zori.getInstance().rotationManager.reset();
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.BEDROCK || Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && Wrapper.getWorld().getBlockState(boost).getBlock() == Blocks.AIR && Wrapper.getWorld().getBlockState(boost2).getBlock() == Blocks.AIR && Wrapper.getWorld().getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && Wrapper.getWorld().getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Wrapper.getPlayer().posX), Math.floor(Wrapper.getPlayer().posY), Math.floor(Wrapper.getPlayer().posZ));
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> circleblocks = (NonNullList<BlockPos>)NonNullList.create();
        final BlockPos loc = this.getPlayerPos();
        final float r = this.placeRange.getValue().floatValue();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = cy - (int)r; y < cy + r; ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r) {
                        final BlockPos l = new BlockPos(x, y, z);
                        if (this.canPlaceCrystal(l)) {
                            circleblocks.add((Object)l);
                        }
                    }
                }
            }
        }
        return (List<BlockPos>)circleblocks;
    }
    
    private float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion((World)Wrapper.getWorld(), (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private float getDamageMultiplied(final float damage) {
        final int diff = Wrapper.getWorld().getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    private float calculateDamage(final BlockPos blockPos, final Entity entity) {
        return this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, entity);
    }
    
    public enum HitMode
    {
        Any, 
        PreferOwn, 
        OnlyOwn;
    }
    
    public enum RenderMode
    {
        FULL, 
        OUTLINE;
    }
}
