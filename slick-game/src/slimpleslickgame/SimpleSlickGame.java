package slimpleslickgame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SimpleSlickGame extends BasicGame {

	private Image[] shipDefault, shipLeft, shipRight;
	private Animation sprite, spaceShipDefault, spaceShipLeft, spaceShipRight;
	private SpriteSheet spaceShipSpriteSheet;

	public SimpleSlickGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawString("Howdy!", 100, 100);
		g.drawRect(50, 50, 200, 400);
		g.drawRect(250, 50, 200, 400);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			appgc.setDisplayMode(
					new Double(screenSize.width * 0.75).intValue(), new Double(
							screenSize.getHeight() * 0.75).intValue(), false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}