import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("**********Torronto Results***********");
        System.out.println("CAR-91 " + " Time Slots : 35 " + " Penalty : 4.42");
        System.out.println("CAR-92 " + " Time Slots : 32 " + " Penalty : 3.74");
        System.out.println("KFU-83 " + " Time Slots : 20 " + " Penalty : 12.96");
        System.out.println("TRE-92 " + " Time Slots : 23 " + " Penalty : 7.75");
        System.out.println("YOR-83 " + " Time Slots : 21 " + " Penalty : 34.84");
        System.out.println("**********My Scheme Results************");
        String[] coursefilename = {"car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs"};
        String[] studentfilename = {"car-s-91.stu", "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu"};
        String[] outputfilename = {"car-s-91.sol", "car-f-92.sol", "kfu-s-93.sol", "tre-s-92.sol", "yor-f-83.sol"};
        for (int f = 0; f < 5; f++) {
            File file = new File(coursefilename[f]);
            int num_courses = 0;
            try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
                while (lnr.readLine() != null) ;
                num_courses = lnr.getLineNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // System.out.println(num_courses);
            Vertex[] vertices = new Vertex[num_courses + 1];
            for (int i = 0; i < num_courses; i++) {
                vertices[i] = new Vertex(i + 1);
            }
            Graph graph = new Graph(num_courses, vertices);
            try {
                FileInputStream fis = new FileInputStream(studentfilename[f]);
                Scanner sc = new Scanner(fis);
                while (sc.hasNextLine()) {
                    String[] test = sc.nextLine().split(" ");
                    for (int i = 0; i < test.length - 1; i++) {
                        for (int j = i + 1; j < test.length; j++) {
                            graph.addEdge(vertices[Integer.parseInt(test[i]) - 1], vertices[Integer.parseInt(test[j]) - 1]);
                        }
                    }
                    //System.out.println();
                }
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            graph.coluring();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputfilename[f], true))) {
                for (int i = 0; i <num_courses ; i++) {
                    int temp = vertices[i].getNumber();
                    writer.append((temp) + " " +(vertices[i].getColour()) + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file2 = new File(studentfilename[f]);
            int num_students = 0;
            try (LineNumberReader lnr2 = new LineNumberReader(new FileReader(file2))) {
                while (lnr2.readLine() != null) ;
                num_students = lnr2.getLineNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int num_student = num_students;
           // System.out.println(num_student);
            int difference = 0;
            //int penalty = 0 ;
            try {
                FileInputStream fis2 = new FileInputStream(studentfilename[f]);
                Scanner sc2 = new Scanner(fis2);
                while (sc2.hasNextLine()) {
                    String[] test2 = sc2.nextLine().split(" ");
                    for (int i = 0; i < test2.length - 1; i++) {
                        for (int j = i + 1; j < test2.length; j++) {
                            if (Math.abs(vertices[Integer.parseInt(test2[i]) - 1].getColour() - vertices[Integer.parseInt(test2[j]) - 1].getColour()) == 1)
                                difference = difference + 16;
                            else if (Math.abs(vertices[Integer.parseInt(test2[i]) - 1].getColour() - vertices[Integer.parseInt(test2[j]) - 1].getColour()) == 2)
                                difference = difference + 8;
                            else if (Math.abs(vertices[Integer.parseInt(test2[i]) - 1].getColour() - vertices[Integer.parseInt(test2[j]) - 1].getColour()) == 3)
                                difference = difference + 4;
                            else if (Math.abs(vertices[Integer.parseInt(test2[i]) - 1].getColour() - vertices[Integer.parseInt(test2[j]) - 1].getColour()) == 4)
                                difference = difference + 2;
                            else if (Math.abs(vertices[Integer.parseInt(test2[i]) - 1].getColour() - vertices[Integer.parseInt(test2[j]) - 1].getColour()) == 5)
                                difference = difference + 1;
                        }
                    }
                }
                sc2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            double penalty = (difference/num_student);
            if(f == 0){
                System.out.println("CAR-91 " + " Time Slots : " + graph.clr + " Penalty : " + penalty);
                //System.out.println(difference + " " + num_student);
            }
            if(f == 1){
                System.out.println("CAR-92 " + " Time Slots : " + graph.clr + " Penalty : " + penalty);
            }
            if(f == 2){
                System.out.println("KFU-83 " + " Time Slots : " + graph.clr + " Penalty : " + penalty);
            }
            if(f == 3){
                System.out.println("TRE-92 " + " Time Slots : " + graph.clr + " Penalty : " + penalty);
            }
            if(f == 4){
                System.out.println("YOR-83 " + " Time Slots : " + graph.clr + " Penalty : " + penalty);
            }
        }
        System.out.println();
    }
}
