package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import client.MessageWrapper;
import util.EventProtocol;
import util.Logger;

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
		byte id = -1;
		while(!socket.isClosed()){
			try {
				byte[] intBytes = new byte[4];
				for(int i = 0; i < 4; i++){
					intBytes[i] = (byte) input.read();
				}
				int msgLen = ByteBuffer.wrap(intBytes).asIntBuffer().get();
				id = (byte)input.read();
				byte[] msg = new byte[msgLen+4];
				for(int i = 0; i < 4; i++){
					msg[i] = intBytes[i];
				}
				
				for(int i = 4; i < msg.length; i++){
					msg[i] = (byte) input.read();
				}
				stateBox.writeMessage(msg);
	
			} catch(ArrayIndexOutOfBoundsException aioobe){
				Logger.log("Message not complete.");
				closeConnection();
			} catch (Exception e) {
				Logger.log(e.getMessage());
				closeConnection();
			}
		}
		stateBox.removeClientSocket(socket);
		if(id > -1){
			byte[] closingMsg = new byte[1];
			closingMsg[0] = EventProtocol.PLAYER_LOST_CONNECTION;
			stateBox.writeMessage(MessageWrapper.wrapMessageToServer(closingMsg, id));
		}
	}
	
	private void closeConnection(){
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}