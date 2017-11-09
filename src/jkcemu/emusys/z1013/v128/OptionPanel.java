package jkcemu.emusys.z1013.v128;

import jkcemu.base.AbstractSettingsFld;
import jkcemu.base.SettingsFrm;

public class OptionPanel extends AbstractSettingsFld {

    private static final long serialVersionUID = -8666929385850460241L;
    public Settings settings;
    String propPrefix;

    public OptionPanel(SettingsFrm settingsFrm, String propPrefix) {
        super(settingsFrm, propPrefix);
        settings = new Settings(settingsFrm,propPrefix, "128");
        setLayout(null); // Das Layout basiert auf den absoluten Koordinaten der
                         // Jumper.
        this.propPrefix = propPrefix;

        ArrayJumper jp7 = new ArrayJumper(this, "JP7", 121, 108, ArrayJumper.Settings3);
        jp7.setInfoMessage(new String[] { "4 MHz", "2 MHz", "1 MHz" });
        jp7.setProperty(settings.jp7_freq);
        jp7.setEnabled(false);
        add(jp7);
        Jumper jp6 = new Jumper(this, "JP6", 135, 48, Jumper.TYPE_321);
        jp6.setInfoMessage(
                new String[] { "64k High kann nicht aktiviert werden", "64k High kann mit D6 aktiviert werden" });
        jp6.setProperty(settings.jp6_64k_enable);
        add(jp6);
        Jumper jp8 = new Jumper(this, "JP8", 122, 51, Jumper.TYPE_123);
        jp8.setInfoMessage(new String[] { "32K Eprom ist nach Reset inaktiv", "32K Eprom ist nach Reset aktiv" });
        jp8.setProperty(settings.jp8_eprom_resetActive);
        jp8.setEnabled(true);
        add(jp8);

        Jumper jp9 = new Jumper(this, "JP9", 135, 54 + 10, Jumper.TYPE_321);
        jp9.setInfoMessage(
                new String[] { "BWS+Monitor ist nach Reset abgeschalten", "BWS+Monitor ist nach Reset aktiv" });
        jp9.setProperty(settings.jp9_bws_resetActive);
        add(jp9);

        Jumper jp11 = new Jumper(this, "JP11", 96, 51, Jumper.TYPE_321);
        jp11.setInfoMessage(new String[] { "32K EPROM kann aktiviert werden", "32K EPROM inaktiv" });
        jp11.setProperty(settings.jp11_eprom_inactive);
        jp11.setEnabled(true);
        add(jp11);

        ArrayJumper jp14 = new ArrayJumper(this, "JP14", 65, 73, ArrayJumper.Settings8);
        jp14.setEnabled(false);
        add(jp14);
        Jumper jp17 = new Jumper(this, "JP17", 109, 51, Jumper.TYPE_321);
        jp17.setEnabled(false);
        add(jp17);
        Jumper jp18 = new Jumper(this, "JP18", 107, 80, Jumper.TYPE_123);
        jp18.setEnabled(false);
        add(jp18);

        Jumper jp19 = new Jumper(this, "JP19", 93, 74, Jumper.TYPE_123);
        jp19.setInfoMessage(
                new String[] { "RAM 64k High ist nach Reset inaktiv", "RAM 64k High ist nach Reset aktiv" });
        jp19.setProperty(settings.jp19_64k_resetHigh);
        add(jp19);
    }

    public void fireDataChanged() {
        super.fireDataChanged();
    }
}
