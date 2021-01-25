import java.util.*;

public class ForwardChecking {
    public static int numBackTrack=0;
    public static int numNode=0;
    public static boolean isSolved = false;

    public static boolean checkSolvability(){
        for (int i = 0; i < Main.squareSize; i++) {
            for (int j = 0; j < Main.squareSize; j++) {
                int value = Main.latinSquare[i][j];
                if(value == 0)
                    return false;
                for (int k = 0; k <Main.squareSize; k++) {
                    if(Main.latinSquare[i][k] == value && j!=k)
                        return false;
                }
                for (int k = 0; k < Main.squareSize; k++) {
                    if(Main.latinSquare[k][j] == value && k!=i)
                        return false;
                }
            }
        }
        return true;
    }

    public static Variable brelaz(){
        if(Main.variableList.size()>0){
            Collections.sort(Main.variableList,new SortBrelaz());
            Variable v = Main.variableList.get(0);
            Main.variableList.remove(0);
            return v;
        }
        return null;
    }

    public static Variable domDeg(){
        if(Main.variableList.size()>0){
            Collections.sort(Main.variableList,new SortDomDeg());
            Variable v=Main.variableList.get(0);
            Main.variableList.remove(0);
            return v;
        }
        return null;
    }

    public static void resetFC(){
        Main.squareSize = Main.squareSize;
        Main.latinSquare = Main.latinSquare;
        Main.variableList=Main.variableList;

        numBackTrack = 0;
        numNode = 0;
        isSolved = false;
    }

    public static void ForwardCheckingAlgo(String variableOrderingType){

        if(isSolved || Main.variableList.size()<=0)
            return;
        Variable var;
        if(variableOrderingType.equalsIgnoreCase("brelaz"))
            var = brelaz();
        else if(variableOrderingType.equalsIgnoreCase("domdeg"))
            var = domDeg();
        else
            var = null;

        if(var == null)
            return;

        for (int i = 0; i < var.getDom().size(); i++) {
            numNode = numNode + 1;
            int value = var.getDom().get(i);
            List<Variable> domainChange = new ArrayList<>();
            int argX = var.getX();
            int argY = var.getY();
            boolean isUpdated = Variable.updateDomain(argX, argY,Main.variableList,domainChange,value);
            if(isUpdated){
                var.setVal(value);
                Main.latinSquare[argX][argY] = value;
                if(Main.variableList.size() == 0)
                    isSolved = true;
                ForwardCheckingAlgo(variableOrderingType);
                Variable.addValueToDomain(domainChange,value);
                Variable.incrementDynamicDegree(argX, argY, Main.variableList);
                if(isSolved)
                    break;
            }
            else{
                Variable.addValueToDomain(domainChange,value);
                Variable.incrementDynamicDegree(argX, argY, Main.variableList);
                numBackTrack = numBackTrack + 1;
            }
        }
        Main.variableList.add(var);
    }
}
