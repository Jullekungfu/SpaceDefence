package slimpleslickgame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class SimpleSlickGame extends BasicGame {

	// TODO: sprites can wait, rectangles are fine
	// private Image[] shipDefault, shipLeft, shipRight;
	// private Animation sprite, spaceShipDefault, spaceShipLeft,
	// spaceShipRight;
	// private SpriteSheet spaceShipSpriteSheet;

	private Player player;

	public SimpleSlickGame(String gamename) {
		super(gamename);
		// TODO: Connect with server

	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO: Setup game stuff

		player = new Player();
		player.init();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO: Update all objects
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

	public void render(GameContainer gc, Graphics g) throws SlickException {
		player.render(g);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame(
					"Simple Slick Game"));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			appgc.setDisplayMode(
					new Double(screenSize.width * 0.75).intValue(), new Double(
							screenSize.getHeight() * 0.75).intValue(), false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}