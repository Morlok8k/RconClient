/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

/**
 * This class will provide some extra methods in relation to {@link KeyEvent}.
 * 
 * @author vincent
 * 
 */
public abstract class KeyEventUtil {

	/**
	 * Find the key code to the virtual key name.
	 * 
	 * @param name
	 *            The name of the virtual key.
	 * @return The key code.
	 */
	public static int getKeyCode(final String name) {
		if (name == null) {
			final NullPointerException exception = new NullPointerException("No name defined");
			throw exception;
		} else if (!name.startsWith("VK_")) {
			final IllegalArgumentException exception = new IllegalArgumentException("Illegal virtual key name: " + name);
			throw exception;
		}

		int code = KeyEvent.VK_UNDEFINED;
		try {
			final Field vkField = KeyEvent.class.getField(name);
			code = vkField.getInt(null);
		} catch (final SecurityException e) {
			final IllegalArgumentException exception = new IllegalArgumentException("No access allowed to virtual key name: "
					+ name, e);
			throw exception;
		} catch (final NoSuchFieldException e) {
			final IllegalArgumentException exception = new IllegalArgumentException("No such virtual key name: " + name, e);
			throw exception;
		} catch (final IllegalAccessException e) {
			final IllegalArgumentException exception = new IllegalArgumentException("Illegal access to virtual key name: " + name,
					e);
			throw exception;
		}

		return code;
	}

	/**
	 * No objects are allowed.
	 */
	private KeyEventUtil() {
		super();
	}

}
