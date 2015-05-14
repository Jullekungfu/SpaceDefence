package slimpleslickgame;

import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.shape.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.EventProtocol;
import client.ByteMonitor;
import client.Client;
import client.GameStatsEvents;

public class Game extends BasicGameState {
	public static final int ID = 2;

	private GameContainer gc;
	private ByteMonitor bm;
	private ConcurrentHashMap<Byte, GameInstance> instances;
	private GameStatsEvents gse;
	private Vector2f boardSize;
	private boolean gameStarted;

	public void addGSM(GameStatsEvents gse, ByteMonitor byteMonitor) {
		this.bm = byteMonitor;
		this.gse = gse;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1)
			throws SlickException {
		this.gc = gc;
		this.boardSize = new Vector2f(gc.getWidth() / 4, gc.getHeight());
		instances = new ConcurrentHashMap<Byte, GameInstance>();
		gameStarted = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {
		if (gameStarted) {
			for (Byte b : instances.keySet()) {
				instances.get(b).render(g);
			}
		} else {
			int th = g.getFont().getLineHeight();
			int tw = 100;
			int buttonWidth = 50;
			int buttonHeight = 50;

			// Controller rectangle
			g.drawRect((Client.WIDTH / 2) - 2 * tw - 6, (Client.HEIGHT / 3) - 2
					* buttonHeight - 6, 4 * tw + 30, 2 * tw + buttonHeight + 6);

			// 1 key
			g.drawRect((Client.WIDTH / 2) - 2 * tw, (Client.HEIGHT / 3) - 2
					* buttonHeight + 6, buttonWidth, buttonHeight);
			g.drawString("1", ((Client.WIDTH + buttonWidth / 3) / 2) - 2 * tw,
					((Client.HEIGHT + buttonHeight / 2) / 3) - 2 * buttonHeight
							+ 6);
			// 2 key
			g.drawRect((Client.WIDTH / 2) - 2 * tw + buttonWidth + 6,
					(Client.HEIGHT / 3) - 2 * buttonHeight + 6, buttonWidth,
					buttonHeight);
			g.drawString("2", ((Client.WIDTH + buttonWidth / 3) / 2) - 2 * tw
					+ buttonWidth + 6, ((Client.HEIGHT + buttonHeight / 2) / 3)
					- 2 * buttonHeight + 6);
			// 3 key
			g.drawRect((Client.WIDTH / 2) - 2 * tw + 2 * (buttonWidth + 6),
					(Client.HEIGHT / 3) - 2 * buttonHeight + 6, buttonWidth,
					buttonHeight);
			g.drawString("3", ((Client.WIDTH + buttonWidth / 3) / 2) - 2 * tw
					+ 2 * (buttonWidth + 6),
					((Client.HEIGHT + buttonHeight / 2) / 3) - 2 * buttonHeight
							+ 6);
			// 123 description
			g.drawString("Send creeps to \nother players", (Client.WIDTH / 2)
					- 2 * tw, (Client.HEIGHT / 3) - buttonHeight + 6);

			// Z key

			g.drawRect((Client.WIDTH / 2) - 2 * tw, (Client.HEIGHT / 3)
					+ buttonHeight + 6, buttonWidth, buttonHeight);
			g.drawString("Z", ((Client.WIDTH + buttonWidth / 2) / 2) - 2 * tw,
					((Client.HEIGHT + buttonHeight / 2) / 3) + buttonHeight + 6);

			g.drawString("Upgrade \nship", (Client.WIDTH / 2) - 2 * tw,
					(Client.HEIGHT / 3) + 2 * buttonHeight + 6);

			// Space
			g.drawRect((Client.WIDTH / 2) - 2 * tw + buttonWidth + 35,
					(Client.HEIGHT / 3) + buttonHeight + 6, 3 * buttonWidth,
					buttonHeight);
			g.drawString("Shoot", (Client.WIDTH / 2) - 2 * tw + buttonWidth
					+ 35, (Client.HEIGHT / 3) + 2 * buttonHeight + 6);

			// Left arrow
			g.drawRect((Client.WIDTH / 2) - tw + 3 * buttonWidth + 6,
					(Client.HEIGHT / 3) + buttonHeight + 6, buttonWidth,
					buttonHeight);
			g.drawString("Left", ((Client.WIDTH + buttonWidth / 3) / 2) - tw
					+ 3 * buttonWidth + 6,
					((Client.HEIGHT + buttonHeight / 2) / 3) + buttonHeight + 6);
			// Keys up/down
			g.drawRect((Client.WIDTH / 2) - tw + 4 * buttonWidth + 2 * 6,
					(Client.HEIGHT / 3), buttonWidth, buttonHeight);
			g.drawRect((Client.WIDTH / 2) - tw + 4 * buttonWidth + 2 * 6,
					(Client.HEIGHT / 3) + buttonHeight + 6, buttonWidth,
					buttonHeight);

			// Right arrow
			g.drawRect((Client.WIDTH / 2) - tw + 5 * buttonWidth + 3 * 6,
					(Client.HEIGHT / 3) + buttonHeight + 6, buttonWidth,
					buttonHeight);
			g.drawString("Right", (Client.WIDTH / 2) - tw + 5 * buttonWidth + 3
					* 6 + 3, ((Client.HEIGHT + buttonHeight / 2) / 3)
					+ buttonHeight + 6);
			// Arrow description
			g.drawString("Navigate ship", (Client.WIDTH / 2) - tw + 3
					* buttonWidth + 6, (Client.HEIGHT / 3) + 2 * buttonHeight
					+ 6);

			// START GAME
			g.drawString("PRESS ENTER TO START GAME\nPlayers in game: "
					+ instances.size(), (Client.WIDTH / 2) - tw,
					(2 * Client.HEIGHT / 3) - th - buttonHeight);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		if (gameStarted) {
			for (Byte b : instances.keySet()) {
				instances.get(b).update(delta);
			}
		} else {
			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
				byte[] bytes = new byte[] { EventProtocol.GAME_STARTED };
				bm.putArrayToServer(bytes, (byte) -1);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void startGame() {
		gameStarted = true;
	}

	public void addOpponentPlayer(byte playerId) {
		addPlayer(new OpponentPlayer(this.gse, playerId));
	}

	public void addLocalPlayer(byte playerId) {
		addPlayer(new LocalPlayer(gc, bm, playerId));
	}

	private void addPlayer(Player player) {
		GameInstance gs = new GameInstance(player, this.boardSize);
		instances.put(player.id, gs);
	}

	/**
	 * Closes the connection held by the bytemonitorn.
	 */
	public void onClose() {
		if (this.bm.isOpen())
			this.bm.closeConnection();
	}

	public void removePlayer(byte playerId) {
		// TODO: remove stuff inside instance?
		try {
			instances.remove(playerId);
		} catch (Exception e) {
			// TODO: end game
		}

	}

	public boolean isStarted() {
		return gameStarted;
	}
}
