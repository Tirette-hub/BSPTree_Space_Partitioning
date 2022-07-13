package Console;

import DataStructure.*;
import GUI.TestGUIMain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Main class that runs the program.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class TestMain {
    static final public double epsilon = 1e-4;

    static private ArrayList<String> fileList;
    static final private BSPTree<Segment>[] trees = new BSPTree[2];
    static final private Timer t1 = new Timer(), t2 = new Timer();
    static final private File jarFile = new File(TestMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    public static void main(String[] args) throws Exception{
        // initializing useful variables
        boolean quit = false;
        int choice = -1, fileIndex, heuristicIndex;
        double[] result = null;

        Map<String, String> parameters = new HashMap<>();
        String option = null;

        // Parse args
        String argKey, argValue;
        for (int i = 0; i < args.length; i++){
            argKey = args[i];
            if (argKey.charAt(0) == '-' && argKey.charAt(1) != '-'){
                // -a
                // check the key
                if (argKey.length() != 2){
                    System.err.println("Invalid argument: " + argKey);
                    return;
                }

                // get the key and value
                argValue = args[++i].substring(1);
                if (argValue.charAt(0) != '-')
                    parameters.put(argKey.substring(1), argValue);

            }else if (argKey.charAt(0) == '-' && argKey.charAt(1) == '-'){
                // --
                // get the key and value
                argValue = args[++i];
                if (argValue.charAt(0) != '-')
                    parameters.put(argKey.substring(2), argValue);

            }else{
                // option
                option = argKey;
            }
        }

        //check args
        if (Objects.equals(option, "gui")){
            TestGUIMain.main(args);
        }
        else if (parameters.size() > 0){
            Integer h1 = null, h2 = null, compare = null;

            // run the application depending on the arguments

            //check the scene given otherwise ask the user for a scene
            if (parameters.containsKey("s")){
                File file = new File(parameters.get("s"));
                if (file.exists()) {
                    fileList = new ArrayList<>();
                    fileList.add(file.getAbsolutePath());
                }else {
                    System.out.println("Invalid File");
                    System.exit(0);
                }
                fileIndex = fileList.size() - 1;
            }else{
                System.out.println("Choose the first scene file:");
                fileIndex = chooseFile();
            }

            //check the 1st heuristic otherwise ask the user for a heuristic.
            if (parameters.containsKey("h1")){
                h1 = Integer.parseInt(parameters.get("h1"));
                if (h1 < 0 || h1 > 3){
                    System.out.println("Invalid id for the first heuristic");
                    System.exit(0);
                }

            }else{
                System.out.println("Choose the heuristic for the first scene:");
                h1 = chooseUniqueHeuristic();
            }

            //check the 2nd heuristic otherwise ask the user for a heuristic.
            if (parameters.containsKey("h2")){
                h2 = Integer.parseInt(parameters.get("h2"));
                if (h1 < 0 || h1 > 3){
                    System.out.println("Invalid id for the second heuristic");
                    System.exit(0);
                }
            }else{
                System.out.println("Choose the heuristic for the second scene:");
                h2 = chooseUniqueHeuristic();
            }

            //check the comparison method id otherwise ask the user for the method to use.
            if (parameters.containsKey("compare")){
                compare = Integer.parseInt(parameters.get("compare"));
                if (compare < 0 || compare > 4){
                    System.out.println("Invalid id for the comparison");
                    System.exit(0);
                }
            }else{
                do{
                    System.out.println("Choose the operation to compare the 2 scenes partitions:");
                    showMenu();
                    compare = getChoice();
                }while(compare < 1 || compare > 6);
            }

            heuristicIndex = Integer.parseInt(h1.toString()+h2.toString());

            //compute the solution
            switch(compare){
                case 1:
                    TestGUIMain.main(args);
                    break;
                case 2:
                    result = compareHeight(heuristicIndex, fileIndex);
                    break;
                case 3:
                    result = compareLength(heuristicIndex, fileIndex);
                    break;
                case 4:
                    result = compareCPUTimeAtBuild(heuristicIndex, fileIndex);
                    break;
                case 5:
                    result = compareCPUTimeForPainting(heuristicIndex, fileIndex);
                    break;
            }
            quit = true;

            if (result != null)
                System.out.println("Results:\n\tFirst: " + result[0] + "\n\tSecond: " + result[1] +
                        ".\nAll the results given for a timing comparison are given in nanoseconds.\n\n");
        }

        //if no option or arguments
        while (!quit) {
            //show the menu and wait for the user to make a choice
            System.out.println("Menu:");
            showMenu();
            do {
                System.out.print("Enter the index corresponding to your choice: ");
                choice = getChoice();
                if (choice > 6)
                    System.out.println("Please use a valid entry.");
            } while (choice < 1 || choice > 6);

            //process the result
            switch (choice) {
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

            //show the result
            if (result != null)
                System.out.println("Results:\n\tFirst: " + result[0] + "\n\tSecond: " + result[1] +
                        ".\nAll the results given for a timing comparison are given in nanoseconds.");
        }

        System.exit(0);
    }

    /**
     * Method used to get an integer user input.
     * @return
     *      Integer input.
     */
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

    /**
     * Show the menu.
     */
    private static void showMenu(){
        System.out.println("1. Open GUI application.");
        System.out.println("2. Compare height between trees.");
        System.out.println("3. Compare length between trees.");
        System.out.println("4. Compare CPU time used to build trees.");
        System.out.println("5. compare CPU time used by the painter's algorithm.");
        System.out.println("6. Quit.");
    }

    /**
     * Get all the files recursively from a given directory.
     * @param f
     *      Directory represented by a File object.
     * @return
     *      List of all files contained in the given directory.
     */
    private static ArrayList<File> getFileRecursive(File f){
        //System.out.println("\nhere: "+f.getPath());
        ArrayList<File> returnList = new ArrayList<>();
        if (f.isFile()) {
            //System.out.println("file!\n");
            returnList.add(f);
        }
        else if (f.isDirectory()) {
            //System.out.println("directory!\n");
            for (File file : f.listFiles())
                returnList.addAll(getFileRecursive(file));
        }

        return returnList;
    }

    /**
     * Show all the files contained in the scene resources directory.
     * @throws Exception
     *      If the directory has not been found.
     */
    private static void showSceneFiles() throws Exception{
        //get resources dir
        File resourcesSceneFiles = new File(TestMain.class.getResource("/scenes/").getFile());

        int id = 1;

        //solution found here: https://stackoverflow.com/questions/11012819/how-can-i-access-a-folder-inside-of-a-resource-folder-from-inside-my-jar-file/20073154#20073154

        if (jarFile.isFile()){
            //run from jar path
            fileList = new ArrayList<>();
            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> entries = jar.entries();
            while(entries.hasMoreElements()){
                String name = entries.nextElement().getName();
                if (name.startsWith("scenes/") && name.endsWith(".txt")){
                    //filters scene files
                    fileList.add("./" + name);
                    String[] parts = name.split("/");
                    System.out.println(id + ". " + parts[parts.length-1]);
                    id++;
                }
            }
        }else {
            //run from gradle root path
            //list all files
            ArrayList<File> listing = getFileRecursive(resourcesSceneFiles);
            fileList = new ArrayList<>();

            for (File f : listing) {
                System.out.println(id + ". " + f.getName());
                fileList.add(f.getPath());
                id++;
            }
        }
        System.out.println(id + ". Choose the path to your scene file.");
    }

    /**
     * Allow the user to choose the 2 heuristics.
     * @return
     *      Id of heuristic.
     */
    private static int chooseHeuristic(){
        int choice;

        System.out.println("1. Random.");
        System.out.println("2. First segment in the file.");
        System.out.println("3. Free split.");

        do {
            System.out.print("Concatenate your choices for the two heuristics." +
                    "\nExample: 12 means that the first tree will be built by the random heuristic and the second by taking the first segment in the set." +
                    "\nEnter the indexes corresponding to your choice: ");
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

    /**
     * Allow the user to choose a heuristic.
     * Used when the user uses arguments.
     * @return
     *      Id of a heuristic.
     */
    private static int chooseUniqueHeuristic(){
        int choice;

        System.out.println("1. Random.");
        System.out.println("2. First segment in the file.");
        System.out.println("3. Free split.");

        do {
            System.out.print("Enter the index corresponding to your choice.");
            choice = getChoice();
            switch(choice){
                case 1:
                case 2:
                case 3:
                    return choice;
                default:
                    System.out.println("Please use a valid entry as explained above.");
                    break;
            }
        }while (true);
    }

    /**
     * Allow the user to choose the id of the scene file.
     * @return
     *      Index of the file in the global list of files.
     */
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
            /*for (int i = 0; i < fileList.size();i++)
                System.out.println(fileList.get(i));*/
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
                        fileList.add(filePath);
                        return fileList.size();
                    }
                } catch (Exception e) {
                    System.out.println("Please use correct values.");
                }
            }while(true);
        }

        return choice;
    }

    /**
     * Parse a file to get all the segments.
     * @param fileIndex
     *      Index of the file to parse.
     * @return
     *      Set of segments constituting the file.
     * @throws Exception
     *      If the file has not been found.
     */
    private static Segment[] parseFile(int fileIndex) throws Exception{
        Segment[] scene;

        String filePath = fileList.get(fileIndex);

        FileParser parser = new FileParser(filePath);

        return parser.getData().toArray(new Segment[0]);
    }

    /**
     * Build the 2 trees with different heuristics and compare their height.
     * @param heuristicIndex
     *      The indexes of the heuristics.
     * @param fileIndex
     *      The index of the file stored in the global list of files.
     * @return
     *      An Array with the results.
     * @throws Exception
     *      If any exception is raised opening files.
     */
    private static double[] compareHeight(int heuristicIndex, int fileIndex) throws Exception{
        Segment[] S = parseFile(fileIndex), randS;
        randS = S.clone();
        Collections.shuffle(Arrays.asList(randS));

        switch (heuristicIndex){
            case 12:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
            case 13:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 21:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 23:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 31:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 32:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
        }

        double[] results = {trees[0].height(), trees[1].height()};
        return results;
    }

    /**
     * Build the 2 trees with different heuristics and compare their length.
     * @param heuristicIndex
     *      The indexes of the heuristics.
     * @param fileIndex
     *      The index of the file stored in the global list of files.
     * @return
     *      An Array with the results.
     * @throws Exception
     *      If any exception is raised opening files.
     */
    private static double[] compareLength(int heuristicIndex, int fileIndex) throws Exception{
        Segment[] S = parseFile(fileIndex), randS;
        randS = S.clone();
        Collections.shuffle(Arrays.asList(randS));

        switch (heuristicIndex){
            case 12:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
            case 13:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 21:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 23:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 31:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 32:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
        }

        double[] results = {trees[0].length(), trees[1].length()};
        return results;
    }

    /**
     * Build the 2 trees with different heuristics and compare the CPU time used to create both.
     * @param heuristicIndex
     *      The indexes of the heuristics.
     * @param fileIndex
     *      The index of the file stored in the global list of files.
     * @return
     *      An Array with the results.
     * @throws Exception
     *      If any exception is raised opening files.
     */
    private static double [] compareCPUTimeAtBuild(int heuristicIndex, int fileIndex) throws Exception{
        Segment[] S = parseFile(fileIndex), randS;
        randS = S.clone();
        Collections.shuffle(Arrays.asList(randS));

        switch (heuristicIndex){
            case 12:
                t1.start();
                trees[0] = BSPTree.makeBasicTree(randS, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeBasicTree(S, null);
                t2.stop();
                break;
            case 13:
                t1.start();
                trees[0] = BSPTree.makeBasicTree(randS, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                t2.stop();
                break;
            case 21:
                t1.start();
                trees[0] = BSPTree.makeBasicTree(S, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeBasicTree(randS, null);
                t2.stop();
                break;
            case 23:
                t1.start();
                trees[0] = BSPTree.makeBasicTree(S, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                t2.stop();
                break;
            case 31:
                t1.start();
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeBasicTree(randS, null);
                t2.stop();
                break;
            case 32:
                t1.start();
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                t1.stop();
                t2.start();
                trees[1] = BSPTree.makeBasicTree(S, null);
                t2.stop();
                break;
        }

        double[] results = {t1.getDelta(), t2.getDelta()};
        return results;
    }

    /**
     * Build the 2 trees with different heuristics and compare the CPU time used by the painter's algorithm.
     * @param heuristicIndex
     *      The indexes of the heuristics.
     * @param fileIndex
     *      The index of the file stored in the global list of files.
     * @return
     *      An Array with the results.
     * @throws Exception
     *      If any exception is raised opening files.
     */
    private static double [] compareCPUTimeForPainting(int heuristicIndex, int fileIndex) throws Exception{
        Segment[] S = parseFile(fileIndex), randS;
        randS = S.clone();
        Collections.shuffle(Arrays.asList(randS));
        Scanner input = new Scanner(System.in);
        double x, y;

        ArrayList<Segment>
                p1 = new ArrayList<>(),
                p2 = new ArrayList<>();

        System.out.print("building trees...");

        switch (heuristicIndex){
            case 12:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
            case 13:
                trees[0] = BSPTree.makeBasicTree(randS, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 21:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 23:
                trees[0] = BSPTree.makeBasicTree(S, null);
                trees[1] = BSPTree.makeFreeSplitTree(S, null);
                break;
            case 31:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(randS, null);
                break;
            case 32:
                trees[0] = BSPTree.makeFreeSplitTree(S, null);
                trees[1] = BSPTree.makeBasicTree(S, null);
                break;
        }

        do {
            System.out.println("\nChoose the x and y positions of the eye in the scene.");
            try {
                System.out.print("x: ");
                x = input.nextDouble();
                System.out.print("y: ");
                y = input.nextDouble();

                t1.start();
                p1 = BSPTree.paintersAlgorithm(trees[0], new Point2D(x, y));
                t1.stop();

                t2.start();
                p2 = BSPTree.paintersAlgorithm(trees[1], new Point2D(x, y));
                t2.stop();

                break;
            } catch (Exception e) {
                System.out.println("Please enter only double values.");
            }
        }while(true);

        double[] results = {t1.getDelta(), t2.getDelta()};
        return results;
    }
}
