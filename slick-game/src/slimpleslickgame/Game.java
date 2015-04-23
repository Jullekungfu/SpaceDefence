package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;
	
	private Player player;
	private GameStatsEvents gameStatsMonitor;
	
	public void addGSM(GameStatsEvents gsm){
		this.gameStatsMonitor = gsm;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		// TODO: Setup game stuff

		player = new LocalPlayer(gc);
		player.init();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		//TODO:render all stuff here
		player.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		// TODO: Update all logic
		player.update(delta);
	}

	@Override
	public int getID() {
		return ID;
	}

}
