package slimpleslickgame;

import java.nio.ByteBuffer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import util.EventProtocol;

public class LocalPlayer extends Player{

	private GameContainer gc;
	
	public LocalPlayer(GameContainer gc){
		this.gc = gc;
	}
	
	@Override
	public void update(int delta) {
		processInput(gc.getInput());
		super.updatePosition();
	}
	
	private void processInput(Input input) {
		Vector2f direction = new Vector2f(0, 0);

		if (input.isKeyDown(Input.KEY_LEFT)) {
			direction.add(new Vector2f(-1, 0));
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			direction.add(new Vector2f(1, 0));
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			direction.add(new Vector2f(0, -1));
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			direction.add(new Vector2f(0, 1));
		}

		// TODO: add shooting capabilities

		super.setDirection(direction);
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

	public byte[] floatToByte(float f) {

		return ByteBuffer.allocate(4).putFloat(f).array();
	}
	
	public byte[] getPositionBytes(Vector2f pos){
		byte[] position = new byte[1];
		position[0] = EventProtocol.PLAYER_POS;
		byte[] x = floatToByte(pos.x);
		byte[] y = floatToByte(pos.y);
		
		byte[] both = appendByteArray(position, x);
		both = appendByteArray(both, y);
		return both;
	}

}