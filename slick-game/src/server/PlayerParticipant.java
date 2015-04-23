package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerParticipant extends Thread {
	private BufferedReader br;
	private Socket socket;
	private Statebox stateBox;

	public PlayerParticipant(Socket socket, Statebox stateBox) {
		try {
			this.socket = socket;
			this.stateBox = stateBox;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String message = "";
			while ((message = br.readLine()) != null) {
				stateBox.writeMessage(message.getBytes());
			}
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}