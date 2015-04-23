package client;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;

import server.Server;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import slimpleslickgame.Application;

public class Client{

    public static int WIDTH   = 1280;
    public static int HEIGHT  = 720;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//WIDTH = (int) screenSize.getWidth();
		//HEIGHT = (int) screenSize.getHeight();
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
	
	private String name;
    private Socket connection;
    private InputStream in;
    private OutputStream out;
    private InThread inputThread;

    public Client(Socket connection, String name){
        this.connection = connection;
        this.name = name;
        
        try{
        	in = connection.getInputStream();
    		out = connection.getOutputStream();
        	
        	AppGameContainer container = new AppGameContainer(new Application("SpaceDefence"));
            container.setDisplayMode(WIDTH, HEIGHT, false);
            container.setTargetFrameRate(FPS);
            container.setShowFPS(true);
            container.start();
            
		} catch (SlickException e){
			e.printStackTrace();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
    }
    

    public void writeMessage(byte[] msg){
        try{
            Writer writer;
            writer = new OutputStreamWriter(connection.getOutputStream()); 
            writer.write(msg.toString(), 0, msg.length);
            writer.flush();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}
