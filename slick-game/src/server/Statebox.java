package server;

//TODO: This should contain a better buffer. Maybe a ringbuffer? Maybe one buffer per client?

public class Statebox{
	//TODO: why volatile??
    private volatile byte[] message;
    private byte currentId;

    public Statebox(){
        this.message = null;
        currentId = 0x0; // first player gets id 0
    }

    public synchronized byte[] readMessage(){
    	while(message == null){ 
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
    	
        byte[] temp = this.message;
        this.message = null;;
        notifyAll();
        return temp;
    }

    public synchronized void writeMessage(byte[] msg){
       while(message != null){ 
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        this.message = msg;
        notifyAll();
    }
}
