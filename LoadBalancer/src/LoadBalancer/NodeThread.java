package LoadBalancer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;

//Nodes will Push and Pull messages and notifications 
public class NodeThread extends Thread {

    Socket socket;
    String ID;
    String MSG;

    public NodeThread(Socket socket) {
        this.socket = socket;
    }

    public String getID() {
        return ID;
    }

    @Override
    public void run() {
        try {
            while (Boolean.TRUE) {
                //Pull From Socket
                BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                MSG = buf.readLine();                
                System.out.println(MSG);
                //Push to Socket
                DataOutputStream Outcoming = new DataOutputStream(socket.getOutputStream());
                Outcoming.flush();//Push
            }
        } catch (Exception exp) {
        }
    }

    public void DeliverMessage(Message MSG) throws IOException {
        DataOutputStream Outcoming = new DataOutputStream(socket.getOutputStream());
        //Hold From Where the Message, and What The Contnet
        Outcoming.writeBytes(MSG.SID + " = " + MSG.Content);
    }
}
