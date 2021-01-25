import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        int T, t = 0;
        T = in.nextInt();
        while (t < T-1) {
            t++;
            int [][] GoalPuzzle = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    GoalPuzzle[i][j] = in.nextInt();
                }
            }
            System.out.println("Puzzle Number: " + t + "\n");
            int[][] GivenPuzzle = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    GivenPuzzle[i][j] = in.nextInt();
                }
            }
            Node BeginNode = new Node(GivenPuzzle, 0, null);
            System.out.println("The given puzzle is: ");
            BeginNode.printPuzzle();
            if (BeginNode.isSolvable()) {
                System.out.println("This puzzle is solvable. ");
                Node temp;
                aStarSearch search;
                System.out.println("************************");
                System.out.println("With Manhattan Heuristic: ");
                HeuristicType.Type = heuristicName.Man;
                temp = BeginNode;
                search = new aStarSearch(temp);
                search.printAllMoves();
                System.out.println("\nPath Cost : " + temp.getPriorityFunction());
                System.out.println("**************************");
                System.out.println("With MisPlaced Tiles Heuristic: ");
                HeuristicType.Type = heuristicName.Ham;
                temp = BeginNode;
                search = new aStarSearch(temp);
                search.printAllMoves();
                System.out.println("\nPath Cost : " + temp.getPriorityFunction());
                System.out.println("*************************");
            }
            else {
                System.out.println("This is a NOT Solvable puzzle");
            }
        }
    }
}

