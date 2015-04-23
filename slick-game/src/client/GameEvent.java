package client;

import java.nio.ByteBuffer;

import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;

public class GameEvent {
	private byte id;
	private Vector2f pos;
	private Vector2f dir;
	
	
	public GameEvent(byte id) {
		this.id = id;
	}

	public void putPosition(Vector2f pos) throws Exception {
		if(pos == null){
			this.pos = pos;
		}else{
			throw new Exception("Position already set");
		}
	}

	public void putDirection(Vector2f dir) throws Exception {
		if(dir == null){
			this.dir = dir;
		}else{
			throw new Exception("Direction already set");
		}
	}
	
	public Vector2f getPosition(){
		return this.pos;
	}
	
	public byte[] getPositionBytes(){
		byte[] position = new byte[1];
		position[0] = EventProtocol.PLAYER_POS;
		byte[] x = floatToByte(pos.x);
		byte[] y = floatToByte(pos.y);
		
		byte[] both = appendByteArray(position, x);
		both = appendByteArray(both, y);
		return both;
	}
	
	public byte[] appendByteArray(byte[] first, byte[] second){
		byte[] both = new byte[first.length + second.length];	
		for(int i = 0 ; i < first.length; i++){
			both[i] = first[i];
		}
		for(int i = 0 ; i < second.length; i++){
			both[i+first.length] = second[i];
		}	
		return both;
	}
	
	public Vector2f getDirection(){
		return this.dir;
	}
	
	public byte[] floatToByte(float f){
		
		return ByteBuffer.allocate(4).putFloat(f).array();
	}
}
