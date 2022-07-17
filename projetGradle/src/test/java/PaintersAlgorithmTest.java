import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Console.TestMain;
import DataStructure.Eye;
import DataStructure.Point2D;
import DataStructure.Segment;
import DataStructure.SegmentBSPTree;
import org.junit.jupiter.api.*;

import javax.management.InvalidAttributeValueException;
import java.util.LinkedList;

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
        Point2D POVposition = new Point2D(0, 3); //AB+, GD-, EI- (in the bsp tree)
        Eye eye = new Eye(POVposition, 30, 60);

        Segment[] segs = {new Segment(1,1,3,4),     //AB 0
                new Segment(4,2,4,11.0/2.0),        //CG 1
                new Segment(5,7,8,7),               //HF 2
                new Segment(4, 11.0/2.0,4,6),       //GD 3
                new Segment(2,7,4,7),               //EI 4
                new Segment(4,7,5,7)                //IH 5
        };

        SegmentBSPTree leafHF = new SegmentBSPTree(segs[2], null, null);
        SegmentBSPTree leafEI = new SegmentBSPTree(segs[4], null, null);
        SegmentBSPTree leafIH = new SegmentBSPTree(segs[5], null, null);
        SegmentBSPTree leftTree = new SegmentBSPTree(segs[1], null, leafHF);
        SegmentBSPTree rightTree = new SegmentBSPTree(segs[3], leafEI, leafIH);
        SegmentBSPTree tree = new SegmentBSPTree(segs[0], leftTree, rightTree);

        LinkedList<Segment> expectedS = new LinkedList<Segment>();
        expectedS.add(new Segment(5,7,8,7));       //HF
        expectedS.add(new Segment(4,2,4,5.5));     //CG
        //expectedS.add(new Segment(1,1,3,4));       //AB
        expectedS.add(new Segment(4,7,5,7));       //IH
        expectedS.add(new Segment(4, 5.5,4,6));    //GD
        expectedS.add(new Segment(2,7,4,7));       //EI

        LinkedList<Segment> s = SegmentBSPTree.paintersAlgorithm(tree, eye);

        assertEquals(expectedS, s);
    }

    @Test
    void testGetIntersection(){
        System.out.println("intersection point between two lines defined by segments:");
        Point2D expectedIntersectionPoint = new Point2D(2,3);

        Segment line1 = new Segment(0,0,2,3),
                line2 = new Segment(1,11.0/3,2, 3);

        Point2D intersectionPoint = Segment.getIntersection(Segment.getCutlineParameters(line1.getFrom(), line1.getTo()), Segment.getCutlineParameters(line2.getFrom(), line2.getTo()));

        assertEquals(expectedIntersectionPoint, intersectionPoint);
    }

    @Test
    void testAngle() throws InvalidAttributeValueException {
        System.out.println("angle between a direction line and a point:");
        Segment direction = new Segment(0,0,Math.sqrt(3),1);
        Point2D pt = new Point2D(1, Math.sqrt(3));

        double expectedAngle = 30;

        double alpha = Segment.getAngle(direction, pt);

        assertEquals(expectedAngle, alpha, TestMain.epsilon);
    }

    @Test
    void testProjection(){
        System.out.println("Projection of a point on the solution canvas (get the correct coordinates):");
        Point2D pt = new Point2D(0, 1);
        Segment direction = new Segment(0, 0, 2, 3.464);
        double[] abc = Segment.getCutlineParameters(direction.getFrom(), direction.getTo());
        double angle = 30, FOV = 90, screenWidth = 600;

        double expectedX = 100;

        double x = Segment.getProjection(pt, abc[0], abc[1], abc[2], angle, FOV, screenWidth);

        assertEquals(x, expectedX, TestMain.epsilon);
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
