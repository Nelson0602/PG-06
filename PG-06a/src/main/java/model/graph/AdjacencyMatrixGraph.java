package model.graph;

import model.LinkedList.ListException;
import model.Queue.QueueException;
import model.Stack.StackException;

public class AdjacencyMatrixGraph<T extends Comparable<T>>implements Graph<T> {

    public int n;
    public Vertex<T>[] vertexList; //arreglpo estatico de objeto tipo vertex
    private T[][] adjacencyMatrix; //arreglo multidimensional tipo matriz
    public int counter; //contador de vertices agregados
    private boolean directed; //true si el grafo es dirigido

    public AdjacencyMatrixGraph(int n, boolean b) {
    if(n<=0) System.exit(1);
    this.n = n;
    this.vertexList = new Vertex[n];
    this.adjacencyMatrix = (T[][]) new Comparable[n][n];
    this.counter = 0;
    initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = (T) Integer.valueOf(0);
            }
        }
    }

    @Override
    public int size() throws ListException {
        return counter;
    }

    @Override
    public void clear() {
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = (T[][]) new Comparable[n][n];
        this.counter = 0;
        initMatrix();
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public boolean containsVertex(T element) throws GraphException, ListException {
       if(isEmpty()) throw new GraphException("Adjacency Matrix Graph is Empty");
       for (int i = 0; i < counter; i++) {
           if(element.equals(vertexList[i].data)) return true;
       }
       return false; //no lo encontro
    }

    @Override
    public boolean containsEdge(T a, T b) throws GraphException, ListException {
        if(isEmpty()) throw new GraphException("Adjacency Matrix Graph is Empty");
        return !equals(adjacencyMatrix[indexOf(a)][indexOf(b)], (T)Integer.valueOf(0));
    }

    @Override
    public void addVertex(T element) throws GraphException, ListException {
      if(counter>= vertexList.length) throw new GraphException("Adjacency Matrix Graph is full");
      vertexList[counter++] = new Vertex<>(element);
    }

    @Override
    public void addEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
       if(!containsEdge(a, b)) {
           adjacencyMatrix[indexOf(a)][indexOf(b)] = (T) Integer.valueOf(1);
           //graafo no dirigido
           if(!directed)    adjacencyMatrix[indexOf(a)][indexOf(b)] = (T) Integer.valueOf(1);
       }

    }

    private int indexOf(T element) throws GraphException, ListException {
        for (int i = 0; i < counter; i++) {
            if(element.equals(vertexList[i].data)) return i;
        }
        return -1; //no encontro la data del vertice
    }

    @Override
    public void addWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");

        if(containsEdge(a, b)) {
            adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
            //graafo no dirigido
            if(!directed) adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
        }
    }

    @Override
    public void addEdgeAndWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(!containsEdge(a, b)) {
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        //graafo no dirigido
            if(!directed)  adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
        }
    }

    @Override
    public void removeVertex(T element) throws GraphException, ListException {
        int index = indexOf(element);
        if (index != -1) { //Si el vertice existe en la lista de vertices
            for(int i = index; i < counter-1; i++){
                vertexList[i] = vertexList[i+1];

            }
        }
    }

    @Override
    public void removeEdge(T a, T b) throws GraphException, ListException {

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
        //mostramos todas las aristas
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < counter; j++) {
                if (!adjacencyMatrix[i][j].equals(0)) { //si existe una arista
                    sb.append("\nThe edge between the vertexes: ")
                            .append(vertexList[i].data)
                            .append(" .......... ").append(vertexList[j].data);
                    //valido que tenga pesos, si es el caso se muestran
                    if (!adjacencyMatrix[i][j].equals(1))
                        sb.append(". Weight: ").append(adjacencyMatrix[i][j]);

                }
            }
        }
        return sb.toString(); //
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
        return "";
    }

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        return "";
    }

///// AYUDAS ////////////
    public boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    // Método genérico de comparación
    public int compareElements(T a, T b) {
        return a.compareTo(b);
    }
}
