package server;

//TODO: This should contain a better buffer. Maybe a ringbuffer? Maybe one buffer per client?

public class Statebox{
	//TODO: why volatile??
    private volatile byte[] message;

    public Statebox(){
        this.message = new byte[1];
    }

    public synchronized byte[] readMessage(){
        byte[] temp = this.message;
        this.message = new byte[1];
        notifyAll();
        return temp;
    }

    public synchronized void writeMessage(byte[] msg){
       while(!(this.message.length < 2)){ //TODO: This logic... ;)
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        this.message = msg;
    }
}
