import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private int[][] Puzzle;
    private int movesTillNow;
    private Node previousNode;
    private int[][] goalState;
    private position_x_y[] goalPositionOfEachNumber, currentPositionOfEachNumber;
    public Node(int[][] puzzle, int movesTillNow, Node previousNode) {
        this.Puzzle = puzzle;
        this.movesTillNow = movesTillNow;
        this.previousNode = previousNode;
        int t = 1;
        goalState = new int[4][4];
        goalPositionOfEachNumber = new position_x_y[25];
        currentPositionOfEachNumber = new position_x_y[25];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                goalState[i][j] = t ;
                if(i==3 && j==3)
                    goalState[i][j]=0;
                goalPositionOfEachNumber[t%16] = new position_x_y(i, j);
                t++;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currentPositionOfEachNumber[Puzzle[i][j]] = new position_x_y(i, j);
            }
        }
    }
    public Node getPreviousNode() {
        return previousNode;
    }
    public boolean isThisTheGoalBoard() {
        boolean flag = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Puzzle[i][j] != goalState[i][j]) {
                    flag = false;
                }
            }
        }
        return flag;
    }
    public boolean isSolvable() {
        int inversionNo = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    if (Puzzle[i][k] != 0 && Puzzle[i][j] != 0) {
                        if (Puzzle[i][k] < Puzzle[i][j]) {
                            inversionNo++;
                        }
                    }
                }
                for (int k = i + 1; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        if (Puzzle[k][l] != 0 && Puzzle[i][j] != 0) {
                            if (Puzzle[k][l] < Puzzle[i][j]) {
                                inversionNo++;
                            }
                        }
                    }
                }
            }
        }
        int tem;
        System.out.println(inversionNo);
        tem = inversionNo + getblankPosition().row;
        return tem % 2 != 0;
    }
    public position_x_y getblankPosition() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Puzzle[i][j] == 0) {
                    return new position_x_y(i, j);
                }
            }
        }
        return null;
    }
    public int[][] copy2DArray(int tem[][]) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(Puzzle[i],0,tem[i],0,4);
        }
        return tem;
    }
    public int getPriorityFunction() {
        if (HeuristicType.Type == heuristicName.Man) {
            return movesTillNow + ManhattanDistance();
        } else if (HeuristicType.Type == heuristicName.Ham) {
            return movesTillNow + misPlacedTiles();
        }
        return movesTillNow;
    }
    public int ManhattanDistance() {
        int mandistance = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Puzzle[i][j] != 0) {
                    mandistance += (Math.abs(i - goalPositionOfEachNumber[Puzzle[i][j]].row)
                            + Math.abs(j - goalPositionOfEachNumber[Puzzle[i][j]].coloumn));
                }
            }
        }
        return mandistance;
    }
    public int misPlacedTiles() {
        int misPlaced = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Puzzle[i][j] != 0) {
                    if (Puzzle[i][j] != goalState[i][j]) {
                        misPlaced++;
                    }
                }
            }
        }
        return misPlaced;
    }
    public List<Node> getChild() {
        List<Node> child = new ArrayList<>();
        position_x_y emptyPos = getblankPosition();
        int[][] tempBoard ;
        if (emptyPos != null) {
            int emptyRow, emptyColumn, temp;
            emptyRow = emptyPos.row;
            emptyColumn = emptyPos.coloumn;
            if (emptyRow - 1 >= 0) {
                tempBoard = new int[4][4];
                tempBoard = copy2DArray(tempBoard);
                temp = tempBoard[emptyRow - 1][emptyColumn];
                tempBoard[emptyRow - 1][emptyColumn] = tempBoard[emptyRow][emptyColumn];
                tempBoard[emptyRow][emptyColumn] = temp;
                child.add(new Node(tempBoard, movesTillNow + 1, this));
            }
            if (emptyColumn - 1 >= 0) {
                tempBoard = new int[4][4];
                tempBoard = copy2DArray(tempBoard);
                temp = tempBoard[emptyRow][emptyColumn - 1];
                tempBoard[emptyRow][emptyColumn - 1] = tempBoard[emptyRow][emptyColumn];
                tempBoard[emptyRow][emptyColumn] = temp;
                child.add(new Node(tempBoard, movesTillNow + 1, this));
            }
            if (emptyRow + 1 < 4) {
                tempBoard = new int[4][4];
                tempBoard = copy2DArray(tempBoard);
                temp = tempBoard[emptyRow + 1][emptyColumn];
                tempBoard[emptyRow + 1][emptyColumn] = tempBoard[emptyRow][emptyColumn];
                tempBoard[emptyRow][emptyColumn] = temp;
                child.add(new Node(tempBoard, movesTillNow + 1, this));
            }
            if (emptyColumn + 1 < 4) {
                tempBoard = new int[4][4];
                tempBoard = copy2DArray(tempBoard);
                temp = tempBoard[emptyRow][emptyColumn + 1];
                tempBoard[emptyRow][emptyColumn + 1] = tempBoard[emptyRow][emptyColumn];
                tempBoard[emptyRow][emptyColumn] = temp;
                child.add(new Node(tempBoard, movesTillNow + 1, this));
            }
        }
        return child;
    }
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.Puzzle);
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (node.Puzzle[i][j] != Puzzle[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                string += "" + Puzzle[i][j] + " ";
            }
            string += "\n";
        }
        string += "////////////";
        return string;
    }
    public void printPuzzle() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(Puzzle[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("////////////////");
    }
}
class position_x_y {
    int row, coloumn;
    public position_x_y(int row, int coloumn) {
        this.row = row;
        this.coloumn = coloumn;
    }
}
