package slimpleslickgame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.ByteMonitor;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private GameContainer gc;
	private ByteMonitor bm;
	private PlayerMonitor playerMonitor;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor, PlayerMonitor playerMonitor) {
		this.bm = byteMonitor;
		this. playerMonitor = playerMonitor;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff
		this.gc = gc;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO:render all stuff here
		if(playerMonitor != null)
			playerMonitor.render(gc, arg1, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		if(playerMonitor != null)
			playerMonitor.update(gc, arg1, delta);
	}

	@Override
	public int getID() {
		return ID;
	}

	public void addPlayer(byte playerId) {
		playerMonitor.addPlayer(playerId);
	}
	
	public void addLocalPlayer(byte playerId){
		playerMonitor.addLocalPlayer(gc, bm, playerId);
	}
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		this.bm.closeConnection();
	}
}
