package DataStructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser {
    private int a, b, n;
    private ArrayList<Segment> data;
    private File file;

    private Scanner scanner;

    public FileParser(File f) throws IOException{
        file = f;
        if (file == null)
            throw new IOException("Impossible to open this file.");
        scanner = new Scanner(file);
        if (!checkScene())
            throw new IOException("The given file is not a correct Scene file.");
    }

    private boolean checkScene(){
        String fl [] = scanner.nextLine().split(" ");
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
        while (scanner.hasNextLine()){
            fl = scanner.nextLine().split(" ");
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

    public int[] getParameters(){
        return new int[] {a, b, n};
    }

    public ArrayList<Segment> getData(){
        return data;
    }
}
