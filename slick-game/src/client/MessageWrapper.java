/**
 * 
 */
package client;

import java.nio.ByteBuffer;

import util.EventProtocol;

/**
 * @author nille
 *
 */
public class MessageWrapper {

	public static byte[] wrapMessageToServer(byte[] msg, byte id){
		byte[] temp = new byte[msg.length + 6];
		byte[] msgLength = ByteBuffer.allocate(4).putInt(msg.length + 2).array(); 
		temp[0] = msgLength[0];
		temp[1] = msgLength[1];
		temp[2] = msgLength[2];
		temp[3] = msgLength[3];
		temp[4] = EventProtocol.PLAYER_ID;
		temp[5] = id;
		for(int i = 0; i < msg.length; i++){
			temp[i+6] = msg[i];
		}
		return temp;
	}
}
