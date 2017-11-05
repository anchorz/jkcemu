/*
 * (c) 2008 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Exception eines Compilers/Assemblers
 */

package jkcemu.programming;



public class PrgException extends Exception
{
  private static final long serialVersionUID = 3131795926049776333L;
  public PrgException( String msg )
  {
    super( msg );
  }
}

