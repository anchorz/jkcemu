package jkcemu.emusys.z1013.v128;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jkcemu.emusys.z1013.v128.Settings.IntProperty;

public class ArrayJumper extends BaseJumper implements ChangeListener {

    private static final long serialVersionUID = -6713529462732217123L;
    public static final int Settings8 = 8;
    public static final int Settings3 = 3;

    public ArrayJumper(OptionPanel owner, String label, int xmm, int ymm, int range) {
        super(owner, label, xmm, ymm, BaseJumper.TYPE_321, 3, range);
    }

    protected void setValue(int v) {
        switch (v) {
        case 0:
            pValue.value = 4000;
            break;
        default:
        case 1:
            pValue.value = 2000;
            break;
        case 2:
            pValue.value = 1000;
        }
        super.setValue(v);
        owner.fireDataChanged();
    }

    @Override
    protected int mapValue(int value) {
        if (dataRange != 3) {
            return value;
        }
        return super.mapValue(value);
    }

    @Override
    protected JComponent createJumperField() {
        JComponent jumper = super.createJumperField();
        jumper.setPreferredSize(new Dimension(50, 60));
        return jumper;
    }

    IntProperty pValue;

    public void setProperty(IntProperty pValue) {
        this.pValue = pValue;
        pValue.setChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }

}
