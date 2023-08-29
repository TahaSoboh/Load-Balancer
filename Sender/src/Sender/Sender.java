package Sender;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class Sender {

    static int port;
    static String host;

    public static void main(String argv[]) throws UnknownHostException, IOException {
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Port:");
        port = scan.nextInt();
        
        scan = new Scanner(System.in);
        System.out.println("Enter Host IP:");
        host = scan.nextLine();        
        
        Socket socket = new Socket(host, port);//may changed in case we run the project in other machines
        DataOutputStream Outcoming = new DataOutputStream(socket.getOutputStream());
        Job j = new Job();
        
        Scanner s = new Scanner(System.in);
        System.out.println("Job ID:");
        j.ID = s.nextLine();  // Read From Console
        System.out.println("Time:");
        j.Time = s.nextLine();
        
        //Push to Server
        //We Reffer 1 as Node and 0 as Job
        Outcoming.writeBytes(("0" + j.ID + "-" + j.Time) + '\n');
        Outcoming.flush();//Push
    }
}
