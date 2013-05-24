/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

/**
 * The action to be used to start the server.
 * 
 * @author vincent
 * 
 */
public class StartServerAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(StartServerAction.class);

	private static final String MSG_CLASS_NAME = StartServerAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";

	/**
	 * The container with the global objects.
	 */
	@SuppressWarnings("unused")
	private final Globals globals;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public StartServerAction(final Globals globals) {
		super();
		this.globals = globals;
		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		// TODO
	}

}
