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

	public static byte[] wrapMessage(byte[] msg, byte id){
		byte[] temp = new byte[msg.length + 7];
		byte[] msgLength = ByteBuffer.allocate(4).putInt(msg.length + 2).array(); 
		temp[0] = EventProtocol.MESSAGE_LENGTH;
		temp[1] = msgLength[0];
		temp[2] = msgLength[1];
		temp[3] = msgLength[2];
		temp[4] = msgLength[3];
		temp[5] = EventProtocol.PLAYER_ID;
		temp[6] = id;
		for(int i = 0; i < msg.length; i++){
			temp[i+7] = msg[i];
		}
		return temp;
	}
}
