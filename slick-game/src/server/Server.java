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
        ReadThread rt = new ReadThread(mb);
        rt.start();
        try{
            while(true){
                connection = ss.accept();
                rt.addSocket(connection);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}

class ReadThread extends Thread{
    private Statebox statebox;
    private byte[] msg;
    private Vector<Socket> clients;
    private Writer writer;

    public ReadThread(Statebox statebox){
        this.statebox = statebox;
        this.clients = new Vector<Socket>();
    }

    public void addSocket(Socket s){
    	clients.add(s);
    }
    
    public void run(){
        while(true){
            try{
                this.sleep((long)(Math.random() * 1000));
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
            msg = this.statebox.readMessage();    
            
            if(!(msg.length < 2)){
                for(int i = 0 ; i < clients.size(); i++){
                	try{
                        writer = new OutputStreamWriter(clients.get(i).getOutputStream()); 
                        writer.write(msg.toString(), 0, msg.length);
                        writer.flush();
                	} catch(IOException ioe){
                		ioe.printStackTrace();
                	}
                }
            }
        }
    }
}
