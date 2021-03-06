/*
 * (c) 2009-2016 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Abstrakter Dialog fuer Aktionen,
 * die in einem separaten Thread laufen
 */

package jkcemu.base;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jkcemu.Main;


public abstract class AbstractThreadDlg extends BaseDlg implements Runnable
{
  protected boolean cancelled;
  protected int     errorCount;

  private Thread       thread;
  private boolean      autoClose;
  private JTextArea    fldLog;
  private JProgressBar progressBar;
  private JButton      btnClose;


  protected AbstractThreadDlg(
			Window  owner,
			String  threadName,
			boolean withProgressBar )
  {
    super( owner, Dialog.ModalityType.MODELESS );
    this.autoClose  = true;
    this.cancelled  = false;
    this.errorCount = 0;
    this.thread     = new Thread( Main.getThreadGroup(), this, threadName );


    // Fensterinhalt
    setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints(
					0, 0,
					1, 1,
					1.0, 1.0,
					GridBagConstraints.CENTER,
					GridBagConstraints.BOTH,
					new Insets( 5, 5, 5, 5 ),
					0, 0 );

    this.fldLog = new JTextArea( 10, 32 );
    this.fldLog.setEditable( false );
    add(
	new JScrollPane(
		this.fldLog,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ),
	gbc );

    if( withProgressBar ) {
      this.progressBar = new JProgressBar( JProgressBar.HORIZONTAL );
      this.progressBar.setBorderPainted( true );
      this.progressBar.setStringPainted( false );
      gbc.fill    = GridBagConstraints.HORIZONTAL;
      gbc.weighty = 0.0;
      gbc.gridy++;
      add( this.progressBar, gbc );
    } else {
      this.progressBar = null;
    }

    this.btnClose = new JButton( "Abbrechen" );
    this.btnClose.addActionListener( this );
    gbc.fill    = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.gridy++;
    add( this.btnClose, gbc );


    // Fenstergroesse und -position
    pack();
    setParentCentered();
    this.fldLog.setColumns( 0 );
    this.fldLog.setRows( 0 );


    // Starten des Threads veranlassen
    final Thread thread = this.thread;
    EventQueue.invokeLater(
		new Runnable()
		{
		  @Override
		  public void run()
		  {
		    thread.start();
		  }
		} );
  }


  protected void appendErrorToLog( Object errObj )
  {
    StringBuilder buf = new StringBuilder( 128 );
    buf.append( "  Fehler" );
    if( errObj != null ) {
      String errMsg = null;
      if( errObj instanceof Exception ) {
	errMsg = ((Exception) errObj).getMessage();
      } else {
	errMsg = errObj.toString();
      }
      if( errMsg != null ) {
	if( !errMsg.isEmpty() ) {
	  buf.append( ": " );
	  buf.append( errMsg );
	}
      }
    }
    buf.append( (char) '\n' );
    appendToLog( buf.toString() );
    this.autoClose = false;
  }


  protected void appendIgnoredToLog()
  {
    appendToLog( " Ignoriert\n" );
    this.autoClose = false;
  }


  protected void appendToLog( final String msg )
  {
    if( !this.cancelled ) {
      final JTextArea fld = this.fldLog;
      EventQueue.invokeLater(
		new Runnable()
		{
		  @Override
		  public void run()
		  {
		    fld.append( msg );
		  }
		} );
		    
    }
  }


  protected void disableAutoClose()
  {
    this.autoClose = false;
  }


  protected abstract void doProgress();


  protected void incErrorCount()
  {
    this.errorCount++;
  }


  /*
   * Die Methode oeffnet eine Datei zum Lesen.
   * Das Argument "nReads" gibt an, wie oft die Datei gelesen werden soll.
   * Das ist wichtig fuer die korrekte Funktion des Fortschrittbalkens.
   */
  protected FileProgressInputStream openInputFile(
					File file,
					int  nReads ) throws IOException
  {
    return new FileProgressInputStream( file, this.progressBar, nReads );
  }


	/* --- Runnable --- */

  @Override
  public void run()
  {
    doProgress();
    EventQueue.invokeLater(
		new Runnable()
		{
		  @Override
		  public void run()
		  {
		    progressFinished();
		  }
		} );
  }


	/* --- ueberschriebene Methoden --- */

  @Override
  public boolean doAction( EventObject e )
  {
    boolean matched = false;
    if( e.getSource() == this.btnClose ) {
      matched        = true;
      this.cancelled = true;
      if( !this.thread.isAlive() ) {
	doClose();
      }
    }
    return matched;
  }


	/* --- private Methoden --- */

  private void progressFinished()
  {
    this.btnClose.setText( "Schlie\u00DFen" );
    if( !this.cancelled && (this.errorCount > 0) ) {
      if( this.progressBar != null ) {
	this.progressBar.setMinimum( 0 );
	this.progressBar.setMaximum( 1 );
	this.progressBar.setValue( 0 );
      }
      this.fldLog.append( "\n" );
      this.fldLog.append( Integer.toString( this.errorCount ) );
      this.fldLog.append( " Fehler\n" );
    } else {
      if( this.autoClose ) {
	doClose();
      }
    }
  }
}
