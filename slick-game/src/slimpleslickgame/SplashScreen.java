package slimpleslickgame;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import client.Client;

public class SplashScreen extends BasicGameState {

	public static final int ID = 0;
	private StateBasedGame game;
	private int time;
	private Font font;
	private UnicodeFont ufont;
	private String text;
	private int tw, th;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		time = 0;

				
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		int th = g.getFont().getLineHeight()/2;
		int tw = 50;
		
		g.drawString("SPLASHSCREEN", (Client.WIDTH/2)-tw, (Client.HEIGHT/2)-th);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
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
