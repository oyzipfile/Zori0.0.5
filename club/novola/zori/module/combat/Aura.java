// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.combat;

import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import club.novola.zori.event.PacketSendEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.Zori;
import net.minecraft.item.ItemSword;
import club.novola.zori.util.Wrapper;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Aura extends Module
{
    private Setting<Mode> mode;
    private Setting<Double> range;
    private Setting<Boolean> rotate;
    private Setting<Boolean> crits;
    private Setting<Boolean> delay;
    private boolean isAttacking;
    
    public Aura() {
        super("Aura", Category.COMBAT);
        this.mode = (Setting<Mode>)this.register("Mode", Mode.SMART);
        this.range = (Setting<Double>)this.register("Range", 5.5, 0.0, 7.0);
        this.rotate = (Setting<Boolean>)this.register("Rotate", true);
        this.crits = (Setting<Boolean>)this.register("Criticals", true);
        this.delay = (Setting<Boolean>)this.register("Delay", true);
        this.isAttacking = false;
    }
    
    @Override
    public String getHudInfo() {
        return this.mode.getValue().name();
    }
    
    @Override
    public void onUpdate() {
        if (Wrapper.getWorld() == null || Wrapper.getPlayer() == null || Wrapper.getWorld().playerEntities.isEmpty()) {
            return;
        }
        if (this.mode.getValue().equals(Mode.SMART)) {
            if (!(Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)) {
                return;
            }
            final ModuleManager moduleManager = Zori.getInstance().moduleManager;
            if (((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).isActive) {
                return;
            }
        }
        final List<EntityPlayer> list = new ArrayList<EntityPlayer>();
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
            list.add(player);
        }
        if (list.isEmpty()) {
            return;
        }
        list.sort(Comparator.comparing(p -> Zori.getInstance().playerStatus.isEnemyInRange(this.range.getValue()) ? ((float)Zori.getInstance().playerStatus.getStatus(((EntityPlayer)p).getName())) : Wrapper.getPlayer().getDistance(p)));
        this.attack(list.get(0));
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (this.crits.getValue() && packet.getAction().equals((Object)CPacketUseEntity.Action.ATTACK) && Wrapper.getPlayer() != null && Wrapper.getPlayer().onGround && this.isAttacking) {
                Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.10000000149011612, Wrapper.getPlayer().posZ, false));
                Wrapper.getPlayer().connection.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
            }
        }
    }
    
    private void attack(final EntityPlayer target) {
        if (Wrapper.getPlayer().getCooledAttackStrength(0.0f) >= 1.0f || !this.delay.getValue()) {
            this.isAttacking = true;
            final boolean rotatee = this.rotate.getValue();
            if (rotatee) {
                Zori.getInstance().rotationManager.rotate(target.posX, target.posY, target.posZ);
            }
            Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.getPlayer(), (Entity)target);
            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
            if (rotatee) {
                Zori.getInstance().rotationManager.reset();
            }
            this.isAttacking = false;
        }
    }
    
    public enum Mode
    {
        NORMAL, 
        SMART;
    }
}
