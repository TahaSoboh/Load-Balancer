
package LoadBalancer;

//The Messages Whic we Using it to Determine The Node And Source, Destination
public class Message {
    //Sender ID
    String SID;
    //Reciver ID
    String RID;
    //Message Content
    String Content;

    public Message(String SID, String RID, String MSG) {
        this.SID = SID;
        this.RID = RID;
        this.Content = MSG;
    }    
}
