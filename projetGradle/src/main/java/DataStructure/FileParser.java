package DataStructure;

import java.io.*;
import java.util.ArrayList;
import java.util.jar.JarFile;

/**
 * File Parser for scene files.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class FileParser {
    private int a, b, n;
    private ArrayList<Segment> data;
    private File file;

    private BufferedReader reader;

    /**
     * Constructor.
     * @param filePath
     *      Path to the file to open.
     * @throws IOException
     *      If the file cannot be opened or if the file is not a scene file.
     */
    public FileParser(String filePath) throws IOException {
        if (filePath.startsWith("./")){
            JarFile jar = new JarFile(FileParser.class.getProtectionDomain().getCodeSource().getLocation().getFile());
            if (jar == null)
                throw new IOException("Impossible to open this file.");
            InputStream in = jar.getInputStream(jar.getJarEntry(filePath.substring(2)));//TestMain.class.getResourceAsStream(filePath.substring(1));
            reader = new BufferedReader(new InputStreamReader(in));
        }else{
            file = new File(filePath);
            if (file == null)
                throw new IOException("Impossible to open this file.");
            InputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
        }

        if (!checkScene())
            throw new IOException("The given file is not a correct Scene file.");
    }

    /**
     * Check if the file is a scene file and get all its data.
     * @return
     *      True if the file is a correct scene file.
     * @throws IOException
     *      If any error happens while reading the file.
     */
    private boolean checkScene() throws IOException{
        String fl [] = reader.readLine().split(" ");
        if (fl.length != 4) //4elements: >, a, b, n
            return false;

        a = Integer.parseInt(fl[1]);
        b = Integer.parseInt(fl[2]);
        n = Integer.parseInt(fl[3]);

        if (a < 0 && b < 0 && n < 1)
            return false;

        int count = 0;
        double x1, y1, x2, y2;
        EColor color;
        data = new ArrayList<>();
        Segment seg;

        String line;
        while ((line = reader.readLine()) != null){
            fl = line.split(" ");
            if (fl.length != 5)
                return false;
            x1 = Double.parseDouble(fl[0]);
            y1 = Double.parseDouble(fl[1]);
            x2 = Double.parseDouble(fl[2]);
            y2 = Double.parseDouble(fl[3]);
            color = EColor.getEColorByName(fl[4]);

            //
            data.add(new Segment((x1 + a) / 2, (y1 + b) / 2, (x2 + a) / 2, (y2 + b) / 2, color));
            count++;
        }

        if (count != n)
            return false;

        return true;
    }

    /**
     * Get the scene parameters (width, height, number of segments)
     * @return
     *      Array with the parameters.
     */
    public int[] getParameters(){
        return new int[] {a, b, n};
    }

    /**
     * Get the list of segments in the scene.
     * @return
     *      List of segments in the scene.
     */
    public ArrayList<Segment> getData(){
        return data;
    }
}
