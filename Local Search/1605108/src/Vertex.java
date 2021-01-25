import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private int number;
    private List<Vertex>neighbourlist = new LinkedList();
    private int degree;
    private int colour = -1;

    public Vertex(int number) {
        this.number = number;
    }

    public int getColour() {
        return colour;
    }
    public void printvertices(){
        for (int i = 0; i <neighbourlist.size() ; i++) {
            System.out.print(neighbourlist.get(i).getNumber()+" ");
        }
    }
    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Vertex> getNeighbourlist() {
        return neighbourlist;
    }

    public void setNeighbourlist(List<Vertex> neighbourlist) {
        this.neighbourlist = neighbourlist;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
    public void addNeighbour(Vertex n){
       if(neighbourlist.contains(n))
           return;
        neighbourlist.add(n);
        degree++;
    }
}
