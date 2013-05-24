/**
 * 
 */
package com.google.rconclient;

import com.google.rconclient.gui.GUI;

/**
 * This is the main class for the RCon client. A remote console for the
 * MineCraft server.
 * 
 * MineCraft is a multi user game where there is a central server containing a
 * virtual world. The game player use a client to connect to the server and can
 * then join the game.
 * 
 * The server can be controlled by its local console. Typing commands gives the
 * ability to manage users and other server resources. Typing is however error
 * prune and this program solves that problem. The server provides an interface
 * for using a remote console. This program uses that interface and provides a
 * graphical interface to the console.
 * 
 * @author vincent
 * 
 */
public class RConClient {

	/**
	 * The main method which is called at program start.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static void main(final String[] args) {
		new GUI();
	}

}
