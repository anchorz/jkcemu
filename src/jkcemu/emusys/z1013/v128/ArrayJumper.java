package jkcemu.emusys.z1013.v128;

import java.awt.Dimension;

import javax.swing.JComponent;

public class ArrayJumper extends BaseJumper {
    
    private static final long serialVersionUID = -6713529462732217123L;
    public static final int Settings8 = 8;
    public static final int Settings3 = 3;

    public ArrayJumper(String label, int xmm, int ymm, int range) {
        super(label,xmm,ymm,BaseJumper.TYPE_321,3,range);
    }

    @Override
    protected int mapValue(int value) {
        System.out.println("value="+value);
        if (dataRange!=3){
            return  value;
        }
        return super.mapValue(value) ;
    }
    
    @Override
    protected JComponent createJumperField() {
        JComponent jumper=super.createJumperField();
        jumper.setPreferredSize(new Dimension(50,60));
        return jumper;
    }

}
