package client;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;
import server.Server;
import server.Statebox;

public class Client extends Thread{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
