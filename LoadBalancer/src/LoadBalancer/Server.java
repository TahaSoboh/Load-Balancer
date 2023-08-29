package LoadBalancer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import Sender.Job;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


public class Server {
        
    //Port Number
    static int port;
    //Contains the Jobs
    static ArrayList<Job> jobs = new ArrayList<Job>();
    //Contains the Nodes
    static ArrayList<NodeThread> Nodes = new ArrayList<NodeThread>();

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Server.port = port;
    }

    public static ArrayList<Job> getJobs() {
        return jobs;
    }

    public static void setJobs(ArrayList<Job> jobs) {
        Server.jobs = jobs;
    }

    public static ArrayList<NodeThread> getNodes() {
        return Nodes;
    }

    public static void setNodes(ArrayList<NodeThread> Nodes) {
        Server.Nodes = Nodes;
    }

    //Display the Node List
    public static void ViewNodes(ArrayList<Freq> F) {
        System.out.println("((The List After Sorting))");
        System.out.println("--------------------------");
        for (Freq node : F) {
            System.out.println("Node ID = " + node.ID + " , Frequency = " + node.Freq);
        }
        System.out.println("--------------------------");
    }
        
    //What Type? 1 or 0?
    public static String Type(String s) {
        if (s.charAt(0) == '1') {
            return "Node";
        }
        return "Job";
    }
    
    //Bubble Sort According to Freq
    public static ArrayList<Freq> SortList(ArrayList<Freq> F) {
        for (int i = 0; i < F.size(); i++) {
            for (int j = 1; j < (F.size() - i); j++) {
                if (F.get(j - 1).Freq > F.get(j).Freq) {
                    Freq temp = new Freq(F.get(j - 1).ID, F.get(j - 1).Freq);
                    F.get(j - 1).ID = F.get(j).ID;
                    F.get(j - 1).Freq = F.get(j).Freq;
                    F.get(j).ID = temp.ID;
                    F.get(j).Freq = temp.Freq;
                }
            }
        }
        return F;
    }


    //Destribute Jobs to Nodes
    public static void Distribute(Message msg) throws IOException {
        for (NodeThread nt : Nodes) {            
            nt.DeliverMessage(msg);
        }
    }
    
    //Combine Jobs,Nodes
    public static void LoadBalance(ArrayList<Freq> F, Push push, Pull pull) throws IOException {        
        Job job = new Job();
        int temp = 1;
        if (!F.isEmpty()) {
            for (int counter = 0; counter < jobs.size(); counter++) {
                //Get the Specific Job
                job = jobs.get(counter);
                //We Must Respect The Form of String to Occupy with: /Node/Push
                Distribute(new Message("((Log))", null, "Node ID = " + F.get(0).ID + " = Job ID = " + job.getID() + " = Need Time = " + job.getTime() + '\n'));
                
                //Increase Freuency
                F.get(0).Freq++;
                //Sort After Assign Job to Node
                F = SortList(F);
                //Disply Nodes After Sort
                ViewNodes(F);
                
                //Start The Node to Pull and Push
                push.start();
                pull.start();
                temp++;
                //Remove The Assigned Job From List
                jobs.remove(job);
                if (temp > F.size() && !jobs.isEmpty()) {
                    temp = 1;
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Port:");
        port = scan.nextInt();
        
        ServerSocket socket = new ServerSocket(port);
        ArrayList<Freq> nodes = new ArrayList<>();        
        
        System.out.println("Start Load Balancer...");
        int NID;
        Job job;
        String JID, Time;        
        String str;        
        while (Boolean.TRUE)
        {
            Socket Channel = socket.accept();
            NodeThread node = new NodeThread(Channel);
            Push pull = new Push(Channel);
            Pull push = new Pull(Channel);
            BufferedReader buf = new BufferedReader(new InputStreamReader(Channel.getInputStream()));
            str = buf.readLine();

            if (Type(str).equals("Node")) {
                if (!str.contains("stop")) {
                    //the first letter is: 1
                    //Get The Node ID
                    NID = Integer.parseInt(str.substring(1, str.length()));                    
                    nodes.add(new Freq(NID, 0));
                    //Sort
                    nodes = SortList(nodes);
                    //Display
                    ViewNodes(nodes);
                    node.ID = Integer.toString(NID);
                    Nodes.add(node);
                    //Start Pull and Push
                    node.start();
                    System.out.println("New Node Has Been Joined: " + NID + " - Port: " + Channel.getPort() + " - IP: " + Channel.getRemoteSocketAddress());                    
                } 
                else
                    System.out.println("!!Node Closed!!");                
            } else {
                StringTokenizer tokenizer = new StringTokenizer(str.substring(1, str.length()));                
                JID = tokenizer.nextToken("-");
                Time = tokenizer.nextToken("-");
                job = new Job(JID, Time);
                jobs.add(job);
                System.out.println("New Job Has Been Joined: " + JID + " - Need Time in Seconds: " + Time + " - on Port : " + Channel.getPort() + " - IP: " + Channel.getRemoteSocketAddress());
            }
            
            LoadBalance(nodes, pull, push);
        }
    }
}
