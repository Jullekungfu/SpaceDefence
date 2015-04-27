package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import client.ByteMonitor;
import client.MessageWrapper;

public class LocalPlayer extends Player{

	private GameContainer gc;
	private ByteMonitor bm;
	
	public LocalPlayer(GameContainer gc, ByteMonitor bm, byte id){
		this.gc = gc;
		this.bm = bm;
		super.id = id;
	}
	
	public void update(int delta) {
		if(processInput(gc.getInput())){
			if(bm != null){
				byte[] bytes = MessageWrapper.appendByteArray(MessageWrapper.getPositionBytes(super.position), MessageWrapper.getDirectionBytes(super.direction));
				bm.putArrayToServer(bytes, id);
			}
			super.updatePosition();
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
