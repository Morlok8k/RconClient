/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.Frame;
import java.util.HashSet;
import java.util.Set;

import com.google.rconclient.rcon.RCon;

/**
 * Container for global objects.
 * 
 * @author vincent
 * 
 */
public class Globals {

	/**
	 * The connection to the server.
	 */
	private RCon connection = null;

	/**
	 * The frame of the main application.
	 */
	private Frame frame = null;

	private final Set<ConnectionListener> connectionListeners = new HashSet<>();

	public void addConnectionListener(final ConnectionListener listener) {
		if (listener != null) {
			connectionListeners.add(listener);
		}
	}

	/**
	 * @return the connection
	 */
	public RCon getConnection() {
		return connection;
	}

	/**
	 * @return the frame
	 */
	public Frame getFrame() {
		return frame;
	}

	public void refreshConnection(final RCon connection) {
		if (connection != null) {
			fireRefresh(connection);
		}
	}

	public void removeConnectionListener(final ConnectionListener listener) {
		connectionListeners.remove(listener);
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(final RCon connection) {
		if (connection == null && this.connection != null) {
			fireDisconnected(this.connection);
		} else if (connection != null && this.connection == null) {
			fireConnected(connection);
		} else if (connection != this.connection) {
			fireDisconnected(this.connection);
			fireConnected(connection);
		}
		this.connection = connection;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(final Frame frame) {
		this.frame = frame;
	}

	/**
	 * @param connection
	 */
	private void fireConnected(final RCon connection) {
		for (final ConnectionListener connectionListener : connectionListeners) {
			connectionListener.connected(connection);
		}
	}

	/**
	 * @param connection
	 */
	private void fireDisconnected(final RCon connection) {
		for (final ConnectionListener connectionListener : connectionListeners) {
			connectionListener.disconnected(connection);
		}
	}

	/**
	 * @param connection
	 */
	private void fireRefresh(final RCon connection) {
		for (final ConnectionListener connectionListener : connectionListeners) {
			connectionListener.refresh(connection);
		}
	}

}
