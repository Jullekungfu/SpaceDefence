package server;

public class Statebox{
    volatile private byte[] message;

    public Statebox(){
        this.message = new byte[1];
    }

    synchronized public byte[] readMessage(){
        byte[] temp = this.message;
        this.message = new byte[1];
        notifyAll();
        return temp;
    }

    synchronized public void writeMessage(byte[] msg){
       while(!(this.message.length < 2)){
            try {
                wait();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        this.message = msg;
    }
}
