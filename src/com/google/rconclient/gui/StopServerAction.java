/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.RCon;

/**
 * The action to be used to stop the server.
 * 
 * @author vincent
 * 
 */
public class StopServerAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(StopServerAction.class);

	private static final String MSG_CLASS_NAME = StopServerAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";
	private static final String MSG_AUTH_EXCEPTION_TITLE = MSG_CLASS_NAME + ".authenticationException.title";
	private static final String MSG_AUTH_EXCEPTION_MESSAGE = MSG_CLASS_NAME + ".authenticationException.message";
	private static final String MSG_IOEXCEPTION_TITLE = MSG_CLASS_NAME + ".ioException.title";
	private static final String MSG_IOEXCEPTION_MESSAGE = MSG_CLASS_NAME + ".ioException.message";
	private static final String MSG_NO_CONNECTION_TITLE = MSG_CLASS_NAME + ".noConnection.title";
	private static final String MSG_NO_CONNECTION_MESSAGE = MSG_CLASS_NAME + ".noConnection.message";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public StopServerAction(final Globals globals) {
		super();
		this.globals = globals;
		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		final RCon connection = globals.getConnection();
		if (connection == null) {
			JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_NO_CONNECTION_MESSAGE),
					MESSAGES.getString(MSG_NO_CONNECTION_TITLE), JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				connection.stop();
				connection.close();
			} catch (final IOException e) {
				JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_IOEXCEPTION_MESSAGE),
						MESSAGES.getString(MSG_IOEXCEPTION_TITLE), JOptionPane.ERROR_MESSAGE);
			} catch (final AuthenticationException e) {
				JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_AUTH_EXCEPTION_MESSAGE),
						MESSAGES.getString(MSG_AUTH_EXCEPTION_TITLE), JOptionPane.ERROR_MESSAGE);
			} finally {
				globals.setConnection(null);
			}
		}
	}

}
