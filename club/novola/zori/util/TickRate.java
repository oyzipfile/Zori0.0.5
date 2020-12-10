// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import java.util.Arrays;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.Packet;
import java.text.DecimalFormat;

public class TickRate
{
    public static float TPS;
    public static TickRate INSTANCE;
    public static long lastUpdate;
    public static float[] tpsCounts;
    public static DecimalFormat format;
    
    public static void update(final Packet packet) {
        if (!(packet instanceof SPacketTimeUpdate)) {
            return;
        }
        final long currentTime = System.currentTimeMillis();
        if (TickRate.lastUpdate == -1L) {
            TickRate.lastUpdate = currentTime;
            return;
        }
        final long timeDiff = currentTime - TickRate.lastUpdate;
        float tickTime = (float)(timeDiff / 20L);
        if (tickTime == 0.0f) {
            tickTime = 50.0f;
        }
        float tps = 1000.0f / tickTime;
        if (tps > 20.0f) {
            tps = 20.0f;
        }
        if (TickRate.tpsCounts.length - 1 >= 0) {
            System.arraycopy(TickRate.tpsCounts, 0, TickRate.tpsCounts, 1, TickRate.tpsCounts.length - 1);
        }
        TickRate.tpsCounts[0] = tps;
        double total = 0.0;
        for (final float f : TickRate.tpsCounts) {
            total += f;
        }
        total /= TickRate.tpsCounts.length;
        if (total > 20.0) {
            total = 20.0;
        }
        TickRate.TPS = Float.parseFloat(TickRate.format.format(total));
        TickRate.lastUpdate = currentTime;
    }
    
    public static void reset() {
        Arrays.fill(TickRate.tpsCounts, 20.0f);
        TickRate.TPS = 20.0f;
    }
    
    static {
        TickRate.TPS = 20.0f;
        TickRate.lastUpdate = -1L;
        TickRate.tpsCounts = new float[10];
        TickRate.format = new DecimalFormat("##.0#");
    }
}
