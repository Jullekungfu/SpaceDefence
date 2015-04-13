package client;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;

import server.Server;
import server.Statebox;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import slimpleslickgame.Application;

public class Client extends Thread{

    public static int WIDTH   = 1920;
    public static int HEIGHT  = 1080;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) screenSize.getWidth();
		HEIGHT = (int) screenSize.getHeight();
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
    volatile private Socket connection;
    private Statebox statebox;
    private InputStreamReader in;

    public Client(Socket connection, Statebox statebox, String name){
        this.connection = connection;
        this.statebox = statebox;
        this.name = name;
        //getUserName();
    }
    
    //TODO: Implement to get info from game.
    public void run(){
        String line = new String();
        /*
		InputStream raw = connection.getInputStream();
		BufferedInputStream buffer = new BufferedInputStream(raw);
		in = new InputStreamReader(buffer, "UTF-8");
		*/
		while(true){
			 try {
				this.sleep((long) (1000 * Math.random()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    /*
			int c;
		    line = "";
		    while ((c = in.read( )) != '\n') {                    
		        line += (char) c;
		    }
		    if(line.contains("quit")){
		        connection.close();
		        break;
		    }
		    */
			line = "hej";
		    this.statebox.writeMessage(line.getBytes());
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
