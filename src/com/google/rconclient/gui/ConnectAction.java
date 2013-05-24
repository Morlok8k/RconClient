/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.RCon;

/**
 * The action to be used when a connection to the RCon needs to be made.
 * 
 * @author vincent
 * 
 */
public class ConnectAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(ConnectAction.class);

	private static final String MSG_CLASS_NAME = ConnectAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";
	private static final String MSG_AUTH_EXCEPTION_TITLE = MSG_CLASS_NAME + ".authenticationException.title";
	private static final String MSG_AUTH_EXCEPTION_MESSAGE = MSG_CLASS_NAME + ".authenticationException.message";
	private static final String MSG_IOEXCEPTION_TITLE = MSG_CLASS_NAME + ".ioException.title";
	private static final String MSG_IOEXCEPTION_MESSAGE = MSG_CLASS_NAME + ".ioException.message";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * Create a new {@link ConnectAction}.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public ConnectAction(final Globals globals) {
		super();
		this.globals = globals;
		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		final ConnectionDialog dialog = new ConnectionDialog((JComponent) event.getSource());
		final int selectedValue = dialog.getOption();
		switch (selectedValue) {
		case JOptionPane.OK_OPTION:
			char[] password = null;
			try {
				RCon connection = globals.getConnection();
				if (connection != null) {
					connection.close();
				}
				final String host = dialog.getHostname();
				final int port = dialog.getPort();
				password = dialog.getPassword();
				connection = new RCon(host, port, password);
				Arrays.fill(password, 'x');
				globals.setConnection(connection);
			} catch (final IOException e) {
				if (password != null) {
					Arrays.fill(password, 'x');
				}
				JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_IOEXCEPTION_MESSAGE),
						MESSAGES.getString(MSG_IOEXCEPTION_TITLE), JOptionPane.ERROR_MESSAGE);
			} catch (final AuthenticationException e) {
				if (password != null) {
					Arrays.fill(password, 'x');
				}
				JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_AUTH_EXCEPTION_MESSAGE),
						MESSAGES.getString(MSG_AUTH_EXCEPTION_TITLE), JOptionPane.ERROR_MESSAGE);
			}
			break;

		case JOptionPane.CANCEL_OPTION:
		case JOptionPane.CLOSED_OPTION:
			break;

		default:
			assert false : selectedValue;
			break;
		}
	}
}
