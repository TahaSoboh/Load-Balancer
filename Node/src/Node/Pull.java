
package Node;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;

//Pull
public class Pull extends Thread{    

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BufferedReader getIDS() {
        return IDS;
    }

    public void setIDS(BufferedReader IDS) {
        this.IDS = IDS;
    }

    public BufferedReader getMSGS() {
        return MSGS;
    }

    public void setMSGS(BufferedReader MSGS) {
        this.MSGS = MSGS;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getOutcoming() {
        return Outcoming;
    }

    public void setOutcoming(DataOutputStream Outcoming) {
        this.Outcoming = Outcoming;
    }

    String id;
    String msg;
    BufferedReader IDS;
    BufferedReader MSGS;    
    Socket socket;
    DataOutputStream Outcoming;
    
    public Pull(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try
        {            
            while(Boolean.TRUE)
            {                
                IDS = new BufferedReader(new InputStreamReader(System.in));
                MSGS = new BufferedReader(new InputStreamReader(System.in));  
                Outcoming = new DataOutputStream(socket.getOutputStream());
                
                id=IDS.readLine();                                
                if(id.equals("stop"))
                    break;
                
                //Push
                Outcoming.writeBytes(id + '\n');                
            }            
            
        } catch (IOException exp)
        {                        
        }           
    }    
}