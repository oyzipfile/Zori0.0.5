// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.module.misc;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.util.ResourceLocation;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import club.novola.zori.util.Wrapper;
import club.novola.zori.event.PlayerKillEvent;
import com.google.common.collect.Lists;
import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import net.minecraft.util.SoundEvent;
import java.util.List;
import club.novola.zori.module.Module;

public class SoundEffects extends Module
{
    private final List<SoundEvent> effects;
    private final SoundEvent hitmarker;
    private int hitDelay;
    private final Setting<Boolean> kills;
    private final Setting<Boolean> hit;
    
    public SoundEffects() {
        super("SoundEffects", Category.MISC);
        this.hitDelay = 0;
        this.kills = (Setting<Boolean>)this.register("Kills", true);
        this.hit = (Setting<Boolean>)this.register("HitMarker", true);
        Zori.getInstance().log.info("[SoundEffects] Registering Sounds");
        this.effects = (List<SoundEvent>)Lists.newArrayList((Object[])new SoundEvent[] { this.registerS("godlike"), this.registerS("holyshit"), this.registerS("impressive"), this.registerS("ownage"), this.registerS("perfect"), this.registerS("wickedsick") });
        this.hitmarker = this.registerS("skeet");
        Zori.getInstance().log.info("[SoundEffects] Successfully Registered Sounds");
    }
    
    @Override
    public void onUpdate() {
        if (!this.hit.getValue()) {
            this.hitDelay = 0;
            return;
        }
        if (this.hitDelay > 0) {
            --this.hitDelay;
        }
    }
    
    public void onEnable() {
        this.hitDelay = 0;
    }
    
    @SubscribeEvent
    public void onKill(final PlayerKillEvent event) {
        if (Wrapper.getPlayer() != null && this.kills.getValue()) {
            Wrapper.getPlayer().playSound(this.randomSound(), 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent event) {
        if (Wrapper.getPlayer() != null && this.hit.getValue() && this.hitDelay <= 0) {
            Wrapper.getPlayer().playSound(this.hitmarker, 0.5f, 1.0f);
            this.hitDelay = 10;
        }
    }
    
    private SoundEvent randomSound() {
        return this.effects.get(ThreadLocalRandom.current().nextInt(0, this.effects.size()));
    }
    
    private SoundEvent registerS(final String name) {
        final SoundEvent soundEvent = new SoundEvent(new ResourceLocation("zori", name));
        soundEvent.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register((IForgeRegistryEntry)soundEvent);
        return soundEvent;
    }
}
