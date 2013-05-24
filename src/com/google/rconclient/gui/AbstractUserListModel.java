/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.RCon;

/**
 * A list model that holds the current user list.
 * 
 * @author vincent
 * 
 */
public abstract class AbstractUserListModel extends AbstractListModel<String> {

	/**
	 * The worker class that will get the list of users from the server.
	 * 
	 * @author vincent
	 * 
	 */
	private class ListWorker extends SwingWorker<String[], Void> {

		@Override
		protected String[] doInBackground() throws Exception {
			final RCon connection = globals.getConnection();
			final String[] users = getList(connection);
			return users;
		}

		@Override
		protected void done() {
			super.done();

			try {
				final String[] users = get();
				update(users);
			} catch (final InterruptedException e) {
				JOptionPane.showMessageDialog(globals.getFrame(),
						MESSAGES.getString(MSG_INTERRUPTED_MESSAGE),
						MESSAGES.getString(MSG_INTERRUPTED_TITLE),
						JOptionPane.ERROR_MESSAGE);
			} catch (final ExecutionException e) {
				try {

					if (globals.getConnection() != null) {
						globals.getConnection().close();
					}
					globals.setConnection(null);
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
				if (globals.getConnection() != null) {
					JOptionPane.showMessageDialog(globals.getFrame(),
							MESSAGES.getString(MSG_EXCEPTION_MESSAGE),
							MESSAGES.getString(MSG_EXCEPTION_TITLE),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	/**
	 * The listener class that will get triggered after every timer timeout. It
	 * will start a background worker to update the list of users.
	 * 
	 * @author vincent
	 * 
	 */
	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent event) {
			final ListWorker listWorker = new ListWorker();
			listWorker.execute();
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(
			AbstractUserListModel.class);

	private static final String MSG_CLASS_NAME = AbstractUserListModel.class
			.getSimpleName();

	private static final String MSG_INTERRUPTED_TITLE = MSG_CLASS_NAME
			+ ".interrupted.title";
	private static final String MSG_INTERRUPTED_MESSAGE = MSG_CLASS_NAME
			+ ".interrupted.message";
	private static final String MSG_EXCEPTION_TITLE = MSG_CLASS_NAME
			+ ".exception.title";
	private static final String MSG_EXCEPTION_MESSAGE = MSG_CLASS_NAME
			+ ".exception.message";
	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * The sorted array with the users.
	 */
	private String[] users;

	/**
	 * Construct a new instance.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public AbstractUserListModel(final Globals globals) {
		super();
		this.globals = globals;
		users = new String[0];
		final Timer timer = new Timer(0, new TimerListener());
		timer.setRepeats(true);
		timer.setDelay(5000);

		globals.addConnectionListener(new ConnectionListener() {

			@Override
			public void connected(final RCon connection) {
				timer.start();
			}

			@Override
			public void disconnected(final RCon connection) {
				timer.stop();
				update(new String[0]);
			}

			@Override
			public void refresh(final RCon connection) {
				if (timer.isRunning()) {
					timer.restart();
				}
			}
		});
	}

	@Override
	public String getElementAt(final int index) {
		return users[index];
	}

	@Override
	public int getSize() {
		return users.length;
	}

	/**
	 * Update the list of users and trigger the front end about which elements
	 * have been added/changed/removed.
	 * 
	 * @param users
	 *            The new list of users.
	 */
	private void update(final String[] users) {
		// final String[] oldUsers = this.users;
		Arrays.sort(users);
		this.users = users;
		fireContentsChanged(this, 0, getSize() - 1);
	}

	protected abstract String[] getList(RCon connection) throws IOException,
			AuthenticationException;

}
