package model.graph;

import model.LinkedList.ListException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void testAdjacencyListGraph() {
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>(10, false);
        try {
            // agregamos vertices
            for (int i = 0; i < 10; i++)
                graph.addVertex(i);

            // agregamos aristas con peso
            graph.addEdgeAndWeight(1, 2, new Random().nextInt(5, 30));
            graph.addEdgeAndWeight(1, 3, new Random().nextInt(5, 30));
            graph.addEdgeAndWeight(2, 3, new Random().nextInt(5, 30));
            graph.addEdgeAndWeight(2, 5, new Random().nextInt(5, 30));
            graph.addEdgeAndWeight(3, 4, new Random().nextInt(5, 30));
            graph.addEdgeAndWeight(4, 5, new Random().nextInt(5, 30));

            // verificamos aristas existentes
            assertTrue(graph.containsEdge(1, 2));
            assertTrue(graph.containsEdge(3, 4));

            // eliminamos una arista
            System.out.println("Remove edge: 1 -- 2");
            graph.removeEdge(1, 2);
            assertFalse(graph.containsEdge(1, 2));

            // eliminamos un vértice
            System.out.println("Remove vertex: 3");
            graph.removeVertex(3);
            assertFalse(graph.containsVertex(3));

        } catch (GraphException | ListException e) {
            throw new RuntimeException(e);
        }
        System.out.println(graph);
    }
}