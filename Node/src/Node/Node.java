
package Node;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;

public class Node {

    static String ID;
    static int port;
    static String host;

    public static void main(String argv[]) throws UnknownHostException, IOException {        
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Port:");
        port = scan.nextInt();
        
        scan = new Scanner(System.in);
        System.out.println("Enter Host IP:");
        host = scan.nextLine();        
        
        Socket socket = new Socket(host, port);

        //When we Push From Side we Pull from the Other Side
        Push Pull = new Push(socket);
        Pull push = new Pull(socket);
        DataOutputStream Outcoming = new DataOutputStream(socket.getOutputStream());

        scan = new Scanner(System.in);
        System.out.println("Node ID:");
        ID = scan.nextLine();

        //1: Node, 0: Job        
        Outcoming.writeBytes(("1" + ID) + '\n');
        Outcoming.flush();
                
        push.start();
        Pull.start();
        System.out.println("((Node Started))");
        while(!socket.isClosed())
        {
        }
    }
}
