// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.gui;

import java.io.IOException;

public interface IGuiComponent
{
    void draw(final int p0, final int p1);
    
    void mouseClicked(final int p0, final int p1, final int p2) throws IOException;
    
    void mouseReleased(final int p0, final int p1, final int p2);
    
    void keyTyped(final char p0, final int p1) throws IOException;
}
