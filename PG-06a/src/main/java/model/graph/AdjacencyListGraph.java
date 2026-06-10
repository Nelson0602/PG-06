package model.graph;

import model.LinkedList.ListException;
import model.Node;
import model.Queue.QueueException;
import model.Stack.StackException;

public class AdjacencyListGraph<T extends Comparable<T>> extends AdjacencyMatrixGraph<T> {

    public AdjacencyListGraph(int n, boolean directed) {
        super(n, directed);
    }

    @Override
    public boolean containsEdge(T a, T b) throws GraphException, ListException {
        if(isEmpty()) throw new GraphException("Adjacency List Graph is Empty");
        Vertex<T> vertexA = getVertex(a);
        if(vertexA == null) return false;
        boolean getVertexA = getNodeNeighbor(vertexA.headNode, b) != null;
        boolean getVertexB = false;
        if(!directed) {
            Vertex<T> vertexB = getVertex(b);
            if(vertexB != null)
                getVertexB = getNodeNeighbor(vertexB.headNode, a) != null;
        }
        return !directed ? getVertexA && getVertexB : getVertexA;
    }

    private Node<T> getNodeNeighbor(Node<T> headNode, T element) {
        if(headNode == null) return null;
        Node<T> aux = headNode;
        while(aux != null){
            if(aux.data.compareTo(element) == 0) return aux;
            aux = aux.neighbor;
        }
        return null;
    }

    @Override
    public void addEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        if(!containsEdge(a, b)) {
            Vertex<T> vertexA = getVertex(a);
            vertexA.headNode = addNeighbor(vertexA.headNode, b, null);
            if(!directed) {
                Vertex<T> vertexB = getVertex(b);
                vertexB.headNode = addNeighbor(vertexB.headNode, a, null);
            }
        }
    }

    private Node<T> addNeighbor(Node<T> headNode, T element, Object weight) {
        Node<T> node = new Node<>(element, weight);
        if(headNode == null)
            headNode = node;
        else{
            Node<T> aux = headNode;
            while(aux.neighbor != null)
                aux = aux.neighbor;
            aux.neighbor = node;
        }
        return headNode;
    }

    private Vertex<T> getVertex(T element) {
        for (int i = 0; i < counter; i++)
            if(equals(vertexList[i].data, element)) return vertexList[i];
        return null;
    }

    @Override
    public void addWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        if(containsEdge(a, b)) {
            Vertex<T> vertexA = getVertex(a);
            Node<T> nodeNeighbor = getNodeNeighbor(vertexA.headNode, b);
            if(nodeNeighbor != null) nodeNeighbor.weight = weight;
            if(!directed) {
                Vertex<T> vertexB = getVertex(b);
                Node<T> nodeNeighborB = getNodeNeighbor(vertexB.headNode, a);
                if(nodeNeighborB != null) nodeNeighborB.weight = weight;
            }
        }
    }

    @Override
    public void addEdgeAndWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        if(!containsEdge(a, b)) {
            Vertex<T> vertexA = getVertex(a);
            vertexA.headNode = addNeighbor(vertexA.headNode, b, weight);
            if(!directed) {
                Vertex<T> vertexB = getVertex(b);
                vertexB.headNode = addNeighbor(vertexB.headNode, a, weight);
            }
        }
    }

    @Override
    public void removeVertex(T element) throws GraphException, ListException {
        if(!containsVertex(element))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        int index = indexOf(element);
        if(index != -1) {
            for (int i = index; i < counter - 1; i++)
                vertexList[i] = vertexList[i + 1];
            vertexList[counter - 1] = null;
            counter--;

            for (int i = 0; i < counter; i++) {
                Vertex<T> vertex = vertexList[i];
                vertex.headNode = removeNeighborIfExists(vertex.headNode, element);
            }
        }
    }

    private Node<T> removeNeighborIfExists(Node<T> headNode, T element) {
        if(headNode == null) return null;
        // Caso 1: el primer nodo es el que hay que eliminar
        if(equals(headNode.data, element))
            return headNode.neighbor;
        // Caso 2: está en medio o al final
        Node<T> prev = headNode;
        while(prev.neighbor != null){
            if(equals(prev.neighbor.data, element)){
                prev.neighbor = prev.neighbor.neighbor;
                break; // solo hay una arista por par de vértices
            }
            prev = prev.neighbor;
        }
        return headNode;
    }

    @Override
    public void removeEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        if(!containsEdge(a, b))
            throw new GraphException("Adjacency List Graph Not Contains Edge");
        Vertex<T> vertexA = getVertex(a);
        vertexA.headNode = removeNeighborIfExists(vertexA.headNode, b);
        if(!directed) {
            Vertex<T> vertexB = getVertex(b);
            vertexB.headNode = removeNeighborIfExists(vertexB.headNode, a);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|——————| |Adjacency List Graph|——————| |\n");
        String graphType = directed ? "Directed" : "Undirected";
        sb.append("※※※※※※Graph Type: ").append(graphType).append("\n");
        for (int i = 0; i < counter; i++) {
            sb.append("\nThe vertex in position [").append(i).append("] is: ")
                    .append(vertexList[i].data);
        }
        for (int i = 0; i < counter; i++) {
            sb.append("\n( ").append(i).append(" )----Vertex [ ").append(getVertexByIndex(i).data).append(" ]");
            Node<T> aux = getVertexByIndex(i).headNode;
            while(aux != null){
                sb.append("\n※※※ Edge: ").append(aux.data).append(", weight: ").append(aux.weight);
                aux = aux.neighbor;
            }
        }
        return sb.toString();
    }

    @Override
    public String dfs() throws GraphException, model.Stack.StackException, ListException {
        return super.dfs();
    }

    @Override
    public String bfs() throws GraphException, model.Queue.QueueException, ListException {
        return super.bfs();
    }
}
