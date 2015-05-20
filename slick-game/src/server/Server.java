package server;

import java.io.*;
import java.net.*;

import client.MessageWrapper;
import util.EventProtocol;
import util.Logger;

public class Server extends Thread {
	private ServerSocket ss;

	public static void main(String[] args) {
		Server si = new Server();
		si.start();
	}

	public Server() {
		int port = 30000;
		try {
			ss = new ServerSocket(port);
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Logger.log("Server started");
		Socket connection;
		Statebox mb = new Statebox();
		UpdateToClient rt = new UpdateToClient(mb);
		rt.start();
		while (true) {
			try {
				connection = ss.accept();
				new PlayerParticipant(connection, mb).start();
				rt.addSocket(connection);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}

class UpdateToClient extends Thread {
	private Statebox statebox;
	private byte[] msg;

	public UpdateToClient(Statebox statebox) {
		this.statebox = statebox;
	}

	// TODO: (re)move this method
	public void addSocket(Socket s) {
		if (statebox.getCurrentClients() >= 4) {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		statebox.addClient(s);
		Logger.log("Sending local player init id");
		byte[] msg = { EventProtocol.LOCAL_PLAYER_INIT };
		byte[] idMessage = MessageWrapper.wrapMessageToServer(msg,
				(byte) statebox.getCurrentClients());
		try {
			sendMessage(idMessage, s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(byte[] msg, OutputStream writer)
			throws IOException {

		writer.write(msg);
		writer.flush();
	}

	@Override
	public void run() {
		while (true) {
			msg = this.statebox.readMessage();
			int id = (int) msg[5];

			for (Socket s : statebox.getClientSockets(id)) {
				if (s.isClosed()) {
					statebox.removeClientSocket(s);
				} else {
					try {
						sendMessage(msg, s.getOutputStream());
					} catch (IOException ioe) {
						Logger.log("Couldn't send message");
					}
				}
			}
		}
	}
}
