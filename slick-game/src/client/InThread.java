package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
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
			while(!connection.isClosed()){
				byte[] intBytes = new byte[4];
				for(int i = 0; i < 4; i++){
					intBytes[i] = (byte) input.read();
				}
				int msgLen = ByteBuffer.wrap(intBytes).getInt();
				
				byte[] msg = new byte[msgLen];
				int read = 0, offset = 0, toRead = msgLen;
				while(toRead > 0 && (read = input.read(msg, offset, toRead)) > 0){
					toRead -= read;
					offset += read;
				}
				if(read >= 0){
					monitor.putArrayFromServer(msg);
				}else{
					break;
					//TODO: fix when server dies
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
