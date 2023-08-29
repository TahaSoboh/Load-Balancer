package Sender;

public class Job {

    String ID;
    String Time;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public Job(String ID, String Time) {
        this.ID = ID;
        this.Time = Time;
    }

    public Job() {
    }

}
