package client;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import slimpleslickgame.Application;

public class Client{

    public static int WIDTH   = 1280;
    public static int HEIGHT  = 720;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;

	public static void main(String[] args) {
		
		try{
			AppGameContainer container = new AppGameContainer(new Application("SpaceDefence"));
            container.setDisplayMode(WIDTH, HEIGHT, false);
            container.setTargetFrameRate(FPS);
            container.setShowFPS(true);
            container.start();
		} catch (SlickException e){
			e.printStackTrace();
		}
	}
}
