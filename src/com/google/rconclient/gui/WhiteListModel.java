/**
 * 
 */
package com.google.rconclient.gui;

import java.io.IOException;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.RCon;

/**
 * @author vincent
 * 
 */
public class WhiteListModel extends AbstractUserListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param globals
	 */
	public WhiteListModel(final Globals globals) {
		super(globals);
	}

	@Override
	protected String[] getList(final RCon connection) throws IOException,
			AuthenticationException {
		final String[] users = connection != null ? connection.whitelist()
				: new String[0];
		return users;
	}

}
