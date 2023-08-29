package Node;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

//Push
public class Push extends Thread {

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

    private final Socket socket;

    BufferedReader Incoming;
    String msg;

    public Push(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (Boolean.TRUE) {
                if (socket.isClosed()) {
                    System.out.println("is Closed");
                    break;
                }
                Incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg = Incoming.readLine();
                System.out.println(msg);
                
                //((Log)) = Node ID = 5 = Job ID = 10 = Job Time = 300
                StringTokenizer token = new StringTokenizer(msg);                
                String temp;
                temp = token.nextToken("=");
                temp = token.nextToken("=");
                String NID = token.nextToken("=");
                //Assign Job to Its Node
                if (NID.trim().equals(Node.ID)) {
                    temp = token.nextToken("=");
                    String JID = token.nextToken("=");
                    temp = token.nextToken("=");
                    String Time = token.nextToken("=");
                    Thread.sleep(Long.parseLong(Time.trim()));
                    DataOutputStream Outcoming = new DataOutputStream(socket.getOutputStream());
                    System.out.println("Message From Node: " + NID + " That it has Finished Job: " + JID + '\n');
                    Outcoming.writeBytes("Node: " + NID + " Finished Job: " + JID + '\n');
                }

            }

        } catch (IOException exp) {
            System.out.println("Closed");
        } catch (InterruptedException exp) {            
        }
    }
}