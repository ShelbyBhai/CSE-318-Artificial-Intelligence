import java.util.*;

class SortBrelaz implements Comparator<Variable>{

    @Override
    public int compare(Variable o1, Variable o2) {
        int diff = o1.getDom().size()-o2.getDom().size();
        if(diff == 0){
            int diffDynamic = o2.getDegreeDynamic()-o1.getDegreeDynamic();
            return diffDynamic;
        }
        else{
            return diff;
        }
    }
}

class SortDomDeg implements Comparator<Variable>{

    @Override
    public int compare(Variable o1, Variable o2) {
        int diff = o1.getDom().size()-o2.getDom().size();
        return diff ;
    }
}

public class Variable {
    int x,y;
    int degreeStatic = 0;
    int degreeDynamic = 0;
    int val = 0;
    private List<Integer> dom;

    public Variable(int x, int y, int degreeStatic, int degreeDynamic, int val, List<Integer> dom) {
        this.x = x;
        this.y = y;
        this.degreeStatic = degreeStatic;
        this.degreeDynamic = degreeDynamic;
        this.val = val;
        this.dom = dom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDegreeDynamic() {
        return degreeDynamic;
    }


    public void setVal(int val) {
        this.val = val;
    }

    public List<Integer> getDom() {
        return dom;
    }


    public static boolean updateDomain(int posX, int posY, List<Variable> variables, List<Variable> changedDomain, int value){

        boolean rowSame,colSame;

        for (int i = 0; i < variables.size(); i++) {
            Variable var = variables.get(i);
            rowSame = (var.x == posX)?true:false;
            colSame = (var.y == posY)?true:false;
            if(!(rowSame && colSame) && (rowSame||colSame)){
                if(var.dom.contains(value)){
                    var.dom.remove(new Integer(value));
                    changedDomain.add(var);
                    if(var.dom.size() == 0)
                        return false;
                    var.degreeDynamic = var.degreeDynamic - 1;
                }
            }
        }
        return true;
    }

    public static  void incrementDynamicDegree(int posX,int posY,List<Variable> variables){

        boolean rowSame,colSame;

        for (int i = 0; i < variables.size(); i++) {
            Variable var = variables.get(i);
            rowSame = (var.x == posX)?true:false;
            colSame = (var.y == posY)?true:false;
            if(!(rowSame && colSame) && (rowSame||colSame)){
                var.degreeDynamic = var.degreeDynamic + 1;
            }
        }
    }
    public static void addValueToDomain(List<Variable> changedDomain,int value){
        for (int i = 0; i < changedDomain.size(); i++) {
                changedDomain.get(i).dom.add(value);
        }
    }
}



