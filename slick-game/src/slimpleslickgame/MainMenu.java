package slimpleslickgame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import server.Server;
import client.Client;
import client.InitThread;

public class MainMenu extends BasicGameState {
	public static final int ID = 1;
	private StateBasedGame game;
	private UnicodeFont ufont;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 60);
		ufont = new UnicodeFont(font);
		ufont.addAsciiGlyphs();
		ufont.addGlyphs(32, 127);
		ufont.getEffects().add(new ColorEffect(Color.WHITE));
		ufont.loadGlyphs();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setFont(ufont);
		
		g.drawString("1. NEW GAME", Client.WIDTH/2 - ufont.getWidth("1. NEW GAME")/2, 200);
		g.drawString("2. JOIN GAME", Client.WIDTH/2 - ufont.getWidth("2. JOIN GAME")/2, 500);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        String ip = "127.0.0.1";
        boolean inp = false;
		Input input = container.getInput();
        if(input.isKeyDown(Input.KEY_1)){
        	(new Server()).start();
        	inp = true;
        } else if(input.isKeyDown(Input.KEY_2)){
        	ip = JOptionPane.showInputDialog("Connect to server using ip.");
        	inp = true;
        }
        if(inp){
        	this.game.enterState(Game.ID, new FadeOutTransition(org.newdawn.slick.Color.black), new FadeInTransition(org.newdawn.slick.Color.black));
			new InitThread(ip, (Game) game.getState(Game.ID)).start();
        }
	}

	@Override
	public int getID() {
		return ID;
	}
}
