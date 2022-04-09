package Console;

import GUI.TestGUIMain;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class TestMain {
    static private ArrayList<File> fileList;
    static private boolean quit;

    public static void main(String[] args){
        quit = false;
        int choice = -1, fileIndex, heuristicIndex;
        double[] result = null;

        /*
        Check the args
         */

        while(!quit){
            showMenu();
            do {
                System.out.print("Enter the index corresponding to your choice: ");
                choice = getChoice();
                if(choice>6)
                    System.out.println("Please use a valid entry.");
            }while (choice < 1 || choice > 6);

            switch(choice){
                case 1:
                    quit = true;
                    TestGUIMain.main(args);
                    break;
                case 2:
                    heuristicIndex = chooseHeuristic();
                    fileIndex = chooseFile() - 1;
                    result = compareHeight(heuristicIndex, fileIndex);
                    break;
                case 3:
                    heuristicIndex = chooseHeuristic();
                    fileIndex = chooseFile() - 1;
                    result = compareLength(heuristicIndex, fileIndex);
                    break;
                case 4:
                    heuristicIndex = chooseHeuristic();
                    fileIndex = chooseFile() - 1;
                    result = compareCPUTimeAtBuild(heuristicIndex, fileIndex);
                    break;
                case 5:
                    heuristicIndex = chooseHeuristic();
                    fileIndex = chooseFile() - 1;
                    result = compareCPUTimeForPainting(heuristicIndex, fileIndex);
                    break;
                case 6:
                    quit = true;
                    break;
            }

            if (result != null)
                System.out.println("Results:\n\tFirst: " + result[0] + "\n\tSecond: " + result[1]);
        }

        System.exit(0);
    }

    private static int getChoice(){
        Scanner input = new Scanner(System.in);
        try {
            int result = input.nextInt();
            return result;
        }catch(Exception e){
            System.out.println("Please enter only integer values.");
        }
        return -1; //error code
    }

    private static void showMenu(){
        System.out.println("Menu:");
        System.out.println("1. Open GUI application.");
        System.out.println("2. Compare height between trees.");
        System.out.println("3. Compare length between trees.");
        System.out.println("4. Compare CPU time used to build trees.");
        System.out.println("5. compare CPU time used by the painter's algorithm.");
        System.out.println("6. Quit.");
    }

    private static ArrayList<File> getFileRecursive(File f){
        ArrayList<File> returnList = new ArrayList<>();
        if (f.isFile())
            returnList.add(f);
        else if (f.isDirectory())
            for (File file : f.listFiles())
                returnList.addAll(getFileRecursive(file));

        return returnList;
    }

    private static void showSceneFiles() throws Exception{
        //get resources dir
        File resourcesSceneFiles = new File(TestMain.class.getResource("/scenes/").getFile());

        //list all files
        fileList = getFileRecursive(resourcesSceneFiles);

        int id = 1;

        for (File f : fileList){
            System.out.println(id + ". " + f.getName());
            id++;
        }
        System.out.println(id + ". Choose the path to your scene file.");
    }

    private static int chooseHeuristic(){
        int choice;

        System.out.println("1. Random.");
        System.out.println("2. First segment in the file.");
        System.out.println("3. Free split.");

        do {
            System.out.print("Enter the indexes corresponding to your choice.\nConcatenate your choices for the two heuristics." +
                    "\nExample: 12 means that the first tree will be built by the random heuristic and the second by taking the first segment in the set.");
            choice = getChoice();
            switch(choice){
                case 12:
                case 13:
                case 21:
                case 23:
                case 31:
                case 32:
                    return choice;
                default:
                    System.out.println("Please use a valid entry as explained above.");
                    break;
            }
        }while (true);
    }

    private static int chooseFile(){
        int choice;
        try {
            showSceneFiles();
        }catch(Exception e){
            System.out.println("Fatal error while trying to read files from the resource directory.");
            System.exit(0);
        }

        do{
            System.out.print("Enter the index corresponding to your choice:");
            choice = getChoice();
        }while(choice < 1 || choice > fileList.size() + 1);

        if (choice == fileList.size()+1){
            File file;
            String filePath;

            Scanner input = new Scanner(System.in);
            do {
                System.out.print("Select the path of the scene file you want: ");
                try {
                    filePath = input.next();
                    file = new File(filePath);
                    if (file.exists()) {
                        fileList.add(file);
                        return fileList.size();
                    }
                } catch (Exception e) {
                    System.out.println("Please use correct values.");
                }
            }while(true);
        }

        return choice;
    }

    private static double[] compareHeight(int heuristicIndex, int fileIndex){
        double[] results = {};
        return results;
    }

    private static double[] compareLength(int heuristicIndex, int fileIndex){
        double[] results = {};
        return results;
    }

    private static double [] compareCPUTimeAtBuild(int heuristicIndex, int fileIndex){
        double[] results = {};
        return results;
    }

    private static double [] compareCPUTimeForPainting(int heuristicIndex, int fileIndex){
        double[] results = {};
        return results;
    }
}
