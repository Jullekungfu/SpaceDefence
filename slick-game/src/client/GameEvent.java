package client;

import org.newdawn.slick.geom.Vector2f;

import util.GameRole;

public class GameEvent {
	private Vector2f pos;
	private Vector2f dir;
	private GameRole role;
	private int id;
	private boolean isAlive;
	private int score = -1;
	private int creepsSent = 0;
	private int hp;
	private byte sendId = 0x0;
	
	public GameEvent() {
		role = GameRole.PLAYER;
		isAlive = true;
		hp = -1;
	}
	
	public GameEvent(GameRole role, int id){
		this.role = role; 
		this.id = id;
		isAlive = true;
		hp = -1;
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
	
	public void setRole(GameRole role){
		this.role = role;
	}
	
	//Only for Role CREEP and BULLET
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setDead(){
		isAlive = false;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void setSendId(byte b){
		this.sendId = b;
	}
	
	public byte getSendId(){
		return sendId;
	}
	
	//Only for player
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}

	public void setInit(int nbrCreeps) {
		this.creepsSent += nbrCreeps;
	}
	
	public int getSentCreeps(){
		return creepsSent;
	}
	
	public void setPlayerHp(int hp) {
		this.hp = hp;
		if (hp <= 0){
			setDead();
		}
	}
	
	public int getPlayerHp(){
		return hp;
	}
}
