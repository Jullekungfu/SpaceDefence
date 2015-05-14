package slimpleslickgame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import util.Logger;

public class Stats {
	
	private float x, y;
	private int credits = 0;
	private int level = 1;
	private int time = 0;
	private int creditsDiff = 0;
	private boolean tryUpgrade = false;
	private int hp = 1;
	
	
	public Stats(Vector2f scoreBoardPos) {
		this.x = scoreBoardPos.x;
		this.y = scoreBoardPos.y;
	}

	public void render(Graphics graphics){
		graphics.drawString("Credits: " + credits , x, y-graphics.getFont().getLineHeight());
		graphics.drawString("HP: "+ hp , x, y);
	}
	
	public boolean update(int delta){
		this.credits += creditsDiff;
		this.creditsDiff = 0;
		int levelCredits = level * 1000;
		if(this.credits >= levelCredits && this.tryUpgrade){
			level++;
			this.tryUpgrade = false;
			this.credits -= levelCredits;
			Logger.log("Upgraded player!");
			return true;
			
		}
		return false;
	}

	public void putUpgradePressed() {
		this.tryUpgrade = true;
	}

	public int calcCreditsDiff(int delta, int scoreDiff) {
		this.creditsDiff += scoreDiff;
		this.time += delta;
		while(time > 1000){
			time -= 1000;
			this.creditsDiff += level;
		}
		return this.creditsDiff;
	}

	public int damaged() {
		hp--;
		return hp;
	}

	public void setHP(int playerHp) {
		this.hp = playerHp;
	}

}
