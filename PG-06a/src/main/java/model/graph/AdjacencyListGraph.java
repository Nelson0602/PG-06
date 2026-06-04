package model.graph;

import model.LinkedList.ListException;
import model.Queue.QueueException;
import model.Stack.StackException;

public class AdjacencyListGraph<T extends Comparable<T>> extends AdjacencyMatrixGraph {

    public AdjacencyListGraph(int n, boolean directed) {
        super(n, directed);
    }

    @Override
    public boolean containsEdge(Comparable a, Comparable b) throws GraphException, ListException {
        return super.containsEdge(a, b);
    }

    @Override
    public void addEdge(Comparable a, Comparable b) throws GraphException, ListException {
        super.addEdge(a, b);
    }

    @Override
    public void addWeight(Comparable a, Comparable b, Comparable weight) throws GraphException, ListException {
        super.addWeight(a, b, weight);
    }

    @Override
    public void addEdgeAndWeight(Comparable a, Comparable b, Comparable weight) throws GraphException, ListException {
        super.addEdgeAndWeight(a, b, weight);
    }

    @Override
    public void removeVertex(Comparable element) throws GraphException, ListException {
        super.removeVertex(element);
    }

    @Override
    public void removeEdge(Comparable a, Comparable b) throws GraphException, ListException {
        super.removeEdge(a, b);
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
        return super.dfs();
    }

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        return super.bfs();
    }
}
