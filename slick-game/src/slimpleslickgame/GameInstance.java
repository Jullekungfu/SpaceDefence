/**
 * 
 */
package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * @author nille
 *
 */
public class GameInstance {
	
	private Player player;
	private Board board;
	private Stats stats;
	
	public GameInstance(Player player, Vector2f size){
		float x = (int)(player.id-1)*size.x;
		this.board = new Board(new Vector2f(x, 0), size);
		player.init(board.getPlayerInitPos());
		this.player = player;
		stats = new Stats(board.getScoreBoardPos());
	}
	
	public void update(int delta){
		StatEvent statEvent = player.update(delta, board.getShape());
		boolean upgraded = stats.update(delta, statEvent);
		if(upgraded){
			player.upgrade();
		}
	}
	
	public void render(Graphics graphics){
		board.render(graphics);
		player.render(graphics);
		stats.render(graphics);
	}
}
