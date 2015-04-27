/**
 * 
 */
package client;

import java.nio.ByteBuffer;

import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;

/**
 * @author nille
 *
 */
public class MessageWrapper {

	/**
	 * Used in ByteMonitor to wrap messages to the server.
	 * @param msg
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Used in LocalPlayer to get position bytes.
	 * @param pos
	 * @return
	 */
	public static byte[] getPositionBytes(Vector2f pos){
		byte[] posi = new byte[1];
		posi[0] = EventProtocol.PLAYER_POS;
		byte[] x = floatToByte(pos.x);
		byte[] y = floatToByte(pos.y);
		
		byte[] both = appendByteArray(posi, x);
		both = appendByteArray(both, y);
		return both;
	}
	
	/**
	 * Generates a byte-array from a float object.
	 * @param f
	 * @return
	 */
	private static byte[] floatToByte(float f) {
		return ByteBuffer.allocate(4).putFloat(f).array();
	}
	
	/**
	 * Adds a second byte-array to the first.
	 * @param first
	 * @param second
	 * @return First+second in one array.
	 */
	private static byte[] appendByteArray(byte[] first, byte[] second){
		byte[] both = new byte[first.length + second.length];	
		for(int i = 0 ; i < first.length; i++){
			both[i] = first[i];
		}
		for(int i = 0 ; i < second.length; i++){
			both[i+first.length] = second[i];
		}	
		return both;
	}
}
