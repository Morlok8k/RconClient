package com.google.rconclient.cli;

public class Out {

		/**
		 * Outputs a formatted string to System.out as a line.
		 * 
		 * @param str
		 *            String to display and format
		 * @author Morlok8k
		 */
		public static void out(final String str) {
			System.out.println("[RCon Client] " + str);		// is there a better/easier way to do this?  I just wanted a lazier way to write "System.out.println(MLG + blah..."
		}
	
		/**
		 * Outputs a formatted string to System.err as a line.
		 * 
		 * @param str
		 *            String to display and format
		 * @author Morlok8k
		 */
		public static void err(final String str) {
			System.err.println("[RCon Client] " + str);
		}

		
}
