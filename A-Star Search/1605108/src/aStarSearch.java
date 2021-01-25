import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class aStarSearch {
    Node BeginNode;
    int nodeNum;
    int expandedNodes;
    public aStarSearch(Node init) {
        BeginNode = init;
        nodeNum = 0;
        expandedNodes = 0;
    }
    public Node execute() {
        if (BeginNode.isThisTheGoalBoard()) {
            return BeginNode;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(new CompareNode());
        HashMap<Node, Integer> map = new HashMap<>();
        pq.add(BeginNode);
        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (u.isThisTheGoalBoard()) {
                return u;
            }
            map.put(u, nodeNum);
            expandedNodes++;
            List<Node> successors = u.getChild();
            for (Node v : successors) {
                if (!map.containsKey(v)) {
                    nodeNum++;
                    pq.add(v);
                }
            }
        }
        return null;
    }
    public void printAllMoves() {
        Node node = execute();
        if (node == null) {
            System.out.println("No solution found!");
            return;
        }
        Node[] arr = new Node[1000];
        int t = 0;
        while (node != null) {
            arr[t] = node;
            t++;
            node = node.getPreviousNode();
        }
        t--;
        if (t == 0) {
            System.out.println("It was itself the GOAL State. 0 moves required.");
        }
        else {
            System.out.println("The solution moves are: ");
            for (int i = t; i >= 0; i--) {
                System.out.println("Move #" + (t - i));
                arr[i].printPuzzle();
            }
            System.out.println("It took " + t + " moves");
            System.out.println("Node closed: " + expandedNodes);
        }
    }
}
