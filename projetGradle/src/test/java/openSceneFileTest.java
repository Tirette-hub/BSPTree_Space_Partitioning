import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.EColor;
import DataStructure.Segment;
import DataStructure.FileParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class openSceneFileTest {
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("Running Unit Tests for scene file opening and parsing and gathering data.");
    }

    @Test
    void testFile() throws Exception{
        ArrayList<Segment> supposedData = new ArrayList<Segment>();
        int supposedA = 300, supposedB = 550;
        supposedData.add(new Segment(0.0, 325.0, 150.0, 325.0, EColor.getEColorByName("Rouge")));
        supposedData.add(new Segment(150.0, 325.0, 300.0, 325.0, EColor.getEColorByName("Bleu")));
        supposedData.add(new Segment(300.0, 225.0, 300.0, 325.0, EColor.getEColorByName("Rose")));
        supposedData.add(new Segment(0.0, 225.0, 150.0, 225.0, EColor.getEColorByName("Noir")));
        supposedData.add(new Segment(150.0, 225.0, 300.0, 225.0, EColor.getEColorByName("Vert")));
        supposedData.add(new Segment(0.0, 225.0, 0.0, 325.0, EColor.getEColorByName("Jaune")));
        supposedData.add(new Segment(250.0, 100.0, (141.421356+300)/2, (-208.578644+550)/2, EColor.getEColorByName("Vert")));
        supposedData.add(new Segment((141.421356+300)/2, (-208.578644+550)/2, 150.0, 200.0, EColor.getEColorByName("Jaune")));
        supposedData.add(new Segment(150.0, 200.0, (-141.421356+300)/2, (-208.578644+550)/2, EColor.getEColorByName("Gris")));
        supposedData.add(new Segment((-141.421356+300)/2, (-208.578644+550)/2, 50.0, 100.0, EColor.getEColorByName("Noir")));
        supposedData.add(new Segment(50.0, 100.0, (-141.421356+300)/2, (-491.421356+550)/2, EColor.getEColorByName("Orange")));
        supposedData.add(new Segment((-141.421356+300)/2, (-491.421356+550)/2, 150.0, 0.0, EColor.getEColorByName("Violet")));
        supposedData.add(new Segment(150.0, 0.0, (141.421356+300)/2, (-491.421356+550)/2, EColor.getEColorByName("Rouge")));
        supposedData.add(new Segment((141.421356+300)/2, (-491.421356+550)/2, 250.0, 100.0, EColor.getEColorByName("Bleu")));

        //open test file
        File file = new File(getClass().getResource("/octangle.txt").toURI());

        FileParser parser = new FileParser(file);

        int abn[] = parser.getParameters();
        int a = abn[0], b = abn[1], n = abn[2];

        ArrayList<Segment> data = parser.getData();

        assertEquals(supposedA, a);
        assertEquals(supposedB, b);
        assertEquals(n, 14);


        assertEquals(supposedData, data);
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("Unit Tests for scene file ended up.");
    }
}
