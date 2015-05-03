package slimpleslickgame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.ByteMonitor;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private GameContainer gc;
	private ByteMonitor bm;
	private List<GameInstance> instances;
	private GameStatsEvents gse;
	private Vector2f boardSize;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor) {
		this.bm = byteMonitor;
		this.gse = gse;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff
		this.gc = gc;
		this.boardSize = new Vector2f(gc.getWidth()/5, gc.getHeight());
		instances = Collections.synchronizedList(new ArrayList<GameInstance>());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		synchronized (instances){
			for(GameInstance i : instances){
				i.render(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		synchronized (instances) {
			for(GameInstance i : instances){
				i.update(delta);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void addOpponentPlayer(byte playerId) {
		addPlayer(new OpponentPlayer(this.gse, playerId));
	}
	
	public void addLocalPlayer(byte playerId){
		addPlayer(new LocalPlayer(gc, bm, playerId));
	}
	
	private void addPlayer(Player player){
		synchronized(instances){
			GameInstance gs = new GameInstance(player, this.boardSize);
			instances.add(gs);
		}
	}
	
	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose(){
		this.bm.closeConnection();
	}
}
