import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.BSPTree;
import DataStructure.Segment;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class PaintersAlgorithmTest {
    @BeforeAll
    static void BeforeAll(){
        System.out.println("Running Tests for Painter's Algorithm...");
    }

    @BeforeEach
    void BeforeEach(){
        System.out.println("Running new Test...");
    }

    @Test
    void testPaintersAlgorithm(){
        System.out.println("painter's algorithm:");
        double[] POVposition = {0, 3}; //AB+, GD-, EI- (in the bsp tree)

        Segment[] segs = {new Segment(1,1,3,4),     //AB 0
                new Segment(4,2,4,11.0/2.0),        //CG 1
                new Segment(5,7,8,7),               //HF 2
                new Segment(4, 11.0/2.0,4,6),       //GD 3
                new Segment(2,7,4,7),               //EI 4
                new Segment(4,7,5,7)                //IH 5
        };

        BSPTree<Segment> leafHF = new BSPTree<>(segs[2], null, null);
        BSPTree<Segment> leafEI = new BSPTree<>(segs[4], null, null);
        BSPTree<Segment> leafIH = new BSPTree<>(segs[5], null, null);
        BSPTree<Segment> leftTree = new BSPTree<>(segs[1], null, leafHF);
        BSPTree<Segment> rightTree = new BSPTree<>(segs[3], leafEI, leafIH);
        BSPTree<Segment> tree = new BSPTree<>(segs[0], leftTree, rightTree);

        ArrayList<Segment> expectedS = new ArrayList<Segment>();
        expectedS.add(new Segment(5,7,8,7));       //HF
        expectedS.add(new Segment(4,2,4,5.5));     //CG
        expectedS.add(new Segment(1,1,3,4));       //AB
        expectedS.add(new Segment(4,7,5,7));       //IH
        expectedS.add(new Segment(4, 5.5,4,6));    //GD
        expectedS.add(new Segment(2,7,4,7));       //EI

        ArrayList<Segment> s = Segment.paintersAlgorithm(tree, POVposition);

        assertEquals(s, expectedS);
    }

    @Test
    void testGetIntersection(){
        System.out.println("intersection point between two lines defined by segments:");
        double[] expectedIntersectionPoint = {2,3};

        Segment line1 = new Segment(0,0,2,3),
                line2 = new Segment(1,11.0/3,2, 3);

        double[] intersectionPoint = Segment.getIntersection(Segment.getCutlineParameters(line1.get()), Segment.getCutlineParameters(line2.get()));

        assertArrayEquals(expectedIntersectionPoint, intersectionPoint);
    }

    @Test
    void testAngle(){
        System.out.println("angle between a direction line and a point:");
        Segment direction = new Segment(0,0,2,3);
        double[] pt = {1, 11.0/3};

        double expectedAngle = Math.toDegrees(Math.atan(1.0/3));

        double alpha = Segment.getAngle(direction, pt);

        assertEquals(expectedAngle, alpha);
    }

    @AfterEach
    void AfterEach(){
        System.out.println("Test ended up successfully.");
    }

    @AfterAll
    static void AfterAll(){
        System.out.println("Tests performed successfully.");
    }
}
