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
	
	public GameInstance(Player player, Vector2f size){
		float x = (int)(player.id-1)*size.x;
		this.board = new Board(new Vector2f(x, 0), size);
		
		Stats playerStats = new Stats(board.getScoreBoardPos());
		player.init(board.getPlayerInitPos(), playerStats);
		this.player = player;
	}
	
	public void update(int delta){
		player.update(delta, board.getShape());
	}
	
	public void render(Graphics graphics){
		board.render(graphics);
		player.render(graphics);
	}
}
