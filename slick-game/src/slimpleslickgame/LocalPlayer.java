package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;
import client.ByteMonitor;
import client.MessageWrapper;

public class LocalPlayer extends Player{

	private GameContainer gc;
	private ByteMonitor bm;
	private int time;
	private int creepID;
	
	public LocalPlayer(GameContainer gc, ByteMonitor bm, byte id){
		this.gc = gc;
		this.bm = bm;
		time = 0;
		creepID = 1;
		super.id = id;
	}
	
	@Override
	public void init(Vector2f pos) {
		super.init(pos);
		byte[] msg = {EventProtocol.OPPONENT_PLAYER_INIT};
		bm.putArrayToServer(msg, super.id);
	}

	public void update(int delta, Shape containerShape) {
		time++;
		if(processInput(gc.getInput())){
			if(bm != null){
				byte[] bytes = MessageWrapper.appendByteArray(MessageWrapper.getPlayerPositionBytes(super.position), MessageWrapper.getPlayerDirectionBytes(super.direction));
				bm.putArrayToServer(bytes, id);
			}
			super.updatePosition();
		}

		if(time % 60 == 0){
			Vector2f initPos = new Vector2f(super.position.x, 0);
			super.creeps.put(creepID, new Creep(initPos));
			byte[] bytes = MessageWrapper.appendByteArray(new byte[]{EventProtocol.CREEP_INIT, EventProtocol.CREEP_ID, (byte) creepID, EventProtocol.CREEP_POS}, MessageWrapper.getVector2fBytes(initPos));
			bm.putArrayToServer(bytes, super.id);
			creepID++;
		}
		for(Creep c : super.creeps.values()){
			c.update(delta);
		}
		gun.update(delta);
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

		if (input.isKeyDown(Input.KEY_UP)) {
			direction.add(new Vector2f(0, -1));
			dirChanged = true;
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			direction.add(new Vector2f(0, 1));
			dirChanged = true;
		}
		// TODO: add shooting capabilities
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			Vector2f shotPos = new Vector2f(this.position.x + this.shape.getWidth()/2, this.position.y + this.shape.getHeight()/2);
			this.gun.shoot(shotPos);
		}
		
		super.setDirection(direction);
		return dirChanged;
	}

}
