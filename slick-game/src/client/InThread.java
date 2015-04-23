package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 * Reads server input and forwards it to ByteMonitor
 * @author antonlin
 *
 */
public class InThread extends Thread {
	private Socket connection;
	private ByteMonitor monitor;

	public InThread(ByteMonitor monitor, Socket connection) {
		this.connection = connection;
		this.monitor = monitor;
	}

	@Override
	public void run() {
		
		try {
			InputStream input = connection.getInputStream();
			String line = "";
			while (!connection.isClosed()) {
				int c = input.read();
				line += (char)c;
				if((char) c == '\n'|| (char) c == '\r'){
					monitor.putArrayFromServer(line.getBytes());
					line = "";
				}
			}
			input.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
