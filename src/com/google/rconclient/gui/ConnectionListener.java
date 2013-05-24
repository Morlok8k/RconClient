/**
 * 
 */
package com.google.rconclient.gui;

import java.util.EventListener;

import com.google.rconclient.rcon.RCon;

/**
 * The connection listener gets informed about the state change of the
 * connection.
 * 
 * @author vincent
 * 
 */
public interface ConnectionListener extends EventListener {

	/**
	 * There has been made a new connection.
	 * 
	 * @param connection
	 *            The new connection.
	 */
	void connected(RCon connection);

	/**
	 * The current connection has been disconnected.
	 * 
	 * @param connection
	 *            The old connection.
	 */
	void disconnected(RCon connection);

	/**
	 * Refresh the information from the connection.
	 * 
	 * @param connection
	 *            The current connection.
	 */
	void refresh(RCon connection);

}
