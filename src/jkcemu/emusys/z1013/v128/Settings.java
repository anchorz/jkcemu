package jkcemu.emusys.z1013.v128;

import java.lang.reflect.Field;
import java.util.Properties;

import javax.swing.event.ChangeListener;

import jkcemu.base.EmuUtil;
import jkcemu.emusys.z1013.v128.Settings.BooleanProperty;

public class Settings {

    public Settings(String propPrefix, String subclass) {
        this.propPrefix = propPrefix + subclass + ".";
    }

    private abstract class BaseProperty {
        public abstract void getProperty(Properties props);

        public abstract void setProperty(Properties props);
    }

    public class BooleanProperty extends BaseProperty {

        ChangeListener l;
        public boolean value;

        public BooleanProperty(boolean defaultValue) {
            this.value = defaultValue;
        }

        public String getName() {
            String ret = "unknown";
            Field[] f = Settings.class.getFields();
            for (Field a : f) {
                try {
                    if (a.get(Settings.this) == this) {
                        ret = a.getName();
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return ret;
        }

        public void getProperty(Properties props) {
            String jp = EmuUtil.getProperty(props, propPrefix + getName());
            if (jp.length() != 0) {
                value = Boolean.parseBoolean(jp);
            }
            if (l != null) {
                l.stateChanged(null);
            }
        }

        public void setProperty(Properties props) {
            EmuUtil.setProperty(props, propPrefix + getName(), value);
        }

        public void setChangeListener(ChangeListener l) {
            this.l = l;
        }

    };

    private String propPrefix;

    public BooleanProperty jp6_64k_enable = new BooleanProperty(true);
    public BooleanProperty jp8_eprom_resetActive = new BooleanProperty(false);
    public BooleanProperty jp9_bws_resetActive = new BooleanProperty(true);
    public BooleanProperty jp19_64k_resetHigh = new BooleanProperty(false);
    public BooleanProperty jp11_eprom_inactive = new BooleanProperty(false);

    public int ramBank;
    public boolean epromActive;
    public boolean bwsDisabled;
    public int epromBank;
    public int port4;

    public void applySettings(Properties props) {
        for (Field a : Settings.class.getFields()) {
            try {
                Object o = a.get(Settings.this);
                if (o instanceof BaseProperty) {
                    BaseProperty b = (BaseProperty) o;
                    b.setProperty(props);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void updFields(Properties props) {
        for (Field a : Settings.class.getFields()) {
            try {
                Object o = a.get(Settings.this);
                if (o instanceof BaseProperty) {
                    BaseProperty b = (BaseProperty) o;
                    b.getProperty(props);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        port4 = 0;
        epromBank = 0;
        bwsDisabled = !jp9_bws_resetActive.value;
        epromActive = jp8_eprom_resetActive.value;
        ramBank = (this.jp19_64k_resetHigh.value && this.jp6_64k_enable.value) ? 1 : 0;
    }
}
