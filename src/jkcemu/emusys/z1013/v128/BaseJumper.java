package jkcemu.emusys.z1013.v128;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class BaseJumper extends JPanel implements FocusListener {

	private static final long serialVersionUID = 270434220125993707L;

	public static final int TYPE_321 = 321;
	public static final int TYPE_123 = 123;

	private int type;
	private int cnt;
	private int value, bitValue;

	private JLabel lMain,l1,l2,l3;
	private JComponent jp;
	private ChangeListener listener;
	ChangeEvent ev;

	int dataRange;

	int getNextValue(int in) {
		return (in + 1) % dataRange;
	}

	protected int mapValue(int value) {
		int ret = 1 << value;
		return ret;
	}

	public void setEnabled(boolean v) {
		lMain.setEnabled(v);
		l1.setEnabled(v);
		l2.setEnabled(v);
		l3.setEnabled(v);
		super.setEnabled(v);
	}

	BaseJumper(String label, int xmm, int ymm, int type, int cnt, int range) {
		super(new GridLayout(1, 1));
		ev = new ChangeEvent(this);

		this.type = type;
		this.cnt = cnt;
		this.dataRange = range;

		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		JPanel main = new JPanel(new BorderLayout());
		main.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		add(main);
		lMain = new JLabel(label);
		main.add(lMain, BorderLayout.NORTH);

		jp = createJumperField();
		Dimension d = jp.getPreferredSize();
		setBounds(((xmm - 65) * 10) / 2, ((109 - ymm) * 7) / 2, 50, d.height);
		main.add(jp, BorderLayout.CENTER);

		JPanel numbering = new JPanel(new GridLayout(1, 3));
		String[] order = { "3", "2", "1" };
		if (type == TYPE_123) {
			order[0] = "1";
			order[2] = "3";
		}
		l1 = new JLabel(order[0]);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		numbering.add(l1);
		l2 = new JLabel(order[1]);
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		numbering.add(l2);
		l3 = new JLabel(order[2]);
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		numbering.add(l3);
		main.add(numbering, BorderLayout.SOUTH);
		setFocusable(true);

		addFocusListener(this);
		final BaseJumper thisComponent = this;
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (BaseJumper.this.isEnabled()) {
					setValue(getNextValue(getValue()));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (BaseJumper.this.isEnabled()) {
					thisComponent.requestFocus();
				}
			}
		});
	}

	public void setChangeListener(ChangeListener l) {
		listener = l;
	}

	public int getValue() {
		return value;
	}

	public void update() {
		if (toolTip != null) {
			this.setToolTipText(toolTip[value]);
		}
		bitValue = mapValue(value);
		if (type == TYPE_321) {
			int tmp = bitValue;
			bitValue = 0;
			for (int i = 0; i < cnt; i++) {
				bitValue *= 2;
				bitValue |= tmp & 1;
				tmp /= 2;
			}
		}
		if (listener != null) {
			listener.stateChanged(ev);
		}
		invalidate();
		repaint();
	}

	protected void setValue(int value) {
		this.value = value;
		update();
	}

	private class Jumperfield extends JComponent {

		private static final long serialVersionUID = -8071037550521735800L;

		int cnt;

		Jumperfield(int cnt) {
			this.cnt = cnt;
		}

		public Color getForeground() {
			if (BaseJumper.this.isEnabled()) {
				return Color.BLACK;
			}
			return Color.LIGHT_GRAY;
		}

		public void paint(Graphics g) {
			Dimension d = this.getSize();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(getForeground());
			g.drawRect(0, 0, d.width - 1, d.height - 1);
			int w = d.width - 2;
			int v = bitValue;
			for (int i = 0; i < cnt; i++) {
				g.setColor(getForeground());
				int sw = (i * w) / cnt;
				int dw = ((i + 1) * w) / cnt - sw;
				if ((v & 1) != 0) {
					g.fillRect(1 + sw, 1, dw, d.height - 2);
				}
				v /= 2;
			}
		}
	}

	protected JComponent createJumperField() {
		JComponent jumper = new Jumperfield(cnt);
		return jumper;
	}

	public int getType() {
		return type;
	}

	@Override
	public void focusGained(FocusEvent e) {
		this.setBorder(BorderFactory.createDashedBorder(null));
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	String[] toolTip;

	public void setInfoMessage(String[] msg) {
		this.toolTip = msg;
		dataRange = toolTip.length;
	}
}
