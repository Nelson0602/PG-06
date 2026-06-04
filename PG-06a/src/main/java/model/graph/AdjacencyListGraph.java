package model.graph;

import model.LinkedList.ListException;
import model.Node;
import model.Queue.QueueException;
import model.Stack.StackException;

public class AdjacencyListGraph<T extends Comparable<T>> extends AdjacencyMatrixGraph {

    public AdjacencyListGraph(int n, boolean directed) {
        super(n, directed);
    }

    @Override
    public boolean containsEdge(Comparable a, Comparable b) throws GraphException, ListException {
        boolean getVertexA = false;
        boolean getVertexB = false;
        Vertex<T> vertexA = getVertex(a);
        getVertexA = getNodeNeigbor(vertexA.headnode, b) != null;
        if(!directed) {
            Vertex<T> vertexB = getVertex(b);
            getVertexB = getNodeNeigbor(vertexB.headnode, a) != null;
        }
        return !directed ? getVertexA && getVertexB : getVertexA;
    }

    private Node<T> getNodeNeigbor(Node<T> headnode, T element) {
        if(headnode== null) return null;
        Node<T> aux = headnode;
        while (aux != null) {
            if(aux.data.compareTo(element) == 0) return aux;
            aux = aux.neighbor; //Movemos aux al siguiente nodo vecino
            
        }
        return null;//Si llega aqui, no enfontró el nodo
    }

    @Override
    public void addEdge(Comparable a, Comparable b) throws GraphException, ListException {
        if (!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if (!containsEdge(a, b)) {
            Vertex<T> vertexA = getVertex(a);
            vertexA.headnode = addNeighbor(vertexA.headnode, b, null);
            if(!directed){
                Vertex<T> vertexB = getVertex(b);
                Vertex<T> vertexB = addNeighbor(vertexB.headnode, a, null);
            }
        }
    }

    private Node<T>  addNeighbor(Node<T> headNode, T element, Object weight){
        Node<T> node = new Node<>(element, weight);
        if (headNode != null) {
            headNode = node;
            else{
                Node<T> aux = headNode;
                //Me muevo por la lista hasta el ultimo nodo
                while(aux.neighbor != null)
                    aux = aux.neighbor; //Se mueve al siguiente nodo vecino
                    //se sale cuando auxiliar.neigbor es  nulo
                aux.neighbor = node; //Entonces conectamos el nodo al final
            }
            return headNode;//Si llego nulo, lo devuelve con un nodo
        }
    }

    private Vertex<T> getVertex(T element){
        for ( int i = 0; i< counter; i++){
            if (equals(vertexList[i].data, element)) return vertexList[i];
                return null;
        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency Matrix Graph: \n");
        String typeGraph = directed ? "Directed" : "Undirected";
        sb.append(" Graph Type: ").append(typeGraph).append("\n");
        //mostramos todos los vertices
        for (int i = 0; i < n; i++) {
            sb.append("\nThe vertex in position: [").append(i)
                    .append("] is: ").append(vertexList[i].data);
        }
        //mostramos la informacion de aristas y pesos
        for(int i = 0; i< counter; i++){
            sb.append("\n( ").append(i).append(" )----Vertex [ ").append(getVertexByIndex(i).data).append(" ]");
            Node<T> aux = getVertexByIndex(i).headnode;
            while (aux != null) {
                sb.append("\n Edge: ").append(aux.data).append(" weight:").append(aux.weight);
                aux = aux.neighbor;//Lo movemos al siguiente nodo vecino
            }
        }
        return sb.toString(); //
    }
}
