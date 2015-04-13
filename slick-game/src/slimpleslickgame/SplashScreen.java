package slimpleslickgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SplashScreen extends BasicGameState {

    private static final int WIDTH   = 1920;
    private static final int HEIGHT  = 1080;
	public static final int ID = 0;
	private StateBasedGame game;
	private int time;
	private Font font;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		this.game = game;
		time = 0;
				
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("SPLASHSCREEN", WIDTH/2, HEIGHT/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		time += delta;
		if(time > 3000){
			game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}

	}

	@Override
	public int getID() {
		return ID;
	}

}
