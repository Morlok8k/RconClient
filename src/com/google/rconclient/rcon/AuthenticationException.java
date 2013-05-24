/**
 * 
 */
package com.google.rconclient.rcon;

/**
 * Thrown when the authentication failed.
 * 
 * @author vincent
 * 
 */
public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message. The cause is
	 * not initialized.
	 */
	public AuthenticationException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized.
	 * 
	 * @param message
	 *            The detail message.
	 */
	public AuthenticationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 *            The detail message.
	 * @param cause
	 *            The cause.
	 */
	public AuthenticationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 * 
	 * @param message
	 *            The detail message.
	 * @param cause
	 *            The cause.
	 * @param enableSuppression
	 *            Whether or not suppression is enabled or disabled.
	 * @param writableStackTrace
	 *            Whether or not the stack trace should be writable.
	 */
	public AuthenticationException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of (cause==null ? null : cause.toString()) (which typically contains the
	 * class and detail message of cause).
	 * 
	 * @param cause
	 *            The cause.
	 */
	public AuthenticationException(final Throwable cause) {
		super(cause);
	}

}
