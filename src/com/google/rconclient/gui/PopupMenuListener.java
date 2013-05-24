/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A mouse listener to activate a popup menu.
 * 
 * @author vincent
 * 
 */
public class PopupMenuListener extends MouseAdapter {

	/**
	 * The popup menu.
	 */
	private final JPopupMenu popupMenu;

	/**
	 * A new listener will be created.
	 * 
	 * @param popupMenu
	 *            The popup menu to show.
	 */
	public PopupMenuListener(final JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	@Override
	public void mousePressed(final MouseEvent event) {
		maybeShowPopup(event);
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		maybeShowPopup(event);
	}

	/**
	 * Show the popup menu in case the event is a popup trigger event.
	 * 
	 * @param event
	 */
	private void maybeShowPopup(final MouseEvent event) {
		if (event.isPopupTrigger()) {
			popupMenu.show(event.getComponent(), event.getX(), event.getY());
		}
	}

}
