package server;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.lang.Math;
import java.lang.Object;

import client.Client;

public class Server {
	private ServerSocket ss;
    private Vector<Client> chatClients;
    
    public static void main(String[] args){
        Server si = new Server();
        si.startServer();
    }
	

    public Server(){
        chatClients = new Vector<Client>();
        try{
            ss = new ServerSocket(30001);
            System.out.println("Server booting...");
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    public void startServer(){
        Socket connection;
        Client cc;
    
        Statebox mb = new Statebox();

        ReadThread rt = new ReadThread(mb, chatClients);
        rt.start();
        
        try{
            while(true){
                connection = ss.accept();
                
                //cc = new Client(connection, mb, "User-" + chatClients.size());
                //cc.start();
                //chatClients.add(cc);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
    }

	

class Statebox{
    volatile private String message;

    public Statebox(){
        this.message = new String();
    }

    synchronized public String readMessage(){
        String temp = this.message;
        this.message = new String();
        notifyAll();
        return temp;
    }

    synchronized public void writeMessage(String msg){
       while(!this.message.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        this.message = msg;
    }
}

class ReadThread extends Thread{
    private Statebox statebox;
    private String msg;
    private Vector<Client> clients;

    public ReadThread(Statebox statebox, Vector<Client> clients){
        this.statebox = statebox;
        this.clients = clients;
    }

    public void run(){
        while(true){
            try{
                this.sleep((long)(Math.random() * 1000));
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
            msg = this.statebox.readMessage();            
            if(!msg.isEmpty()){
                for(int i = 0 ; i < chatClients.size(); i++){
                    //clients.get(i).writeMessage(msg);
                }
            }
        }
    }
}


}
