// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatus
{
    private List<String> friends;
    private List<String> enemies;
    
    public PlayerStatus() {
        this.friends = new ArrayList<String>();
        this.enemies = new ArrayList<String>();
    }
    
    public List<String> getFriends() {
        return this.friends;
    }
    
    public List<String> getEnemies() {
        return this.enemies;
    }
    
    public void addFriend(final String name) {
        this.friends.add(name);
    }
    
    public void addEnemy(final String name) {
        this.enemies.add(name);
    }
    
    public void delFriend(final String name) {
        this.friends.remove(name);
    }
    
    public void delEnemy(final String name) {
        this.enemies.remove(name);
    }
    
    public boolean isEnemyInRange(final double range) {
        for (final EntityPlayer p : Wrapper.getWorld().playerEntities) {
            if (Wrapper.getPlayer().getDistance((Entity)p) > range) {
                continue;
            }
            if (this.enemies.contains(p.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public int getStatus(final String name) {
        if (this.friends.contains(name)) {
            return 1;
        }
        if (this.enemies.contains(name)) {
            return -1;
        }
        return 0;
    }
}
