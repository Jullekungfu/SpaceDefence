package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import util.EventProtocol;
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
			int b = -1;
			int id = -1;
			while(true){
				b = input.read();
				if(b != EventProtocol.PLAYER_ID){
					//Out of sync...
				}
				id = input.read();
				byte[] intBytes = new byte[4];
				for(int i = 0; i < 4; i++){
					intBytes[i] = (byte) input.read();
				}
				int msgLen = ByteBuffer.wrap(intBytes).getInt();
				
				int readBytes = 0;
				byte[] msg = new byte[msgLen];
				while(readBytes < msgLen){
					b = input.read(msg, readBytes, msgLen);
					
							
				}
				
			}
			
//			String line = "";
//			while (!connection.isClosed()) {
//				while((line = br.readLine()) != null){
//					monitor.putArrayFromServer(line.getBytes());
//				}
//			}
//			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
