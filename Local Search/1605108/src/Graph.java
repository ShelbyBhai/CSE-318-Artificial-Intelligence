import java.util.LinkedList;
import java.util.List;

public class Graph {
    int num_vertex;
    Vertex[] vertices;
    int clr = 0;

    public Graph(int num_vertex, Vertex[] vertices) {
        this.num_vertex = num_vertex;
        this.vertices = vertices;
    }

    public void addEdge(Vertex v1, Vertex v2){
        v1.addNeighbour(v2);
        v2.addNeighbour(v1);
    }
    public int getNum_vertex() {
        return num_vertex;
    }

    public void setNum_vertex(int num_vertex) {
        this.num_vertex = num_vertex;
    }
    void printgraph(){
        for (int i = 0; i < num_vertex; i++) {
            //System.out.println(i + "->" + );
            System.out.print(vertices[i].getNumber()+"->");
            vertices[i].printvertices();
            System.out.println();
        }
//        for (int i = 0; i <num_vertex ; i++) {
//            System.out.println("Color of " + i +" " + vertices[i].getColour());
//        }
    }
    void printgraphcolor(){
        for (int i = 0; i <num_vertex ; i++) {
            System.out.println("Color of " + i +" " + vertices[i].getColour());
        }
    }
    public void coluring(){
//        int [] degrees = new int[num_vertex];
//        for (int i = 0; i <num_vertex ; i++) {
//            degrees[i] = vertices[i].getNeighbourlist().size();
//        }
        //Vertex[] sortedVertices = new Vertex[num_vertex];
        Vertex temp;
        for (int i = 0; i <num_vertex ; i++) {
            for (int j = 0; j <num_vertex-i-1 ; j++) {
                if(vertices[j].getDegree()<vertices[j+1].getDegree())
                {
                    temp = vertices[j];
                    vertices[j] = vertices[j+1];
                    vertices[j+1] = temp;
                }
            }
        }
        boolean shift = true;
        for (int i = 0; i <num_vertex ; i++) {
            Vertex v = vertices[i];
            for (int j = 0; j <clr ; j++) {
                for (int k = 0; k <v.getNeighbourlist().size() ; k++) {
                    Vertex v1 = v.getNeighbourlist().get(k);
                    if(v1.getColour() == j)
                    {
                        shift = true;
                        break;
                    }
                    else {
                        shift = false;
                    }
                }
                if(!shift){
                    v.setColour(j);
                    vertices[i] = v;
                    break;
                }
            }
            if(shift)
            {
                clr++;
                v.setColour(clr);
                vertices[i] = v;
            }
        }
    }
}
