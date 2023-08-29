
package LoadBalancer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;

//Psuh
public class Push extends Thread{

    private final Socket socket;

    BufferedReader Incoming;            
    String msg;
    
    public Push(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIncoming() {
        return Incoming;
    }

    public void setIncoming(BufferedReader Incoming) {
        this.Incoming = Incoming;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void run(){
        try {
            while(Boolean.TRUE)
            {
                if(socket.isClosed())
                {
                    System.out.println("Closed");
                    break;
                }                        
                Incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg = Incoming.readLine();
                System.out.println(msg);
            }            
            
        } catch (IOException exp) {            
        }
    }    
}