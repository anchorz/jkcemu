/*
 * (c) 2016-2017 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Komponente fuer die NANOS-Einstellungen
 */

package jkcemu.emusys.etc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.EventObject;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import jkcemu.Main;
import jkcemu.base.AbstractSettingsFld;
import jkcemu.base.AutoInputSettingsFld;
import jkcemu.base.EmuUtil;
import jkcemu.base.FileNameFld;
import jkcemu.base.ROMFileSettingsFld;
import jkcemu.base.SettingsFrm;
import jkcemu.base.UserCancelException;
import jkcemu.base.UserInputException;
import jkcemu.disk.GIDESettingsFld;
import jkcemu.emusys.NANOS;


public class NANOSSettingsFld extends AbstractSettingsFld
{
  private JTabbedPane          tabbedPane;
  private JPanel               tabGraphic;
  private JRadioButton         btnGraphic64x32;
  private JRadioButton         btnGraphic80x24;
  private JRadioButton         btnGraphic80x25;
  private JRadioButton         btnGraphicPoppe;
  private JPanel               tabKeyboard;
  private JRadioButton         btnKbPio00Ahs;
  private JRadioButton         btnKbPio00Abit7;
  private JRadioButton         btnKbSio84A;
  private JCheckBox            btnKbSwapCase;
  private JPanel               tabRom;
  private JRadioButton         btnRomNanos;
  private JRadioButton         btnRomEpos;
  private JRadioButton         btnRomFile;
  private FileNameFld          fldRomFile;
  private JButton              btnRomFileSelect;
  private JButton              btnRomFileRemove;
  private GIDESettingsFld      tabGIDE;
  private JPanel               tabExt;
  private JCheckBox            btnKCNet;
  private JCheckBox            btnVDIP;
  private ROMFileSettingsFld   fldAltFont8x6;
  private ROMFileSettingsFld   fldAltFont8x8;
  private AutoInputSettingsFld tabAutoInput;


  public NANOSSettingsFld( SettingsFrm settingsFrm, String propPrefix )
  {
    super( settingsFrm, propPrefix );

    setLayout( new BorderLayout() );

    this.tabbedPane = new JTabbedPane( JTabbedPane.TOP );
    add( this.tabbedPane, BorderLayout.CENTER );


    // Tab Grafik
    this.tabGraphic = new JPanel( new GridBagLayout() );
    this.tabbedPane.addTab( "Grafik", this.tabGraphic );

    GridBagConstraints gbcGraphic = new GridBagConstraints(
					0, 0,
					1, 1,
					0.0, 0.0,
					GridBagConstraints.WEST,
					GridBagConstraints.NONE,
					new Insets( 5, 5, 0, 5 ),
					0, 0 );

    this.tabGraphic.add( new JLabel( "Grafikkarte:" ), gbcGraphic );

    ButtonGroup grpGraphic = new ButtonGroup();

    this.btnGraphic64x32 = new JRadioButton(
	"Bildschirmsteuerung Video 2 mit 64x32 Zeichen" );
    grpGraphic.add( this.btnGraphic64x32 );
    gbcGraphic.insets.top  = 0;
    gbcGraphic.insets.left = 50;
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.btnGraphic64x32, gbcGraphic );

    this.btnGraphic80x24 = new JRadioButton(
	"Bildschirmsteuerung Video 3 mit 80x24 Zeichen" );
    grpGraphic.add( this.btnGraphic80x24 );
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.btnGraphic80x24, gbcGraphic );

    this.btnGraphic80x25 = new JRadioButton(
	"Bildschirmsteuerung Video 3 mit 80x25 Zeichen" );
    grpGraphic.add( this.btnGraphic80x25 );
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.btnGraphic80x25, gbcGraphic );

    this.btnGraphicPoppe = new JRadioButton(
	"Farbgrafikkarte mit 64x32 und 80x24 Zeichen umschaltbar" );
    grpGraphic.add( this.btnGraphicPoppe );
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.btnGraphicPoppe, gbcGraphic );

    gbcGraphic.fill        = GridBagConstraints.HORIZONTAL;
    gbcGraphic.weightx     = 1.0;
    gbcGraphic.insets.top  = 10;
    gbcGraphic.insets.left = 5;
    gbcGraphic.gridy++;
    this.tabGraphic.add( new JSeparator(), gbcGraphic );

    this.fldAltFont8x8 = new ROMFileSettingsFld(
				settingsFrm,
				propPrefix + NANOS.PROP_FONT_8X8_PREFIX,
				"Alternativer Zeichensatz (8x8):" );
    gbcGraphic.insets.top = 10;
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.fldAltFont8x8, gbcGraphic );

    this.fldAltFont8x6 = new ROMFileSettingsFld(
		settingsFrm,
		propPrefix + NANOS.PROP_FONT_8X6_PREFIX,
		"Alternativer 8x6-Zeichensatz"
			+ " f\u00FCr 64x32-Modus der Farbgrafikkarte:" );
    gbcGraphic.insets.bottom = 5;
    gbcGraphic.gridy++;
    this.tabGraphic.add( this.fldAltFont8x6, gbcGraphic );


    // Tab Tastatur
    this.tabKeyboard = new JPanel( new GridBagLayout() );
    this.tabbedPane.addTab( "Tastatur", this.tabKeyboard );

    GridBagConstraints gbcKeyboard = new GridBagConstraints(
					0, 0,
					1, 1,
					0.0, 0.0,
					GridBagConstraints.WEST,
					GridBagConstraints.NONE,
					new Insets( 5, 5, 0, 5 ),
					0, 0 );

    this.tabKeyboard.add(
		new JLabel( "Tastatur ist angeschlossen an:" ),
		gbcKeyboard );

    ButtonGroup grpKeyboard = new ButtonGroup();

    this.btnKbPio00Ahs = new JRadioButton(
		"ZRE-PIO Port A mit Ready/Strobe-Handshake (NANOS 2.2)",
		true );
    grpKeyboard.add( this.btnKbPio00Ahs );
    gbcKeyboard.insets.top  = 0;
    gbcKeyboard.insets.left = 50;
    gbcKeyboard.gridy++;
    this.tabKeyboard.add( this.btnKbPio00Ahs, gbcKeyboard );

    this.btnKbPio00Abit7 = new JRadioButton(
		"ZRE-PIO Port A mit Strobe an Bit 7 (EPOS 2.1)" );
    grpKeyboard.add( this.btnKbPio00Abit7 );
    gbcKeyboard.gridy++;
    this.tabKeyboard.add( this.btnKbPio00Abit7, gbcKeyboard );

    this.btnKbSio84A = new JRadioButton( "IO-Karte SIO Port A" );
    grpKeyboard.add( this.btnKbSio84A );
    gbcKeyboard.gridy++;
    this.tabKeyboard.add( this.btnKbSio84A, gbcKeyboard );

    this.btnKbSwapCase = new JCheckBox(
		"Gro\u00DF-/Kleinschreibung umkehren" );
    gbcKeyboard.insets.top    = 10;
    gbcKeyboard.insets.left   = 5;
    gbcKeyboard.insets.bottom = 5;
    gbcKeyboard.gridy++;
    this.tabKeyboard.add( this.btnKbSwapCase, gbcKeyboard );


    // Tab ROM
    this.tabRom = new JPanel( new GridBagLayout() );
    this.tabbedPane.addTab( "ROM", this.tabRom );

    GridBagConstraints gbcRom = new GridBagConstraints(
					0, 0,
					GridBagConstraints.REMAINDER, 1,
					1.0, 0.0,
					GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL,
					new Insets( 5, 5, 0, 5 ),
					0, 0 );

    ButtonGroup grpRom = new ButtonGroup();

    this.btnRomNanos = new JRadioButton(
				"Boot-ROM f\u00FCr NANOS 2.2",
				true );
    grpRom.add( this.btnRomNanos );
    this.tabRom.add( this.btnRomNanos, gbcRom );

    this.btnRomEpos = new JRadioButton( "Boot-ROM f\u00FCr EPOS 2.1" );
    grpRom.add( this.btnRomEpos );
    gbcRom.insets.top = 0;
    gbcRom.gridy++;
    this.tabRom.add( this.btnRomEpos, gbcRom );

    this.btnRomFile = new JRadioButton( "ROM-Datei:" );
    grpRom.add( this.btnRomFile );
    gbcRom.gridy++;
    this.tabRom.add( this.btnRomFile, gbcRom );

    this.fldRomFile      = new FileNameFld();
    gbcRom.insets.bottom = 5;
    gbcRom.insets.left   = 50;
    gbcRom.gridwidth     = 1;
    gbcRom.gridy++;
    this.tabRom.add( this.fldRomFile, gbcRom );

    this.btnRomFileSelect = createImageButton(
					"/images/file/open.png",
					"ROM-Datei ausw\u00E4hlen" );
    gbcRom.fill        = GridBagConstraints.NONE;
    gbcRom.weightx     = 0.0;
    gbcRom.insets.left = 0;
    gbcRom.gridx++;
    this.tabRom.add( this.btnRomFileSelect, gbcRom );

    this.btnRomFileRemove = createImageButton(
					"/images/file/delete.png",
					"ROM-Datei entfernen" );
    gbcRom.gridx++;
    this.tabRom.add( this.btnRomFileRemove, gbcRom );


    // Tab GIDE
    this.tabGIDE = new GIDESettingsFld( settingsFrm, propPrefix );
    this.tabbedPane.addTab( "GIDE", this.tabGIDE );


    // Tab Erweiterungen
    this.tabExt = new JPanel( new GridBagLayout() );
    this.tabbedPane.addTab( "Erweiterungen", this.tabExt );

    GridBagConstraints gbcExt = new GridBagConstraints(
					0, 0,
					GridBagConstraints.REMAINDER, 1,
					0.0, 0.0,
					GridBagConstraints.WEST,
					GridBagConstraints.NONE,
					new Insets( 5, 5, 0, 5 ),
					0, 0 );

    this.btnKCNet = new JCheckBox( "KCNet-kompatible Netzwerkkarte" );
    gbcExt.gridy++;
    this.tabExt.add( this.btnKCNet, gbcExt );

    this.btnVDIP = new JCheckBox( "USB-Anschluss (Vinculum VDIP Modul)" );
    gbcExt.insets.top    = 0;
    gbcExt.insets.bottom = 5;
    gbcExt.gridy++;
    this.tabExt.add( this.btnVDIP, gbcExt );


    // Tab AutoInput
    this.tabAutoInput = new AutoInputSettingsFld(
				settingsFrm,
				propPrefix,
				NANOS.DEFAULT_SWAP_KEY_CHAR_CASE,
				NANOS.DEFAULT_PROMPT_AFTER_RESET_MILLIS_MAX );
    this.tabbedPane.addTab( "AutoInput", this.tabAutoInput );


    // Listener
    this.btnGraphic64x32.addActionListener( this );
    this.btnGraphic80x24.addActionListener( this );
    this.btnGraphic80x25.addActionListener( this );
    this.btnGraphicPoppe.addActionListener( this );
    this.btnKbPio00Ahs.addActionListener( this );
    this.btnKbPio00Abit7.addActionListener( this );
    this.btnKbSio84A.addActionListener( this );
    this.btnKbSwapCase.addActionListener( this );
    this.btnRomNanos.addActionListener( this );
    this.btnRomEpos.addActionListener( this );
    this.btnRomFile.addActionListener( this );
    this.btnKCNet.addActionListener( this );
    this.btnVDIP.addActionListener( this );


    // Sonstiges
    updGraphicDependFields();
    updRomDependFields();
  }


	/* --- ueberschriebene Methoden --- */

  @Override
  public void applyInput(
		Properties props,
		boolean    selected ) throws
					UserCancelException,
					UserInputException
  {
    Component tab  = null;
    String    text = null;
    try {

      // Tab Grafik
      tab  = this.tabGraphic;
      text = NANOS.VALUE_GRAPHIC_80X25;
      if( this.btnGraphic64x32.isSelected() ) {
	text = NANOS.VALUE_GRAPHIC_64X32;
      } else if( this.btnGraphic80x24.isSelected() ) {
	text = NANOS.VALUE_GRAPHIC_80X24;
      } else if( this.btnGraphicPoppe.isSelected() ) {
	text = NANOS.VALUE_GRAPHIC_POPPE;
      }
      EmuUtil.setProperty(
		props,
		this.propPrefix + NANOS.PROP_GRAPHIC,
		text );
      this.fldAltFont8x8.applyInput( props, selected );
      this.fldAltFont8x6.applyInput( props, selected );

      // Tab Tastatur
      tab  = this.tabKeyboard;
      text = NANOS.VALUE_KEYBOARD_PIO00A_HS;
      if( this.btnKbPio00Abit7.isSelected() ) {
	text = NANOS.VALUE_KEYBOARD_PIO00A_BIT7;
      } else if( this.btnKbSio84A.isSelected() ) {
	text = NANOS.VALUE_KEYBOARD_SIO84A;
      }
      EmuUtil.setProperty(
		props,
		this.propPrefix + NANOS.PROP_KEYBOARD,
		text );
      EmuUtil.setProperty(
		props,
		this.propPrefix + NANOS.PROP_KEYBOARD_SWAP_CASE,
		this.btnKbSwapCase.isSelected() );

      // Tab ROM
      tab  = this.tabRom;
      text = NANOS.VALUE_NANOS;
      if( this.btnRomEpos.isSelected() ) {
	text = NANOS.VALUE_EPOS;
      } else if( this.btnRomFile.isSelected() ) {
	File file = this.fldRomFile.getFile();
	if( file == null ) {
	  if( selected ) {
	    this.tabbedPane.setSelectedComponent( this.tabRom );
	    throw new UserInputException(
		"ROM: Bitte w\u00E4hlen Sie eine ROM-Datei aus\n"
			+ "oder stellen Sie einen anderen Boot-ROM ein." );
	  }
	}
	if( file != null ) {
	  text = NANOS.VALUE_PREFIX_FILE + file.getPath();
	}
      }
      EmuUtil.setProperty( props, this.propPrefix + NANOS.PROP_ROM, text );

      // Tab GIDE
      tab = this.tabGIDE;
      this.tabGIDE.applyInput( props, selected );

      // Tab Erweiterungen
      tab = this.tabExt;
      EmuUtil.setProperty(
		props,
		this.propPrefix + NANOS.PROP_KCNET_ENABLED,
		this.btnKCNet.isSelected() );
      EmuUtil.setProperty(
		props,
		this.propPrefix + NANOS.PROP_VDIP_ENABLED,
		this.btnVDIP.isSelected() );

      // Tab AutoInput
      tab = this.tabAutoInput;
      this.tabAutoInput.applyInput( props, selected );
    }
    catch( UserInputException ex ) {
      if( tab != null ) {
	this.tabbedPane.setSelectedComponent( tab );
      }
      throw ex;
    }
  }


  @Override
  protected boolean doAction( EventObject e )
  {
    this.settingsFrm.setWaitCursor( true );

    boolean rv  = false;
    Object  src = e.getSource();
    if( src != null ) {
      rv = this.tabGIDE.doAction( e );
      if( !rv ) {
	rv = this.tabAutoInput.doAction( e );
      }
      if( !rv ) {
	if( (src == this.btnGraphic64x32)
	    || (src == this.btnGraphic80x24)
	    || (src == this.btnGraphic80x25)
	    || (src == this.btnGraphicPoppe) )
	{
	  updGraphicDependFields();
	  fireDataChanged();
	  rv = true;
	}
	else if( (src == this.btnRomNanos)
		 || (src == this.btnRomEpos)
		 || (src == this.btnRomFile) )
	{
	  updRomDependFields();
	  fireDataChanged();
	  rv = true;
	}
	else if( src == this.btnRomFileSelect ) {
	  File file = selectFile(
				"ROM-Datei ausw\u00E4hlen",
				Main.FILE_GROUP_ROM,
				this.fldRomFile.getFile(),
				EmuUtil.getROMFileFilter() );
	  if( file != null ) {
	    if( this.fldRomFile.setFile( file ) ) {
	      this.btnRomFileRemove.setEnabled( true );
	      fireDataChanged();
	    }
	  }
	  rv = true;
	}
	else if( src == this.btnRomFileRemove ) {
	  if( this.fldRomFile.setFile( null ) ) {
	    this.btnRomFileRemove.setEnabled( false );
	    fireDataChanged();
	  }
	  rv = true;
	}
	else if( src instanceof AbstractButton ) {
	  fireDataChanged();
	  rv = true;
	}
      }
    }
    this.settingsFrm.setWaitCursor( false );
    return rv;
  }


  @Override
  public void lookAndFeelChanged()
  {
    this.fldAltFont8x8.lookAndFeelChanged();
    this.fldAltFont8x6.lookAndFeelChanged();
    this.tabGIDE.lookAndFeelChanged();
    this.tabAutoInput.lookAndFeelChanged();
  }


  @Override
  public void updFields( Properties props )
  {
    switch( EmuUtil.getProperty(
		props,
		this.propPrefix + NANOS.PROP_GRAPHIC ) )
    {
      case NANOS.VALUE_GRAPHIC_64X32:
	this.btnGraphic64x32.setSelected( true );
	break;
      case NANOS.VALUE_GRAPHIC_80X24:
	this.btnGraphic80x24.setSelected( true );
	break;
      case NANOS.VALUE_GRAPHIC_POPPE:
	this.btnGraphicPoppe.setSelected( true );
	break;
      default:
	this.btnGraphic80x25.setSelected( true );
    }
    this.fldAltFont8x8.updFields( props );
    this.fldAltFont8x6.updFields( props );
    switch( EmuUtil.getProperty(
		props,
		this.propPrefix + NANOS.PROP_KEYBOARD ) )
    {
      case NANOS.VALUE_KEYBOARD_PIO00A_BIT7:
	this.btnKbPio00Abit7.setSelected( true );
	break;
      case NANOS.VALUE_KEYBOARD_SIO84A:
	this.btnKbSio84A.setSelected( true );
	break;
      default:
	this.btnKbPio00Ahs.setSelected( true );
    }
    this.btnKbSwapCase.setSelected(
		EmuUtil.getBooleanProperty(
			props,
			this.propPrefix + NANOS.PROP_KEYBOARD_SWAP_CASE,
			false ) );
    String valueText = EmuUtil.getProperty(
				props,
				this.propPrefix + NANOS.PROP_ROM );
    String lowerText = valueText.toLowerCase();
    if( (lowerText.length() > NANOS.VALUE_PREFIX_FILE.length())
	 && lowerText.startsWith( NANOS.VALUE_PREFIX_FILE ) )
    {
      this.btnRomFile.setSelected( true );
      this.fldRomFile.setFileName( valueText.substring( 5 ) );
    } else if( lowerText.equalsIgnoreCase( NANOS.VALUE_EPOS ) ) {
      this.btnRomEpos.setSelected( true );
    } else {
      this.btnRomNanos.setSelected( true );
    }
    this.btnKCNet.setSelected(
		EmuUtil.getBooleanProperty(
			props,
			this.propPrefix + NANOS.PROP_KCNET_ENABLED,
			false ) );
    this.btnVDIP.setSelected(
		EmuUtil.getBooleanProperty(
			props,
			this.propPrefix + NANOS.PROP_VDIP_ENABLED,
			false ) );
    this.tabGIDE.updFields( props );
    this.tabAutoInput.updFields( props );
    updGraphicDependFields();
    updRomDependFields();
  }


  private void updGraphicDependFields()
  {
    this.fldAltFont8x6.setEnabled( this.btnGraphicPoppe.isSelected() );
  }


  private void updRomDependFields()
  {
    boolean stateFile = this.btnRomFile.isSelected();
    this.fldRomFile.setEnabled( stateFile );
    this.btnRomFileSelect.setEnabled( stateFile );
    this.btnRomFileRemove.setEnabled(
		stateFile && (this.fldRomFile.getFile() != null) );
  }
}
