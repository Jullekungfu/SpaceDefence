package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class PlayerParticipant extends Thread {
	private InputStream input;
	private Socket socket;
	private Statebox stateBox;

	public PlayerParticipant(Socket socket, Statebox stateBox) {
		try {
			this.socket = socket;
			this.stateBox = stateBox;
			input = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(!socket.isClosed()){
			try {
				byte[] intBytes = new byte[4];
				for(int i = 0; i < 4; i++){
					intBytes[i] = (byte) input.read();
				}
				int msgLen = ByteBuffer.wrap(intBytes).getInt();
				
				byte[] msg = new byte[msgLen+4];
				for(int i = 0; i < 4; i++){
					msg[i] = intBytes[i];
				}
				
				for(int i = 4; i < msg.length; i++){
					msg[i] = (byte) input.read();
				}
				stateBox.writeMessage(msg);
	
			} catch(ArrayIndexOutOfBoundsException aioobe){
				System.out.println("Message not complete.");
				try {
					socket.close();
					System.out.println("Socket closed.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		stateBox.removeClientSocket(socket);
	}
}