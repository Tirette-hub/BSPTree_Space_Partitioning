import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import DataStructure.BSPTree;
import DataStructure.Segment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @AfterAll
    static void testAfterAll(){
        System.out.println("Unit Tests for heuristic ended up.");
    }
}
