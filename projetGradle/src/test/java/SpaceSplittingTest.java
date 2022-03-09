import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.BSPTree;
import DataStructure.Segment;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

    /*@Test
    void testRandom(){
        System.out.println("Random Segment in set:");
        Segment[] S = {new Segment(1,1,3,4),
                        new Segment(4,2,4,6),
                        new Segment(2,7,8,7)};

        List<Segment> newS = Arrays.asList(S);
        Collections.shuffle(newS);
        BSPTree<Segment> tree = Segment.makeBasicTree(newS.toArray(new Segment[0]));

        BSPTree<Segment> expectedTree = new BSPTree<>();

        assertEquals(1, 1);
    }*/

    @Test
    void testFirst(){
        System.out.println("First Segment in set:");

        Segment[] S = {new Segment(1,1,3,4),    //AB
                new Segment(4,2,4,6),           //CD
                new Segment(2,7,8,7)};          //EF

        BSPTree<Segment> tree = Segment.makeBasicTree(S);

        Segment[] expectedS = {new Segment(1,1,3,4),    //AB 0
                new Segment(4,2,4,11.0/2.0),            //CG 1
                new Segment(5,7,8,7),                   //HF 2
                new Segment(4, 11.0/2.0,4,6),           //GD 3
                new Segment(2,7,4,7),                   //EI 4
                new Segment(4,7,5,7)};                  //IH 5

        BSPTree<Segment> leafHF = new BSPTree<>(expectedS[2], null, null);
        BSPTree<Segment> leafEI = new BSPTree<>(expectedS[4], null, null);
        BSPTree<Segment> leafIH = new BSPTree<>(expectedS[5], null, null);
        BSPTree<Segment> leftTree = new BSPTree<>(expectedS[1], null, leafHF);
        BSPTree<Segment> rightTree = new BSPTree<>(expectedS[3], leafEI, leafIH);
        BSPTree<Segment> expectedTree = new BSPTree<>(expectedS[0], leftTree, rightTree);

        /*System.out.println("tree:");
        tree.print();

        System.out.println("\nexpectedTree:");
        expectedTree.print();*/

        assertEquals(tree, expectedTree);
    }

    /*@Test
    void testFreeSplit(){

    }*/

    @AfterEach
    void testAfterEach(){
        System.out.println("Test ended up Successfully.");
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("Unit Tests for heuristic ended up.");
    }
}
