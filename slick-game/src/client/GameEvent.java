package client;

import org.newdawn.slick.geom.Vector2f;

import util.GameRole;

public class GameEvent {
	private Vector2f pos;
	private Vector2f dir;
	private GameRole role;
	private byte id;
	
	public GameEvent() {
		role = GameRole.PLAYER;
	}
	
	public GameEvent(GameRole role, byte id){
		this.role = role; 
		this.id = id;
	}

	public void putPosition(Vector2f pos) throws Exception {
		if(this.pos == null){
			this.pos = pos;
		}else{
			throw new Exception("Position already set");
		}
	}

	public void putDirection(Vector2f dir) throws Exception {
		if(this.dir == null){
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
	
	public GameRole getRole(){
		return role;
	}
	
	//Only for Role CREEP and BULLET
	public byte getId(){
		return id;
	}
}
