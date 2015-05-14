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
	private int hp = 100;
	private int incomeRate = 1000;
	
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
		int levelCredits = 1000;
		if(this.credits >= levelCredits && this.tryUpgrade){
			level++;
			this.credits -= levelCredits;
			Logger.log("Upgraded player!");
			return true;
		}
		this.tryUpgrade = false;
		return false;
	}

	public void putUpgradePressed() {
		this.tryUpgrade = true;
	}

	public int calcCreditsDiff(int delta, int scoreDiff) {
		this.creditsDiff += scoreDiff;
		this.time += delta;
		while(time > incomeRate){
			time -= incomeRate;
			this.creditsDiff += level;
		}
		return this.credits;
	}

	private static final int CREEP_PRICE_1 = 100;
	private static final int CREEP_PRICE_5 = 400;
	private static final int CREEP_PRICE_20 = 1200;
	
	public int buyCreeps(int key) {
		switch(key){
			case 1:
				if(this.credits >= CREEP_PRICE_1){
					credits -= CREEP_PRICE_1;
					this.incomeRate--;
					return 1;
				}
				break;
			case 2:
				if(this.credits >= CREEP_PRICE_5){
					credits -= CREEP_PRICE_5;
					this.incomeRate -= 6;
					return 5;
				}
				break;
			case 3:
				if(this.credits >= CREEP_PRICE_20){
					credits -= CREEP_PRICE_20;
					this.incomeRate -= 25;
					return 20;
				}
				break;
		}
		return 0;
	}
	
	public int damaged() {
		hp--;
		return hp;
	}

	public void setHP(int playerHp) {
		this.hp = playerHp;
	}

}
