package com.google.rconclient.cli;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import com.google.rconclient.RConClient;
import com.google.rconclient.gui.Globals;
import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.IncorrectRequestIdException;
import com.google.rconclient.rcon.RCon;

/**
 * @author Morlok8k
 */
public class CLI {
	private final Globals globals;

	public CLI() {
		// TODO Auto-generated constructor stub
		super();
		globals = new Globals();
	}

	public void run(final String[] args) {
		// read args

		final String fileName = getFilename();

		if (args.length == 0) { // we didn't find a an X and Z size, so lets ask
								// for one.
			Out.err("RCon Client v" + globals.ver);
			Out.err("No switches were provided.");
			Out.err("Use the following example: java -jar "
					+ fileName
					+ " -host example.com -port 25575 -pass test -command \"stop\"");
			return;

		}

		if (args[0].equalsIgnoreCase("-version")
				|| args[0].equalsIgnoreCase("-help") || args[0].equals("/?")) {
			System.out.println("RCon Client v" + globals.ver);
			System.out.println("");
			System.out.println("Switches:  (All are required)");
			System.out
					.println("Long version:      | Short Version:  | Description:");
			System.out
					.println("-host example.com  | -h example.com  | Server Address");
			System.out
					.println("-port 25575        | -t 25575        | Server RCon Port");
			System.out
					.println("-pass test         | -p test         | Server RCon Password");
			System.out
					.println("-command \"stop\"    | -c \"stop\"       | Command to run. (must be quoted)");
			System.out.println("");
			System.out.println("Other Switches:");
			System.out
					.println("-version  -help  /?                  | Displays this message");
			System.out.println("");
			return;
		}

		String host = null;
		int port = -1; // 25575 is the default
		String pass = null;
		String command = null;

		try {
			for (int i = 0; i < (args.length); i++) {
				final String nextSwitch = args[i].toLowerCase();
				if (nextSwitch.equals("-host") || nextSwitch.equals("-h")) {
					host = args[i + 1].substring(0);
					Out.out("Host: " + host);

				} else if (nextSwitch.startsWith("-port")
						|| nextSwitch.startsWith("-t")) {
					port = Integer.parseInt(args[i + 1].substring(0));
					Out.out("Port: " + port);

				} else if (nextSwitch.startsWith("-pass")
						|| nextSwitch.startsWith("-p")) {
					pass = args[i + 1].substring(0);
					Out.out("Pass: " + pass);

				} else if (nextSwitch.startsWith("-command")
						|| nextSwitch.startsWith("-c")) {
					command = args[i + 1].substring(0);
					Out.out("Command: " + command);
				}
			}
		} catch (final NumberFormatException ex) {
			Out.err("Invalid switch value.");
			return;
		}

		// if something is missing, give error and quit
		if ((host == null) || (port == -1) || (pass == null)
				|| (command == null)) {
			Out.err("RCon Client v" + globals.ver);
			Out.err("Not all switches were provided.");
			Out.err("Use the following example: java -jar "
					+ fileName
					+ " -host example.com -port 25575 -pass test -command \"stop\"");
			return;

		}

		final char[] password = pass.toCharArray();

		// connect to server
		RCon connection = globals.getConnection();

		try {
			connection = new RCon(host, port, password);
		} catch (IOException e1) {

			Out.err("[Creating Connection] Could Not Connect!");
			// e1.printStackTrace();
			System.exit(0);
		} catch (AuthenticationException e1) {

			Out.err("[Creating Connection] Wrong Password!");
			// e1.printStackTrace();
			System.exit(0);
		}

		// send command
		globals.setConnection(connection);
		String response = "";
		try {
			response = connection.send(command);
			Out.out("[Server] " + response);
		} catch (IncorrectRequestIdException e) {
			Out.err("[Sending Command] Incorrect Request Id Exception Found!");
			e.printStackTrace();
		} catch (IOException e) {
			Out.err("[Sending Command] General Exception Found!");
			e.printStackTrace();
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				Out.err("[Disconnecting] General Exception Found!");
				e.printStackTrace();
			}
		}

	}

	private static String getFilename() {
		Class<?> cls = RConClient.class;
		String fileName = null;

		if (fileName == null) {
			try {
				fileName = getClassLoader(cls);
			} catch (final Exception e) {
				Out.out("Error: Finding file failed");
				e.printStackTrace();
			}
			if (fileName.equals("rsrcERROR")) {
				return "RConClient.jar";
			}
		}

		fileName = fileName
				.substring(fileName.lastIndexOf(File.separatorChar) + 1,
						fileName.length());
		return fileName;
	}

	/**
	 * This gets the filename of a .jar (typically this one!)
	 * 
	 * @author Morlok8k
	 */
	private static String getClassLoader(final Class<?> classFile)
			throws IOException {
		final ClassLoader loader = classFile.getClassLoader();
		String filename = classFile.getName().replace('.', '/') + ".class";
		final URL resource = (loader != null) ? loader.getResource(filename)
				: ClassLoader.getSystemResource(filename);
		filename = URLDecoder.decode(resource.toString(), "UTF-8");
		// out(filename);

		// START Garbage removal:
		int bang = filename.indexOf("!"); // remove everything after xxxx.jar
		if (bang == -1) { // a real example:
			bang = filename.length(); // jar:file:/home/morlok8k/test.jar!/me/Morlok8k/test/Main.class
		}
		int file = filename.indexOf("file:"); // removes junk from the beginning
												// of the path
		file = file + 5;
		if (file == -1) {
			file = 0;
		}
		if (filename.contains("rsrc:")) {
			Out.err("THIS WAS COMPILED USING \"org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader\"! ");
			Out.err("DO NOT PACKAGE YOUR .JAR'S WITH THIS CLASSLOADER CODE!");
			Out.err("(Your Libraries need to be extracted.)");
			return "rsrcERROR";
		}

		filename = filename.replace('/', File.separatorChar);
		final String returnString = filename.substring(file, bang);
		// END Garbage removal
		return returnString;
	}

}
