package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class LocalPlayer extends Player{

	private GameContainer gc;
	
	public LocalPlayer(GameContainer gc){
		this.gc = gc;
	}
	
	@Override
	public void update(int delta) {
		processInput(gc.getInput());
		super.updatePosition();
	}
	
	private boolean processInput(Input input) {
		Vector2f direction = new Vector2f(0, 0);
		boolean dirChanged = false;

		if (input.isKeyDown(Input.KEY_LEFT)) {
			direction.add(new Vector2f(-1, 0));
			dirChanged = true;
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			direction.add(new Vector2f(1, 0));
			dirChanged = true;
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			direction.add(new Vector2f(0, -1));
			dirChanged = true;
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			direction.add(new Vector2f(0, 1));
			dirChanged = true;
		}

		// TODO: add shooting capabilities

		super.setDirection(direction);
		return dirChanged;
	}

}
