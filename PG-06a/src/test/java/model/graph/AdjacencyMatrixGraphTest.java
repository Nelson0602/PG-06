package model.graph;

import model.LinkedList.ListException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {

    @Test
    void testAdjecencyMatrixGraph() {
        AdjacencyMatrixGraph<Integer> graph = new AdjacencyMatrixGraph<>(10, false);
        try {
            //agregamos vertices
        for (int i = 0; i < 10; i++)
            graph.addVertex(i);

        //agregamos aristas
            graph.addEdgeAndWeight(1,2, new Random().nextInt(5,30));
            graph.addEdgeAndWeight(1,3, new Random().nextInt(5,30));
            graph.addEdgeAndWeight(2,3, new Random().nextInt(5,30));
            graph.addEdgeAndWeight(2,5, new Random().nextInt(5,30));
            graph.addEdgeAndWeight(3,4, new Random().nextInt(5,30));
            graph.addEdgeAndWeight(4,5, new Random().nextInt(5,30));

            //eliminemos algunos vertices
            System.out.println("Remove vertex: 1");
            graph.removeVertex(1);
    }catch (GraphException | ListException e) {
            throw new RuntimeException(e);
    }
        System.out.println(graph);
    }

}