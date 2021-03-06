/*
 * (c) 2008-2017 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Dialog zur Auswahl eines Zeichensatzes und weiterer Optionen
 */

package jkcemu.text;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.EventObject;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jkcemu.base.BaseDlg;


public class SelectEncodingDlg extends BaseDlg
{
  private boolean           applied;
  private boolean           ignoreEofByte;
  private CharConverter     charConverter;
  private String            encodingName;
  private String            encodingDisplayText;
  private JComboBox<Object> comboEncoding;
  private JCheckBox         btnIgnoreEofByte;
  private JButton           btnOK;
  private JButton           btnCancel;


  public SelectEncodingDlg( Frame parent )
  {
    super( parent, "Zeichensatz ausw\u00E4hlen" );


    // Initialisierungen
    this.applied             = false;
    this.ignoreEofByte       = false;
    this.charConverter       = null;
    this.encodingName        = null;
    this.encodingDisplayText = null;


    // Fensterinhalt
    setLayout( new GridBagLayout() );

    GridBagConstraints gbc = new GridBagConstraints(
					0, 0,
					2, 1,
					0.0, 0.0,
					GridBagConstraints.WEST,
					GridBagConstraints.NONE,
					new Insets( 5, 5, 5, 5 ),
					0, 0 );

    // Fragetext
    add(
	new JLabel(
		"Mit welchem Zeichensatz soll die Datei"
			+ " ge\u00F6ffnet werden?" ),
	gbc );

    // Auswahlfeld
    this.comboEncoding = new JComboBox<>();
    this.comboEncoding.addItem( "Systemzeichensatz" );
    this.comboEncoding.addItem(
                new CharConverter( CharConverter.Encoding.ASCII_7BIT ) );
    this.comboEncoding.addItem(
                new CharConverter( CharConverter.Encoding.ISO646DE ) );
    this.comboEncoding.addItem(
		new CharConverter( CharConverter.Encoding.CP437 ) );
    this.comboEncoding.addItem(
		new CharConverter( CharConverter.Encoding.CP850 ) );
    this.comboEncoding.addItem( new CharConverter(
		CharConverter.Encoding.LATIN1 ) );
    this.comboEncoding.addItem( "UTF-8" );
    this.comboEncoding.addItem( "UTF-16BE (Big Endian)" );
    this.comboEncoding.addItem( "UTF-16LE (Little Endian)" );
    this.comboEncoding.setEditable( false );
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridy++;
    add( this.comboEncoding, gbc );

    // Hinweistext
    JLabel label = new JLabel( "Achtung!" );
    Font font = label.getFont();
    if( font != null ) {
      label.setFont( font.deriveFont( Font.BOLD ) );
    }
    gbc.anchor        = GridBagConstraints.WEST;
    gbc.insets.top    = 20;
    gbc.insets.bottom = 0;
    gbc.gridy++;
    add( label, gbc );

    gbc.insets.top = 0;
    gbc.gridy++;
    add(
	new JLabel(
		"Die Datei wird als Textdatei mit dem"
			+ " ausgew\u00E4hlten Zeichensatz ge\u00F6ffnet." ),
	gbc );

    gbc.gridy++;
    add(
	new JLabel(
		"Das gilt auch, wenn die Datei gar keine"
			+ " Textdatei ist oder in einem" ),
	gbc );

    gbc.gridy++;
    add(
	new JLabel( "anderem Zeichensatz gespeichert wurde." ),
	gbc );

    this.btnIgnoreEofByte = new JCheckBox(
		"Eventuell vorhandenes Dateiendezeichen ignorieren" );
    gbc.anchor     = GridBagConstraints.CENTER;
    gbc.insets.top = 20;
    gbc.gridy++;
    add( this.btnIgnoreEofByte, gbc );


    // Knoepfe
    JPanel panelBtn = new JPanel();
    panelBtn.setLayout( new GridLayout( 1, 2, 5, 5 ) );

    this.btnOK = new JButton( "OK" );
    this.btnOK.addActionListener( this );
    this.btnOK.addKeyListener( this );
    panelBtn.add( this.btnOK );

    this.btnCancel = new JButton( "Abbrechen" );
    this.btnCancel.addActionListener( this );
    this.btnCancel.addKeyListener( this );
    panelBtn.add( this.btnCancel );

    gbc.insets.top = 10;
    gbc.gridy++;
    add( panelBtn, gbc );


    // Fenstergroesse und -position
    pack();
    setParentCentered();
    setResizable( false );
  }


  public boolean encodingChoosen()
  {
    return this.applied;
  }


  public CharConverter getCharConverter()
  {
    return this.charConverter;
  }


  public String getEncodingName()
  {
    return this.encodingName;
  }


  public String getEncodingDisplayText()
  {
    return this.encodingDisplayText;
  }


  public boolean getIgnoreEofByte()
  {
    return this.ignoreEofByte;
  }


	/* --- ueberschriebene Methoden --- */

  @Override
  protected boolean doAction( EventObject e )
  {
    boolean rv = false;
    if( e != null ) {
      Object src = e.getSource();
      if( src != null ) {
	if( src == this.btnOK ) {
	  doApply();
	}
	else if( src == this.btnCancel ) {
	  doClose();
	}
      }
    }
    return rv;
  }


	/* --- private Methoden --- */

  private void doApply()
  {
    Object encodingObj = this.comboEncoding.getSelectedItem();
    if( encodingObj != null ) {
      this.encodingDisplayText = encodingObj.toString();
      if( encodingObj instanceof CharConverter ) {
	this.charConverter = (CharConverter) encodingObj;
	this.encodingName  = charConverter.getEncodingName();
      } else {
	String s = encodingObj.toString();
	if( s != null ) {
	  if( s.startsWith( "ISO" ) || s.startsWith( "UTF" ) ) {
	    int posSpace = s.indexOf( '\u0020' );
	    this.encodingName = (posSpace > 0 ?
					s.substring( 0, posSpace ) : s);
	  }
	}
      }
    }
    this.ignoreEofByte = this.btnIgnoreEofByte.isSelected();
    this.applied       = true;
    doClose();
  }
}
