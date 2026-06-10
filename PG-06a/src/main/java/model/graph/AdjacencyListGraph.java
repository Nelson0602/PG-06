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
        if(isEmpty()) throw new GraphException("Adjacency Matrix Graph is Empty");
        Vertex<T> vertexA = getVertex(a);
        boolean getVertexA = getNodeNeighbor(vertexA.headNode, b)!=null;
        boolean getVertexB = false;
        if(!directed) {
            Vertex<T> vertexB = getVertex(b);
            getVertexB = getNodeNeighbor(vertexB.headNode, a) != null;
        }
        return !directed ? getVertexA && getVertexB : getVertexA;
    }

    private Node<T> getNodeNeighbor(Node<T> headNode, T element) {
        if(headNode==null) return null;
        Node<T> aux = headNode;
        while(aux!=null){
            if(aux.data.compareTo(element)==0) return aux;
            aux = aux.neighbor; //movemos aux al sgte nodo vecino
        }
        return null; //si llega aquí, no encontró el nodo
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
                vertexB.headNode =  addNeighbor(vertexB.headNode, a, null);
            }
        }
    }

    private Node<T> addNeighbor(Node<T> headNode, T element, Object weight) {
        Node<T> node = new Node<>(element, weight);
        if(headNode == null)
            headNode = node;
        else{
            Node<T> aux = headNode;
            //me muevo por la lista hasta el ult nodo
            while(aux.neighbor != null)
                aux = aux.neighbor; //se mueve al sgte nodo vecino
            //se sale cuando aux.neighbor es nulo
            aux.neighbor = node; //entonces conectados el nodo al final
        }
        return headNode; //si llegó nulo, lo devuelve con un nodo
    }

    private Vertex<T> getVertex(T element) {
        for (int i = 0; i < counter; i++)
            if(equals(vertexList[i].data, element)) return vertexList[i];
        return null; //no existe el vértice
    }

    @Override
    public void addWeight(T a, T b, T weight) throws GraphException, ListException {
        super.addWeight(a, b, weight);
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
                vertexB.headNode =  addNeighbor(vertexB.headNode, a, weight);
            }
        }
    }

    @Override
    public void removeVertex(T element) throws GraphException, ListException {
        if(!containsVertex(element))
            throw new GraphException("Adjacency List Graph Not Contains Vertex");
        //ahora que sabemos q el vértice existe, procedemos a buscarlo para eliminarlo
        int index = indexOf(element); //devuelve el índice del vértice a eliminar
        if(index!=-1) { //si el vértice existe en la lista de vértices
            for (int i = index; i < counter - 1; i++)
                vertexList[i] = vertexList[i + 1];
            counter--; //lo debemos decrementar por el vértice suprimido

            //ahora debemos buscar el rastro del vértice suprimido en las listas enlazadas
            //de vecinos de los otros vértices
            for (int i = 0; i < counter; i++) {
                Vertex<T> vertex = vertexList[i];
                vertex.headNode = removeNeighbor(vertex.headNode, element);
            }
        }
    }

    private Node<T> removeNeighbor(Node<T> headNode, T element) throws ListException {
        if(headNode==null) throw new ListException("Linked List in Graph is Empty");
        //Caso 1. el elemento a suprimir es el primero
        if(equals(headNode.data, element))
            headNode = headNode.neighbor; //queda apuntando al sgte nodo vecino
            //Caso 2. El elemento a suprimir puede estar en medio o al final de la lista
        else{
            Node<T> prev = headNode; //anterior
            while(prev.neighbor!=null){
                if(equals(prev.neighbor.data, element)){
                    Node<T> removed = prev.neighbor; //es el nodo a eliminar
                    //desenlaza el nodo
                    prev.neighbor = removed.neighbor;
                }
                prev = prev.neighbor; //lo movemos al sgte vecino
            }
        }
        return headNode; //modificado sin el elemento eliminado
    }

    @Override
    public void removeEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(!containsEdge(a, b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Edge");
        if(containsEdge(a, b)) {
            Vertex<T> vertexA = getVertex(a);
            vertexA.headNode = removeNeighbor(vertexA.headNode, b);
            if(!directed) {
                Vertex<T> vertexB = getVertex(b);
                vertexB.headNode =  removeNeighbor(vertexB.headNode, a);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|——————| |Adjacency List Graph|——————| |\n");
        String graphType = directed ? "Directed" : "Undirected";
        sb.append("※※※※※※Graph Type: ").append(graphType).append("\n");
        //mostramos todos los vértices
        for (int i = 0; i < counter; i++) {
            sb.append("\nThe vertex in position [").append(i).append("] is: ")
                    .append(vertexList[i].data);
        }
        //mostramos la información de aristas y pesos
        for (int i = 0; i < counter; i++) {
            sb.append("\n( ").append(i).append(" )----Vertex [ ").append(getVertexByIndex(i).data).append(" ]");
            Node<T> aux = getVertexByIndex(i).headNode; //llamanos al inicio de la lista enlazada del nodo
            while(aux!=null){
                sb.append("\n※※※ Edge: ").append(aux.data).append(", weight: ").append(aux.weight);
                aux = aux.neighbor; //lo movemos al sgte vecino con quien tiene una arista
            }
        }
        return sb.toString();
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
