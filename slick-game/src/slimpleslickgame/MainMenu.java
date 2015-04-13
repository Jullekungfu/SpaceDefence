package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.Client;

public class MainMenu extends BasicGameState {
	public static final int ID = 1;
	private StateBasedGame game;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("NEW GAME", Client.WIDTH/2 - 50, 200);
		g.drawString("JOIN GAME", Client.WIDTH/2 - 50, 300);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return ID;
	}

}
