package client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import slimpleslickgame.Application;

public class Client {

    public static final int WIDTH   = 1920;
    public static final int HEIGHT  = 1080;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;

	public static void main(String[] args) {
		//TODO: implement
		
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
