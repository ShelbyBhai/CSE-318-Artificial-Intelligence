import java.io.*;
import java.util.*;

public class Main {
    public static int squareSize;
    public static int [][] latinSquare;
    public static List<Variable> variableList;

    public static void initialize(){
        variableList = new ArrayList<>();
        int zeroCounter = -2;
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if(latinSquare[i][j] == 0){
                    List<Integer> domainList = new ArrayList<Integer>();
                    for (int k = 0; k < squareSize; k++) {
                        domainList.add(k+1);
                    }
                    for (int k = 0; k < squareSize; k++) {
                        if(latinSquare[i][k]==0){
                            zeroCounter = zeroCounter + 1;
                        }
                        else if(latinSquare[i][k] != 0){
                            domainList.remove(new Integer(latinSquare[i][k]));
                        }
                    }
                    for (int k = 0; k < squareSize; k++) {
                        if(latinSquare[k][j] == 0){
                            zeroCounter = zeroCounter + 1;
                        }
                        else if(latinSquare[k][j]!=0){
                            domainList.remove(new Integer(latinSquare[k][j]));
                        }
                    }
                    variableList.add(new Variable(i,j,2*(squareSize - 1),zeroCounter,0 ,domainList));
                }
            }
        }
    }

    public static void printBoard(){
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                System.out.print(latinSquare[i][j]+"  ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void fileReader(String fileName) throws IOException {
        int countLine=0;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string;
        string = reader.readLine();
        squareSize =Integer.parseInt(string.substring(2,string.length()-1));
        latinSquare = new int[squareSize][squareSize];

        while ((string = reader.readLine()) != null){
            countLine++;
            if(countLine>2 && countLine< squareSize+2){
                String []test = string.substring(0,string.length()-2).split(",");
                for (int i = 0; i < squareSize; i++) {
                    latinSquare[countLine - 3][i]=Integer.parseInt(test[i].trim());
                }
            }
            else if(countLine == squareSize + 2){
                String []test = string.substring(0,string.length()-4).split(",");
                for (int i = 0; i < squareSize; i++) {
                    latinSquare[squareSize - 1][i]=Integer.parseInt(test[i].trim());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String []fileNames ={"d-10-01.txt.txt","d-10-06.txt.txt","d-10-07.txt.txt","d-10-08.txt.txt","d-10-09.txt.txt","d-15-01.txt.txt"};

        System.out.println("********Forward Checking with Variable ordering heuristic DOMDEG*********");
        System.out.println();
        for (int i = 0; i < fileNames.length; i++) {
            fileReader(fileNames[i]);
            initialize();
            System.out.println("********Latin Square before solution**********");
            printBoard();
            ForwardChecking.ForwardCheckingAlgo("domdeg");
            System.out.println("********Latin Square after solution********");
            printBoard();
            System.out.println("Latin Square Solved : " + ForwardChecking.checkSolvability());
            System.out.println("# of Nodes : " + ForwardChecking.numNode);
            System.out.println("# of Backtracks : " + ForwardChecking.numBackTrack);
            System.out.println();
            ForwardChecking.resetFC();
        }

        System.out.println();
        System.out.println();

        System.out.println("**********Forward Checking with Variable ordering heuristic BRELAZ************");
        System.out.println();

        for (int i = 0; i < fileNames.length; i++) {
            fileReader(fileNames[i]);
            initialize();
            System.out.println("*********Latin Square before solution*********");
            printBoard();
            ForwardChecking.ForwardCheckingAlgo("brelaz");
            System.out.println("********Latin Square after solution********");
            printBoard();
            System.out.println("Latin Square Solved : " + ForwardChecking.checkSolvability());
            System.out.println("# of Nodes : " + ForwardChecking.numNode);
            System.out.println("# of Backtracks : " + ForwardChecking.numBackTrack);
            System.out.println();
            ForwardChecking.resetFC();
        }
    }
}

