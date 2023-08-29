
package LoadBalancer;

//Handle Nodes and its Frequency to Sort it Accouding To Round Robin Algorithm
public class Freq {
   int ID;
   int Freq;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Freq(int ID, int Freq) {
        this.ID = ID;
        this.Freq = Freq;
    }

    public int getFreq() {
        return Freq;
    }

    public void setFreq(int Freq) {
        this.Freq = Freq;
    }   
}
