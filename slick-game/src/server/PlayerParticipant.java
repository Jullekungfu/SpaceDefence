package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PlayerParticipant extends Thread {
	private InputStream input;
	private Socket socket;
	private Statebox stateBox;

	public PlayerParticipant(Socket socket, Statebox stateBox) {
		try {
			this.input = socket.getInputStream();
			this.socket = socket;
			this.stateBox = stateBox;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String message = "";
			int character;
			while ((character = input.read()) != -1) {
				message += character;
				if ((char) character == '\n' || (char) character == '\r') {
					stateBox.writeMessage(message.getBytes());
				}
			}
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}