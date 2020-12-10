// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.hud.components;

import java.util.Random;
import java.util.List;
import club.novola.zori.Zori;
import java.util.ArrayList;
import club.novola.zori.util.Wrapper;
import club.novola.zori.module.hud.Welcomer;
import club.novola.zori.hud.HudComponent;

public class WelcomerComponent extends HudComponent<Welcomer>
{
    String name;
    private int timer;
    
    public WelcomerComponent() {
        super("Welcomer", 2, 2, Welcomer.INSTANCE);
    }
    
    @Override
    public void render() {
        if (Wrapper.getPlayer() != null && Wrapper.mc.world != null) {
            ++this.timer;
            super.render();
            final List<String> nameList = new ArrayList<String>();
            nameList.add("Vaporimeter");
            nameList.add("MaajLogjam");
            nameList.add("Cloying");
            nameList.add("MatrixjOr");
            nameList.add("Epiphany");
            nameList.add("Spinnbar");
            nameList.add("MethacrMadcap");
            nameList.add("Paraesthesia");
            nameList.add("Absquatulate");
            nameList.add("UncladJinker");
            nameList.add("Landlubber");
            nameList.add("Elytriferous");
            nameList.add("Shebang");
            nameList.add("ShihTzu");
            nameList.add("Piccadilly");
            nameList.add("Serpenticide");
            nameList.add("Decklechick3");
            nameList.add("Gasconade");
            nameList.add("Pettifogger");
            nameList.add("Petcock");
            nameList.add("Javanais");
            nameList.add("Squeegee");
            nameList.add("Pannikin");
            nameList.add("Gewgawperty05");
            nameList.add("Beeno1000");
            nameList.add("Cathedra");
            nameList.add("Swashbuckler");
            nameList.add("Oxymoron");
            nameList.add("Logotype");
            nameList.add("Frogman");
            nameList.add("Xenogenous");
            nameList.add("Acipenser");
            if (((Welcomer)this.module).welcomeMode.getValue().equals(Welcomer.Mode.NORMAL)) {
                this.name = Wrapper.getPlayer().getName();
            }
            else if (((Welcomer)this.module).welcomeMode.getValue().equals(Welcomer.Mode.DOX)) {
                this.name = System.getProperty("user.name");
            }
            else if (((Welcomer)this.module).welcomeMode.getValue().equals(Welcomer.Mode.FAKENAME) && this.timer >= 600) {
                this.name = this.getRandomName(nameList);
                this.timer = -150;
            }
            Wrapper.getFontRenderer().drawStringWithShadow("Merry Christmas " + this.name + " :^)", (float)this.x, (float)this.y, Zori.getInstance().clientSettings.getColor());
            this.width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
        }
    }
    
    public String getRandomName(final List<String> list) {
        final Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
