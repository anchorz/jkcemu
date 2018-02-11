package jkcemu.emusys.z1013.v128;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jkcemu.emusys.z1013.v128.Settings.BooleanProperty;

public class Jumper extends BaseJumper implements ChangeListener {
    private static final long serialVersionUID = -5331413286105856161L;
    private static final int SET_23 = 1;
    private static final int SET_12 = 0;
    
    public Jumper(OptionPanel owner,String label, int xmm, int ymm, int type) {
        super(owner, label, xmm, ymm, type, 2, 2);
    }

    @Override
    protected JComponent createJumperField() {
        JComponent jumper = super.createJumperField();
        jumper.setPreferredSize(new Dimension(50, 50));
        return jumper;
    }

    BooleanProperty pValue;
    
    public void setProperty(BooleanProperty pValue) {
        this.pValue=pValue;   
        pValue.setChangeListener(this);
    }
    
    protected void setValue(int v)
    { 
        if (v==1)
        {
            pValue.value=true;             
        } else {
            pValue.value=false;                         
        }
        super.setValue(v);
        owner.fireDataChanged();
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (pValue.value) {
            setValue(Jumper.SET_23);
        } else {
            setValue(Jumper.SET_12);                 
        }        
    }
}
