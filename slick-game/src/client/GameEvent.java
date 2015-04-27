package client;

import org.newdawn.slick.geom.Vector2f;

public class GameEvent {
	private byte id;
	private Vector2f pos;
	private Vector2f dir;
	
	
	public GameEvent(byte id) {
		this.id = id;
	}

	public void putPosition(Vector2f pos) throws Exception {
			this.pos = pos;
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
	
	public Vector2f getDirection(){
		return this.dir;
	}
}
