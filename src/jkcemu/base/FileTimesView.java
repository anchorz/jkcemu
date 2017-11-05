/*
 * (c) 2015 Jens Mueller
 *
 * Kleincomputer-Emulator
 *
 * Sicht auf die Zeitstempel einer Datei
 */

package jkcemu.base;



public interface FileTimesView
{
  public Long getCreationMillis();
  public Long getLastAccessMillis();
  public Long getLastModifiedMillis();
  public void setTimesInMillis(
			Long creationMillis,
			Long lastAccessMillis,
			Long lastModifiedMillis );
};
