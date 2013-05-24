package com.google.rconclient.cli;
import java.io.IOException;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.IncorrectRequestIdException;
import com.google.rconclient.rcon.RCon;
import com.google.rconclient.cli.Out;
import com.google.rconclient.gui.Globals;

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
		//read args
		if (args[0].equalsIgnoreCase("-version") || args[0].equalsIgnoreCase("-help")
				|| args[0].equals("/?")) {
			System.out.println("RCon Client");
			return;
		}

		String host = null;
		int port = -1;		//25575 is the default
		String pass = null;
		String command = null;
		
		
		try {
			for (int i = 0; i < (args.length); i++) {
				final String nextSwitch = args[i].toLowerCase();
				if (nextSwitch.equals("-host") || nextSwitch.equals("-h")) {
					host = args[i + 1].substring(0);
					Out.out("Host: " + host);

				} else if (nextSwitch.startsWith("-port") || nextSwitch.startsWith("-t")) {
					port = Integer.parseInt(args[i + 1].substring(0));
					Out.out("Port: " + port);

				} else if (nextSwitch.startsWith("-pass") || nextSwitch.startsWith("-p")) {
					pass = args[i + 1].substring(0);
					Out.out("Pass: " + pass);

				} else if (nextSwitch.startsWith("-command") || nextSwitch.startsWith("-c")) {
					command = args[i + 1].substring(0);
					Out.out("Command: " + command);
				} 
			}
		} catch (final NumberFormatException ex) {
			Out.err("Invalid switch value.");
			return;
		}
		
		//if something is missing, give error and quit
		if ((host == null) || (port == -1) || (pass == null) || (command == null)) {
			Out.err("Not all switches were provided.");
			Out.err("Use the following example: java -jar RCON.jar -host example.com -port 25575 -pass test -command \"stop\"");
			return;
			
		}
		
		final char[] password = pass.toCharArray();
		

		//connect to server
		RCon connection = globals.getConnection();

		try {
			connection = new RCon(host, port, password);
		} catch (IOException e1) {

			Out.err("[Creating Connection] Could Not Connect!");
			//e1.printStackTrace();
			System.exit(0);
		} catch (AuthenticationException e1) {

			Out.err("[Creating Connection] Wrong Password!");
			//e1.printStackTrace();
			System.exit(0);
		}
		
		//send command
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

}


