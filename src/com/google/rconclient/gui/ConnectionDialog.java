/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class will show a dialog for asking the properties of a connection.
 * 
 * @author vincent
 * 
 */
public class ConnectionDialog {

	/**
	 * The preference key used for the host.
	 */
	private static final String PREF_KEY_HOST = "host";

	/**
	 * The preference key used for the port.
	 */
	private static final String PREF_KEY_PORT = "port";

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(ConnectionDialog.class);

	private static final String MSG_CLASS_NAME = ConnectionDialog.class.getSimpleName();
	private static final String MSG_HOST = MSG_CLASS_NAME + ".host";
	private static final String MSG_PORT = MSG_CLASS_NAME + ".port";
	private static final String MSG_PASSWORD = MSG_CLASS_NAME + ".password";

	/**
	 * The {@link JDialog} of this connection dialog.
	 */
	private final JDialog dialog;

	/**
	 * The pane of the dialog.
	 */
	private final JOptionPane pane;

	/**
	 * The text field for the host name.
	 */
	private final JTextField hostField;

	/**
	 * The text field for the port number.
	 */
	private final JTextField portField;

	/**
	 * The field for the password.
	 */
	private final JPasswordField passwordField;

	/**
	 * Create a new connection dialog. The component will be used as a reference
	 * for its position.
	 * 
	 * @param component
	 *            The component.
	 */
	public ConnectionDialog(final JComponent component) {
		super();
		// The panel
		final JPanel panel = new JPanel(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(2, 2, 2, 2);

		// The host label and field
		final JLabel hostLabel = new JLabel(MESSAGES.getString(MSG_HOST));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.LINE_END;
		panel.add(hostLabel, constraints);

		hostField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.LINE_START;
		panel.add(hostField, constraints);

		// The port label and field.
		final JLabel portLable = new JLabel(MESSAGES.getString(MSG_PORT));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.LINE_END;
		panel.add(portLable, constraints);

		portField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.LINE_START;
		panel.add(portField, constraints);

		// The password label and field.
		final JLabel passwordLable = new JLabel(MESSAGES.getString(MSG_PASSWORD));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.LINE_END;
		panel.add(passwordLable, constraints);

		passwordField = new JPasswordField(20);
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.LINE_START;
		panel.add(passwordField, constraints);

		// Create the dialog itself.
		pane = new JOptionPane(panel);
		pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		dialog = pane.createDialog(component, "Title");
		dialog.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(final WindowEvent e) {
				if (hostField.getText().isEmpty()) {
					hostField.requestFocusInWindow();
				} else if (portField.getText().isEmpty()) {
					portField.requestFocusInWindow();
				} else {
					passwordField.requestFocusInWindow();
				}
			}

			@Override
			public void windowLostFocus(final WindowEvent e) {
			}
		});
	}

	/**
	 * Get the hostname for the connection.
	 * 
	 * @return The hostname.
	 */
	public String getHostname() {
		return hostField.getText();
	}

	/**
	 * Show the dialog and wait for the user to close the dialog.
	 * 
	 * @return The option that was used to close the dialog.
	 */
	public int getOption() {
		final Preferences preferences = Preferences.userNodeForPackage(this.getClass());
		hostField.setText(preferences.get(PREF_KEY_HOST, ""));
		portField.setText(Integer.toString(preferences.getInt(PREF_KEY_PORT, 25575)));
		dialog.setVisible(true);
		final Object selectedValue = pane.getValue();
		if (selectedValue == null) {
			return JOptionPane.CLOSED_OPTION;
		} else if (selectedValue instanceof Integer) {
			final Integer selectedInteger = (Integer) selectedValue;
			switch (selectedInteger) {
			case JOptionPane.OK_OPTION:
				preferences.put(PREF_KEY_HOST, hostField.getText());
				preferences.putInt(PREF_KEY_PORT, Integer.parseInt(portField.getText()));
				return selectedInteger.intValue();

			case JOptionPane.CANCEL_OPTION:
			case JOptionPane.CLOSED_OPTION:
				return selectedInteger.intValue();

			default:
				assert false : selectedInteger;
				break;
			}
			return selectedInteger.intValue();
		}
		assert false : selectedValue;
		return JOptionPane.CLOSED_OPTION;
	}

	/**
	 * Get the password for the connection.
	 * 
	 * @return The password.
	 */
	public char[] getPassword() {
		return passwordField.getPassword();
	}

	/**
	 * Get the port for the connection.
	 * 
	 * @return The port.
	 */
	public int getPort() {
		return Integer.parseInt(portField.getText());
	}

}
