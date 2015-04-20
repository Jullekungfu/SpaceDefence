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
		InputStream in;
		try {
			in = connection.getInputStream();
			byte[] byteArray;
			while (true) {
				int c;
				line = "";
				while ((c = in.read()) != '\n') {
					line += (char) c;
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
