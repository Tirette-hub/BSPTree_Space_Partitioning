import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.EColor;
import DataStructure.Segment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class openSceneFileTest {
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("Running Unit Tests for scene file opening and parsing and gathering data.");
    }

    @Test
    void testGetData() throws Exception{
        Segment[] supposedData = new Segment[14];
        int supposedA = 300, supposedB = 550;
        supposedData[0] = new Segment(-300.000000, 100.000000, 0.000000, 100.000000, EColor.getEColorByName("Rouge"));
        supposedData[1] = new Segment(0.000000, 100.000000, 300.000000, 100.000000, EColor.getEColorByName("Bleu"));
        supposedData[2] = new Segment(300.000000, -100.000000, 300.000000, 100.000000, EColor.getEColorByName("Rose"));
        supposedData[3] = new Segment(-300.000000, 100.000000, 0.000000, -100.000000, EColor.getEColorByName("Noir"));
        supposedData[4] = new Segment(0.000000, -100.000000, 300.000000, -100.000000, EColor.getEColorByName("Vert"));
        supposedData[5] = new Segment(-300.000000, 100.000000, -300.000000, 100.000000, EColor.getEColorByName("Jaune"));
        supposedData[6] = new Segment(200.000000, -350.000000, 141.421356, -208.578644, EColor.getEColorByName("Vert"));
        supposedData[7] = new Segment(141.421356, -208.578644, 0.000000, -150.000000, EColor.getEColorByName("Jaune"));
        supposedData[8] = new Segment(0.000000, -150.000000, -141.421356, -208.578644, EColor.getEColorByName("Gris"));
        supposedData[9] = new Segment(-141.421356, -208.578644, -200.000000, -350.000000, EColor.getEColorByName("Noir"));
        supposedData[10] = new Segment(-200.000000, -350.000000, -141.421356, -491.421356, EColor.getEColorByName("Orange"));
        supposedData[11] = new Segment(-141.421356, -491.421356, -0.000000, -550.000000, EColor.getEColorByName("Violet"));
        supposedData[12] = new Segment(-0.000000, -550.000000, 141.421356, -491.421356, EColor.getEColorByName("Rouge"));
        supposedData[13] = new Segment(141.421356, -491.421356, 200.000000, -350.000000, EColor.getEColorByName("Bleu"));

        //open test file
        System.out.println(getClass().getResource("/octangle.txt").toURI());
        File file = new File(getClass().getResource("/octangle.txt").toURI());

        //init the scanner to parse the file
        Scanner scanner = new Scanner(file);
        //read first line
        String[] fl = scanner.nextLine().split(" ");
        //get principal data from the file
        int a = Integer.parseInt(fl[1]); //max abs
        int b = Integer.parseInt(fl[2]); //max ord
        int n = Integer.parseInt(fl[3]); //#segment in the scene

        Segment[] data = new Segment[n];
        int i = 0;

        while(scanner.hasNextLine()){
            //get data from the file
            fl = scanner.nextLine().split(" ");
            double x1, x2, y1, y2;
            EColor color;
            //convert data
            x1 = Double.parseDouble(fl[0]); y1 = Double.parseDouble(fl[1]);
            x2 = Double.parseDouble(fl[2]); y2 = Double.parseDouble(fl[3]);
            color = EColor.getEColorByName(fl[4]);

            //create the segment from a line of data and add it to the scene
            Segment segment = new Segment(x1,y1, x2, y2, color);
            data[i] = segment;
            i++;
        }

        assertEquals(supposedA, a);
        assertEquals(supposedB, b);
        assertEquals(supposedData, supposedData);
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("Unit Tests for scene file ended up.");
    }
}
