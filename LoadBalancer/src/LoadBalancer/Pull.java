
package LoadBalancer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;



//Pulling
public class Pull extends Thread{

    Socket socket;
    String id;
    String msg;    
    BufferedReader IDS;    
    BufferedReader MSGS;
    DataOutputStream Outcoming;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

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

    public DataOutputStream getOutcoming() {
        return Outcoming;
    }

    public void setOutcoming(DataOutputStream Outcoming) {
        this.Outcoming = Outcoming;
    }
    
    public Pull(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try
        {            
            while(Boolean.TRUE)
            {                
                //Read ID, Pull
                IDS = new BufferedReader(new InputStreamReader(System.in));
                //Read Message, Pull
                MSGS = new BufferedReader(new InputStreamReader(System.in));  
                //Push
                Outcoming = new DataOutputStream(socket.getOutputStream());                
                
                id=IDS.readLine();
                //Stop Node
                if(id.equals("stop"))
                    break;                             
                
                msg=MSGS.readLine();                
                //Push
                Outcoming.writeBytes(id + '\n');
                //Push
                Outcoming.writeBytes(msg + '\n');                  
            }
            socket.close();//Stop Node
            
        } catch (IOException exp){}        
    }    
}