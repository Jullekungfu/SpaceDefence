package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.GameStatsMonitor;

public class Game extends BasicGameState {
	public static final int ID = 2;
	
	private Player player;
	private GameStatsMonitor gameStatsMonitor;
	
	public void addGSM(GameStatsMonitor gsm){
		this.gameStatsMonitor = gsm;
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
		//TODO:render all stuff here
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

}
