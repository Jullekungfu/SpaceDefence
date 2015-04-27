package slimpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Application extends StateBasedGame {
	
	// Game state identifiers
    public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;

    // Application Properties
    public static final int WIDTH   = 640;
    public static final int HEIGHT  = 480;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;
    
    

	public Application(String appName) {
		super(appName);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new SplashScreen());
        this.addState(new MainMenu());
        this.addState(new Game());
	}
	
	@Override
    public boolean closeRequested(){
		GameState currGS = this.getCurrentState();
		if(currGS.getID() == GAME){
			((Game) currGS).onClose();
		}
		System.exit(0); 
		return false;
    }	
}
