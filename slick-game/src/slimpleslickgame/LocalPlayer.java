package slimpleslickgame;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import util.ColorSwitch;
import util.EventProtocol;
import util.Logger;
import client.ByteMonitor;
import client.GameEvent;
import client.GameStatsEvents;
import client.MessageWrapper;

public class LocalPlayer extends Player {

	private GameContainer gc;
	private ByteMonitor bm;
	private int time;
	private int creepID;
	private ArrayList<Integer> deadCreeps;
	private int playerDamageCoolDown;

	public LocalPlayer(GameContainer gc, ByteMonitor bm, GameStatsEvents gse, byte id) {
		super(id, gse);
		this.gc = gc;
		this.bm = bm;
		time = 0;
		creepID = 1;
		deadCreeps = new ArrayList<>();
		playerDamageCoolDown = -1;
	}

	@Override
	public void init(Vector2f pos, Stats stats) {
		super.init(pos, stats);
		byte[] msg = { EventProtocol.OPPONENT_PLAYER_INIT };
		bm.putArrayToServer(msg, super.id);
	}

	public void update(int delta, Shape containerShape) {
		if (dead)
			return;
		
		time++;
		if (processInput(gc.getInput())) {
			if (bm != null) {
				byte[] bytes = MessageWrapper.appendByteArray(
								MessageWrapper.getPlayerPositionBytes(super.position),
								MessageWrapper.getPlayerDirectionBytes(super.direction));
				bm.putArrayToServer(bytes, id);
			}
			super.updatePosition(containerShape);
		}

		if (time % 60 == 0) {
			float xPos =(float) (containerShape.getMinX() + (20 + (Math.random()*(containerShape.getWidth()-40))));
			
			Vector2f initPos = new Vector2f(xPos, 20);
			super.creeps.put(creepID, new Creep(initPos, Color.white));

			byte[] bytes = MessageWrapper.appendByteArray(
					MessageWrapper.appendByteArray(new byte[]{EventProtocol.CREEP_INIT, EventProtocol.CREEP_ID}, ByteBuffer.allocate(4).putInt(creepID).array()), 
					MessageWrapper.appendByteArray(new byte[]{0x0, EventProtocol.EVENT_POS}, MessageWrapper.getVector2fBytes(initPos)));
			
			bm.putArrayToServer(bytes, super.id);
			creepID++;
		}
		
		GameEvent event;
		while((event = gse.pop(id)) != null){
			int sentCreeps = 0;
			if((sentCreeps = event.getSentCreeps()) > 0){
				byte sendId = (byte) event.getId();
				Color color = ColorSwitch.getColorFromId(sendId);
				for(int i = 0; i < sentCreeps; i++){
					float xPos =(float) (containerShape.getMinX() + (20 + (Math.random()*(containerShape.getWidth()-40))));
				
					Vector2f initPos = new Vector2f(xPos, 20);
					super.creeps.put(creepID, new Creep(initPos, color));
					
					byte[] bytes = MessageWrapper.appendByteArray(
							MessageWrapper.appendByteArray(new byte[]{EventProtocol.CREEP_INIT, EventProtocol.CREEP_ID}, ByteBuffer.allocate(4).putInt(creepID).array()), 
							MessageWrapper.appendByteArray(new byte[]{sendId, EventProtocol.EVENT_POS}, MessageWrapper.getVector2fBytes(initPos)));
					bm.putArrayToServer(bytes, super.id);
					
					creepID++;
				}
			}
		}

		for (Creep c : creeps.values()) {
			if(super.shape.intersects(c.getShape())&& playerDamageCoolDown==-1){
				//TODO: Stun player.
				playerDamageCoolDown = 0;
			}
			c.update(delta);
		}
		
		if(playerDamageCoolDown >= 0){
			if(playerDamageCoolDown > 2000){
				playerDamageCoolDown = -1;
			} else {
				playerDamageCoolDown+=delta;
			}
		}
		
		gun.update(delta);
		
		deadCreeps.clear();
		int score = 0;
		int bid;
		for(Entry<Integer, Creep> c : creeps.entrySet()){
			if((bid = gun.bulletIntersectsCreep(c.getValue().getShape())) != -1){
				score += c.getValue().getScoreValue();
				deadCreeps.add(c.getKey());
				byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.BULLET_DIED, EventProtocol.BULLET_ID}, ByteBuffer.allocate(4).putInt(bid).array());
				bm.putArrayToServer(bytes, super.id);
			}
			
			if (containerShape.intersects(c.getValue().getShape())){
				int hp = stats.damaged();
				dead = hp <= 0;
				
				byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.PLAYER_HP}, ByteBuffer.allocate(4).putInt(hp).array());
				bm.putArrayToServer(bytes, super.id);
				
				deadCreeps.add(c.getKey());
			}
		}
		for(int boobid : gun.getOutOfBoundsBullets()){
			gun.delete(boobid);
			byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.BULLET_DIED, EventProtocol.BULLET_ID}, ByteBuffer.allocate(4).putInt(boobid).array());
			bm.putArrayToServer(bytes, super.id);
			
		}
		
		for(int i : deadCreeps){
			this.delete(i);
			byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.CREEP_DIED, EventProtocol.CREEP_ID}, ByteBuffer.allocate(4).putInt(i).array());
			bm.putArrayToServer(bytes, super.id);
		}
		
		score = stats.calcCredits(delta, score);
		if(stats.update(delta)){
			super.gun.upgrade();
		}
		
		byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.PLAYER_SCORE}, ByteBuffer.allocate(4).putInt(score).array());
		bm.putArrayToServer(bytes, id);
	}
	
	public void delete(int id){
		creeps.remove(id);
	}

	private boolean processInput(Input input) {
		Vector2f direction = new Vector2f(0, 0);
		boolean dirChanged = false;

		if (input.isKeyDown(Input.KEY_LEFT)) {
			direction.add(new Vector2f(-1, 0));
			dirChanged = true;
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			direction.add(new Vector2f(1, 0));
			dirChanged = true;
		}
		
		if(input.isKeyDown(Input.KEY_Z)){
			stats.putUpgradePressed();
		}
		
		int sendCreeps = 0;
		if(input.isKeyPressed(Input.KEY_1)){
			sendCreeps += stats.buyCreeps(1);
		}
		
		if(input.isKeyPressed(Input.KEY_2)){
			sendCreeps += stats.buyCreeps(2);
		}
		
		if(input.isKeyPressed(Input.KEY_3)){
			sendCreeps += stats.buyCreeps(3);
		}
		
		if(sendCreeps > 0){
			byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.CREEP_SENT},ByteBuffer.allocate(4).putInt(sendCreeps).array()); 
			bm.putArrayToServer(bytes, id);
		}

		if (input.isKeyDown(Input.KEY_SPACE) && playerDamageCoolDown==-1) {
			Vector2f shotPos = new Vector2f(this.position.x
					+ this.shape.getWidth() / 2, this.position.y
					+ this.shape.getHeight() / 2);
			if(super.gun.shoot(shotPos)){

				byte[] bytes = MessageWrapper.appendByteArray(
					MessageWrapper.appendByteArray(new byte[]{EventProtocol.BULLET_INIT, EventProtocol.BULLET_ID}, ByteBuffer.allocate(4).putInt(super.gun.getbulletID()).array()), 
					MessageWrapper.appendByteArray(new byte[]{EventProtocol.EVENT_POS}, MessageWrapper.getVector2fBytes(shotPos)));
				bm.putArrayToServer(bytes, super.id);
			}
		}
		
		super.setDirection(direction);
		return dirChanged;
	}

}
