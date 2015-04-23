package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		System.out.println("Inthread created.");
	}

	@Override
	public void run() {
		
		try {
			InputStream input = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String line = "";
			while (!connection.isClosed()) {
				while((line = br.readLine()) != null){
					monitor.putArrayFromServer(line.getBytes());
				}
			}
			input.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
