package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientInput extends Thread {
	private Socket connection;

	public ClientInput(Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		String line = new String();
		try {
			InputStream input = connection.getInputStream();
			byte[] byteArray;
			while (!connection.isClosed()) {
				int c;
				line = "";
				while ((c = input.read()) != '\n') {
					line += (char) c;
				}
			}
			input.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
