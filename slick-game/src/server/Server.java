package server;

import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;

import client.Client;

public class Server {
	private ServerSocket ss;
    
    public static void main(String[] args){
        Server si = new Server();
        si.startServer();
    }

    public Server(){
        try{
            ss = new ServerSocket(30001);
            System.out.println("Server booting...");
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    public void startServer(){
        Socket connection;
        Statebox mb = new Statebox();
        UpdateToClient rt = new UpdateToClient(mb);
        rt.start();
        try{
            while(true){
                connection = ss.accept();
                new PlayerParticipant(connection, mb).start();
                rt.addSocket(connection);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}

class UpdateToClient extends Thread{
    private Statebox statebox;
    private byte[] msg;
    private Vector<Socket> clients;
    private OutputStream writer;

    public UpdateToClient(Statebox statebox){
        this.statebox = statebox;
        this.clients = new Vector<Socket>();
    }

    public void addSocket(Socket s){
    	clients.add(s);
    }
    
    public void run(){
        while(true){          
            msg = this.statebox.readMessage();    
            
            if(!(msg.length < 2)){
                for(int i = 0 ; i < clients.size(); i++){
                	try{
                        writer = clients.get(i).getOutputStream(); 
                        writer.write(msg);
                        writer.flush();
                	} catch(IOException ioe){
                		ioe.printStackTrace();
                	}
                }
            }
        }
    }
}
