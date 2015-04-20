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

import client.Client;

public class MainMenu extends BasicGameState {
	public static final int ID = 1;
	private StateBasedGame game;
	private UnicodeFont ufont;

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
        Input input = container.getInput();
        if(input.isKeyDown(Input.KEY_1)){
        	JOptionPane.showInputDialog("");
        } else if(input.isKeyDown(Input.KEY_2)){
        	String ipport = JOptionPane.showInputDialog("Connect to server using ip:port");
        	game.enterState(Game.ID, new FadeOutTransition(org.newdawn.slick.Color.black), new FadeInTransition(org.newdawn.slick.Color.black));
    		
        }
	}

	@Override
	public int getID() {
		return ID;
	}
}
