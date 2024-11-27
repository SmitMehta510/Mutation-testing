package com.TranquilMind.Algorithms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BFSTest{

    BFS bfs = new BFS();

    @Test
    public void testBfsOfGraph() {

        // Test case 1
        int V1 = 5;
        ArrayList<ArrayList<Integer>> adj1 = new ArrayList<>();
        for (int i = 0; i < V1; i++) {
            adj1.add(new ArrayList<>());
        }
        adj1.get(0).add(1);
        adj1.get(1).add(0);
        adj1.get(0).add(2);
        adj1.get(2).add(0);
        adj1.get(1).add(3);
        adj1.get(3).add(1);
        adj1.get(1).add(4);
        adj1.get(4).add(1);
        adj1.get(3).add(4);
        adj1.get(4).add(3);
        ArrayList<Integer> result1 = bfs.bfsOfGraph(V1, adj1);
        ArrayList<Integer> expected1 = new ArrayList<>(List.of(0, 1, 2, 3, 4));
        assertEquals(expected1, result1);

        // Test case 2
        int V2 = 6;
        ArrayList<ArrayList<Integer>> adj2 = new ArrayList<>();
        for (int i = 0; i < V2; i++) {
            adj2.add(new ArrayList<>());
        }
        adj2.get(0).add(3);
        adj2.get(0).add(4);
        adj2.get(0).add(5);
        adj2.get(1).add(2);
        adj2.get(1).add(5);
        adj2.get(2).add(1);
        adj2.get(2).add(3);
        adj2.get(3).add(0);
        adj2.get(3).add(2);
        adj2.get(4).add(0);
        adj2.get(5).add(0);
        adj2.get(5).add(1);
        ArrayList<Integer> result2 = bfs.bfsOfGraph(V2, adj2);
        ArrayList<Integer> expected2 = new ArrayList<>(List.of(0, 3, 4, 5, 2, 1));
        assertEquals(expected2, result2);

        // Test case for assertNotEquals
        int V3 = 3;
        ArrayList<ArrayList<Integer>> adj3 = new ArrayList<>();
        adj3.add(new ArrayList<>(List.of(1, 2)));
        adj3.add(new ArrayList<>(List.of(0)));
        adj3.add(new ArrayList<>(List.of(0)));
        ArrayList<Integer> result3 = bfs.bfsOfGraph(V3, adj3);
        ArrayList<Integer> invalidExpected = new ArrayList<>(List.of(0, 2, 1));     // Incorrect expected result
        assertNotEquals(invalidExpected, result3);




    }


}