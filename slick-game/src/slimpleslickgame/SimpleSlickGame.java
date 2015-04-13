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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.geom.Vector2f;

public class SimpleSlickGame extends BasicGame implements InputProviderListener {

	private Image[] shipDefault, shipLeft, shipRight;
	private Animation sprite, spaceShipDefault, spaceShipLeft, spaceShipRight;
	private SpriteSheet spaceShipSpriteSheet;
	private InputProvider provider;
	private Player player;
	
	private final String moveLeftS = "moveLeft";
	private final String moveRightS = "moveRight";
	private final String moveUpS = "moveUp";
	private final String moveDownS = "moveDown";
	private final String attackS = "attack";
	
	private Command moveLeft = new BasicCommand(moveLeftS);
	private Command moveRight = new BasicCommand(moveRightS);
	private Command moveUp = new BasicCommand(moveUpS);
	private Command moveDown = new BasicCommand(moveDownS);
	private Command attack = new BasicCommand(attackS);
	
	public SimpleSlickGame(String gamename) {
		super(gamename);
		//TODO: Connect with server
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		provider = new InputProvider(gc.getInput());
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), moveLeft);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), moveRight);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), moveRight);
		provider.bindCommand(new KeyControl(Input.KEY_UP), moveUp);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), moveUp);
		provider.bindCommand(new KeyControl(Input.KEY_DOWN), moveDown);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.DOWN), moveDown);
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), attack);
		
		player = new Player();
		player.init();
		//TODO: Setup game stuff
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//TODO: Update all objects
		player.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawString("Howdy!", 100, 100);
		g.drawRect(50, 50, 200, 400);
		g.drawRect(250, 50, 200, 400);
		player.render(g);
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

	@Override
	public void controlPressed(Command cmd) {
		// TODO Auto-generated method stub
		BasicCommand bcmd = (BasicCommand) cmd;
		System.out.println(bcmd.toString());
		float x, y;
		switch(bcmd.getName()){
		case moveLeftS:
			x = -1;
			y = 0;
			break;
		case moveRightS:
			x = 1;
			y = 0;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		
		player.changeDirection(x, y);
		
		
	}

	@Override
	public void controlReleased(Command cmd) {
		// TODO Auto-generated method stub
		
	}
}