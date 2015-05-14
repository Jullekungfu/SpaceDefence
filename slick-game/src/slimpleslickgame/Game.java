package slimpleslickgame;

import java.util.concurrent.ConcurrentHashMap;

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
	private boolean gameOver;
	private byte winnerID;

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
		gameOver = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g)
			throws SlickException {

		if (gameStarted) {
			if (gameOver) {
				g.drawString("Player " + winnerID + " WINS!",
						(Client.WIDTH / 2) - 50, (Client.HEIGHT / 2) + 100);
				g.drawString("GAME IS OVER, PRESS ENTER TO EXIT",
						(Client.WIDTH / 2) - 150, (Client.HEIGHT / 2) + 200);
			} else {
				for (GameInstance gi : instances.values()) {
					gi.render(g);
				}
			}
		} else {
			int th = g.getFont().getLineHeight();
			int tw = 100;
			int buttonSize = 50;

			// Game Info
			g.drawRect((Client.WIDTH / 2) + tw - buttonSize,
					(Client.HEIGHT / 3) - 2 * buttonSize - 6, 3 * tw, 2 * tw
							+ buttonSize + 6);
			g.drawString("Protect home from invaders "
					+ "\n-HP decrease on enemy passing by"
					+ "\n-Freeze on enemy collision"
					+ "\n\nSending creeps:"
					+ "\n-1 key 1 creep 100 credits"
					+ "\n-2 key 5 creeps 250 credits"
					+ "\n-3 key 20 creeps 500 credits"
					+ "\n\nUpgrading ship cost 750 credits:"
					+ "\n-Fire rate increase"
					+ "\n-Income increase",
					(Client.WIDTH / 2) + tw - buttonSize + 6,
					(Client.HEIGHT / 3) - 2 * buttonSize);

			// Controller rectangle
			g.drawRect((Client.WIDTH / 2) - 4 * tw - 6, (Client.HEIGHT / 3) - 2
					* buttonSize - 6, 4 * tw + 30, 2 * tw + buttonSize + 6);

			// 1 key
			g.drawRect((Client.WIDTH / 2) - 4 * tw, (Client.HEIGHT / 3) - 2
					* buttonSize + 6, buttonSize, buttonSize);
			g.drawString("1", ((Client.WIDTH + buttonSize / 3) / 2) - 4 * tw,
					((Client.HEIGHT + buttonSize / 2) / 3) - 2 * buttonSize + 6);
			// 2 key
			g.drawRect((Client.WIDTH / 2) - 4 * tw + buttonSize + 6,
					(Client.HEIGHT / 3) - 2 * buttonSize + 6, buttonSize,
					buttonSize);
			g.drawString("2", ((Client.WIDTH + buttonSize / 3) / 2) - 4 * tw
					+ buttonSize + 6, ((Client.HEIGHT + buttonSize / 2) / 3)
					- 2 * buttonSize + 6);
			// 3 key
			g.drawRect((Client.WIDTH / 2) - 4 * tw + 2 * (buttonSize + 6),
					(Client.HEIGHT / 3) - 2 * buttonSize + 6, buttonSize,
					buttonSize);
			g.drawString("3", ((Client.WIDTH + buttonSize / 3) / 2) - 4 * tw
					+ 2 * (buttonSize + 6),
					((Client.HEIGHT + buttonSize / 2) / 3) - 2 * buttonSize + 6);
			// 123 description
			g.drawString("Send creeps to \nother players", (Client.WIDTH / 2)
					- 4 * tw, (Client.HEIGHT / 3) - buttonSize + 6);

			// Z key

			g.drawRect((Client.WIDTH / 2) - 4 * tw, (Client.HEIGHT / 3)
					+ buttonSize + 6, buttonSize, buttonSize);
			g.drawString("Z", ((Client.WIDTH + buttonSize / 2) / 2) - 4 * tw,
					((Client.HEIGHT + buttonSize / 2) / 3) + buttonSize + 6);

			g.drawString("Upgrade \nship", (Client.WIDTH / 2) - 4 * tw,
					(Client.HEIGHT / 3) + 2 * buttonSize + 6);

			// Space
			g.drawRect((Client.WIDTH / 2) - 4 * tw + buttonSize + 35,
					(Client.HEIGHT / 3) + buttonSize + 6, 3 * buttonSize,
					buttonSize);
			g.drawString("Shoot",
					(Client.WIDTH / 2) - 4 * tw + buttonSize + 35,
					(Client.HEIGHT / 3) + 2 * buttonSize + 6);

			// Left arrow
			g.drawRect((Client.WIDTH / 2) - 3 * tw + 3 * buttonSize + 6,
					(Client.HEIGHT / 3) + buttonSize + 6, buttonSize,
					buttonSize);
			g.drawString("Left", ((Client.WIDTH + buttonSize / 3) / 2) - 3 * tw
					+ 3 * buttonSize + 6,
					((Client.HEIGHT + buttonSize / 2) / 3) + buttonSize + 6);
			// Keys up/down
			g.drawRect((Client.WIDTH / 2) - 3 * tw + 4 * buttonSize + 2 * 6,
					(Client.HEIGHT / 3), buttonSize, buttonSize);
			g.drawRect((Client.WIDTH / 2) - 3 * tw + 4 * buttonSize + 2 * 6,
					(Client.HEIGHT / 3) + buttonSize + 6, buttonSize,
					buttonSize);

			// Right arrow
			g.drawRect((Client.WIDTH / 2) - 3 * tw + 5 * buttonSize + 3 * 6,
					(Client.HEIGHT / 3) + buttonSize + 6, buttonSize,
					buttonSize);
			g.drawString("Right", (Client.WIDTH / 2) - 3 * tw + 5 * buttonSize
					+ 3 * 6 + 3, ((Client.HEIGHT + buttonSize / 2) / 3)
					+ buttonSize + 6);
			// Arrow description
			g.drawString("Navigate ship", (Client.WIDTH / 2) - 3 * tw + 3
					* buttonSize + 6, (Client.HEIGHT / 3) + 2 * buttonSize + 6);

			// START GAME
			g.drawString("PRESS ENTER TO START GAME\nPlayers in game: "
					+ instances.size(), (Client.WIDTH / 2) - tw,
					(2 * Client.HEIGHT / 3) - th - buttonSize);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {

		if (gameStarted) {
			if (gameOver) {
				if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
					System.exit(0);
				}
			} else {
				int totalP = 0;
				int deadP = 0;
				for (GameInstance gi : instances.values()) {
					if (gi.isPlayerDead()) {
						deadP++;
					} else {
						winnerID = gi.getPlayerId();
					}
					totalP++;
				}
				gameOver = (deadP >= (totalP - 1));

				for (GameInstance gi : instances.values()) {
					gi.update(delta);
				}

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
		addPlayer(new OpponentPlayer(playerId, this.gse));
	}

	public void addLocalPlayer(byte playerId) {
		addPlayer(new LocalPlayer(gc, bm, gse, playerId));
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
