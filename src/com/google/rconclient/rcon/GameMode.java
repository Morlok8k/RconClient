/**
 * 
 */
package com.google.rconclient.rcon;

/**
 * The game mode for a player.
 * 
 * @author vincent
 * 
 */
public enum GameMode {

	Survival(0), Creative(1);

	/**
	 * The internal number of this mode.
	 */
	private final int number;

	/**
	 * Create a new instance of this object and initialize it;
	 * 
	 * @param number
	 *            The internal number.
	 */
	private GameMode(final int number) {
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

}
