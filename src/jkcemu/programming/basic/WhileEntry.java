/*
 * (c) 2012-2014 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Struktureintrag fuer eine WHILE-Schleife
 */

package jkcemu.programming.basic;

import jkcemu.programming.PrgSource;


public class WhileEntry extends LoopEntry
{


  public WhileEntry(
		PrgSource source,
		long      basicLineNum,
		String    loopLabel,
		String    exitLabel )
  {
    super( source, basicLineNum, loopLabel, exitLabel );
  }


	/* --- ueberschriebene Methoden --- */

  @Override
  public String getLoopBegKeyword()
  {
    return "WHILE";
  }


  @Override
  public String toString()
  {
    return "WHILE-Schleife";
  }
}

