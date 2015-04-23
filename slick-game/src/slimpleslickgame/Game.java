package slimpleslickgame;

import java.nio.ByteBuffer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.EventProtocol;

import client.ByteMonitor;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private Player player;
	//private GameStatsEvents gameStatsMonitor;
	private ByteMonitor byteMonitor;

	public void addGSM(GameStatsEvents gsm, ByteMonitor byteMonitor) {
		//this.gameStatsMonitor = gsm;
		this.byteMonitor = byteMonitor;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff

		player = new Player();
		player.init();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO:render all stuff here
		player.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		// TODO: Update all logic
		processInput(gc.getInput());
		player.update(delta);
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

		player.setDirection(direction);
	}

	@Override
	public int getID() {
		return ID;
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

}
