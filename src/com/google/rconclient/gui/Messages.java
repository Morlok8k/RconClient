/**
 * 
 */
package com.google.rconclient.gui;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * This class gives access to the messages for a package.
 * 
 * @author vincent
 * 
 */
public class Messages extends ResourceBundle {

	/**
	 * The real resource bundle with the messages.
	 */
	private final ResourceBundle MESSAGES;

	/**
	 * Get a resource bundle to access the resources in Messages within the same
	 * package as the class.
	 */
	public Messages(final Class<?> clazz) {
		super();
		final String packageName = clazz.getPackage().getName();
		final String dirName = packageName.replace('.', '/');
		MESSAGES = ResourceBundle.getBundle(dirName + "/Messages");
	}

	@Override
	public Enumeration<String> getKeys() {
		return MESSAGES.getKeys();
	}

	@Override
	protected Object handleGetObject(final String key) {
		return MESSAGES.getObject(key);
	}

}
