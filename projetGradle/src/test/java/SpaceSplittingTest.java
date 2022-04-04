import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.BSPTree;
import DataStructure.Segment;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class SpaceSplittingTest {
    @BeforeAll
    static void testBeforeAll(){
        System.out.println("Running tests for space partitioning heuristics...");
    }

    @BeforeEach
    void testBeforeEach() {
        System.out.println("Running new heuristic Test...");
    }

    @Test
    void testCut(){
        System.out.println("Cut:");

        Segment s1 = new Segment(2,7,8,7); //horizontal
        Segment s2 = new Segment(4,2,4,6); //vertical
        Segment s3 = new Segment(1, 1, 3, 4); // slope = 3/2

        //s3 cut s2 expected result
        Segment[] r1 = {new Segment(4, 2, 4, 11.0/2.0), new Segment(4, 11.0/2.0, 4, 6)};
        //s3 cut s1 expected result
        Segment[] r2 = {new Segment(2, 7, 5, 7), new Segment(5, 7, 8, 7)};
        //s2 cut s1 expected result
        Segment[] r3 = {new Segment(2, 7, 4, 7), new Segment(4, 7, 8, 7)};

        assertArrayEquals(Segment.cut(s2, s3), r1);
        assertArrayEquals(Segment.cut(s1, s3), r2);
        assertArrayEquals(Segment.cut(s1, s2), r3);
    }

    @Test
    void testFirst(){
        //No test for random set because it uses the same method as the following but with a shuffled data set S.
        System.out.println("First Segment in set:");

        Segment[] S = {new Segment(1,1,3,4),    //AB
                new Segment(4,2,4,6),           //CD
                new Segment(2,7,8,7)};          //EF

        BSPTree<Segment> tree = Segment.makeBasicTree(S,null, false);

        Segment[] segs = {new Segment(1,1,3,4),     //AB 0
                new Segment(4,2,4,11.0/2.0),        //CG 1
                new Segment(5,7,8,7),               //HF 2
                new Segment(4, 11.0/2.0,4,6),       //GD 3
                new Segment(2,7,4,7),               //EI 4
                new Segment(4,7,5,7)};              //IH 5

        BSPTree<Segment> leafHF = new BSPTree<>(segs[2], null, null);
        BSPTree<Segment> leafEI = new BSPTree<>(segs[4], null, null);
        BSPTree<Segment> leafIH = new BSPTree<>(segs[5], null, null);
        BSPTree<Segment> leftTree = new BSPTree<>(segs[1], null, leafHF);
        BSPTree<Segment> rightTree = new BSPTree<>(segs[3], leafEI, leafIH);
        BSPTree<Segment> expectedTree = new BSPTree<>(segs[0], leftTree, rightTree);

        /*System.out.println("tree:");
        tree.print();

        System.out.println("\nexpectedTree:");
        expectedTree.print();*/

        assertEquals(tree, expectedTree);
    }

    @Test
    void testFreeSplit(){
        System.out.println("free split:");
        Segment[] S = {
                new Segment(3.5,0.5,2.5,2.5),   //AB
                new Segment(-0.5,5,2.5,6),      //CD
                new Segment(5.5,6.5,5,4.5),     //EF
                new Segment(2.5,4,3.5,4.5),     //GH
                new Segment(-0.5,4.5,7,1.5)     //IJ
        };

        BSPTree<Segment> tree = Segment.makeFreeSplitTree(S, null);

        Segment[] segs = {
                new Segment(3.5,0.5,2.5,2.5),   //AB 0
                new Segment(-0.5,5,1,5.5),      //CL 1
                new Segment(1,5.5,2.5,6),       //LD 2
                new Segment(5.5,6.5,5,4.5),     //EF 3
                new Segment(2.5,4,3.5,4.5),     //GH 4
                new Segment(-0.5,4.5,2,3.5),    //IK 5
                new Segment(2,3.5,4.5,2.5),     //KM 6
                new Segment(4.5,2.5,7,1.5)      //MJ 7
        };

        BSPTree<Segment> leafGH = new BSPTree<>(segs[4], null, null);
        BSPTree<Segment> leafIK = new BSPTree<>(segs[5], null, null);
        BSPTree<Segment> leafMJ = new BSPTree<>(segs[7], null, null);
        BSPTree<Segment> leftTree = new BSPTree<>(segs[1], leafIK, null);
        BSPTree<Segment> nodeKM = new BSPTree<>(segs[6], null, leafGH);
        BSPTree<Segment> nodeEF = new BSPTree<>(segs[3], leafMJ, nodeKM);
        BSPTree<Segment> rightTree = new BSPTree<>(segs[2], nodeEF, null);
        BSPTree<Segment> expectedTree = new BSPTree<>(segs[0], leftTree, rightTree);

        /*System.out.println("tree:");
        tree.print();

        System.out.println("\nexpectedTree:");
        expectedTree.print();*/

        assertEquals(tree, expectedTree);
    }

    @AfterEach
    void testAfterEach(){
        System.out.println("Test ended up Successfully.");
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("Unit Tests for heuristic ended up.");
    }
}
