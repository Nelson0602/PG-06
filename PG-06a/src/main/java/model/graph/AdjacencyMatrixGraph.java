package model.graph;

import model.LinkedList.ListException;
import model.Queue.LinkedQueue;
import model.Queue.QueueException;
import model.Stack.LinkedStack;
import model.Stack.StackException;

public class AdjacencyMatrixGraph<T extends Comparable<T>> implements Graph<T> {
    public int n; //tam máximo de la matriz
    public Vertex<T>[] vertexList; //arreglo estático de objetos tipo Vertex
    private T[][] adjacencyMatrix; //arreglo multidimensional tipo matriz
    public int counter; //contador de vértices agregados
    public final boolean directed; //true si el grafo es dirigido

    //atributos para los recorridos dfs, bfs
    public LinkedStack<Integer> stack;
    public LinkedQueue<Integer> queue;


    public AdjacencyMatrixGraph(int n, boolean directed) {
        if (n <= 0) System.exit(1);
        this.n = n;
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = (T[][]) new Comparable[n][n];
        this.stack = new LinkedStack<>();
        this.queue = new LinkedQueue<>();
        this.counter = 0;
        this.directed = directed;
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
        return false; //no lo encontró
    }

    @Override
    public boolean containsEdge(T a, T b) throws GraphException, ListException {
        if(isEmpty()) throw new GraphException("Adjacency Matrix Graph is Empty");
        return !equals(adjacencyMatrix[indexOf(a)][indexOf(b)], (T)Integer.valueOf(0));
    }

    @Override
    public void addVertex(T element) throws GraphException, ListException {
        if(counter >=vertexList.length)
            throw new GraphException("Adjacency Matrix Graph is Full");
        vertexList[counter++] = new Vertex<>(element);
    }

    @Override
    public void addEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(!containsEdge(a, b)) {
            adjacencyMatrix[indexOf(a)][indexOf(b)] = (T) Integer.valueOf(1);
            //grafo no dirigido
            if(!directed) adjacencyMatrix[indexOf(b)][indexOf(a)] = (T) Integer.valueOf(1);
        }
    }

    public int indexOf(T element) {
        for (int i = 0; i < counter; i++) {
            if(element.equals(vertexList[i].data)) return i;
        }
        return -1; //no encontró la data del vértice
    }

    @Override
    public void addWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(containsEdge(a, b)) {
            adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
            //grafo no dirigido
            if(!directed) adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
        }
    }

    @Override
    public void addEdgeAndWeight(T a, T b, T weight) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(!containsEdge(a, b)) {
            adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
            //grafo no dirigido
            if(!directed) adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
        }
    }

    @Override
    public void removeVertex(T element) throws GraphException, ListException {
        int index = indexOf(element); //devuelve el índice del vértice a eliminar
        if(index!=-1){ //si el vértice existe en la lista de vértices
            for (int i = index; i < counter-1; i++) {
                vertexList[i] = vertexList[i+1];

                //ahora hacemos lo mismo en la matriz con las filas, columnas
                //es decir, eliminamos las aristas
                //primero movemos todas las filas, una pos hacia arriba
                for (int j = 0; j < counter; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i+1][j];
                }
            }
            //luego movemos todas las cols una pos a la izq
            for (int i = 0; i < counter; i++) {
                for (int j = index; j < counter-1; j++)
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j+1];
            }
            //al final se debe decrementar el contador de vértices existente
            //eliminamos los datos "sucios" de la lista de vértices y la matriz
            vertexList[counter] = null;
            counter--;
            for (int i = 0; i < counter; i++) {
                //cambia las filas y la col es la ult (columna "sucia")
                adjacencyMatrix[i][counter] = (T) Integer.valueOf(0);
                //cambia las cols y la fila es la ult (fila "sucia")
                adjacencyMatrix[counter][i] = (T) Integer.valueOf(0);
            }
        }
    }

    @Override
    public void removeEdge(T a, T b) throws GraphException, ListException {
        if(!containsVertex(a) || !containsVertex(b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Vertex");
        if(!containsEdge(a, b))
            throw new GraphException("Adjacency Matrix Graph Not Contains Edge");
        int i = indexOf(a); //obtenemos la pos del vértice "a"
        int j = indexOf(b); //obtenemos la pos del vértice "b"
        if(i!=-1 && j!=-1){ //si los dos vértices existen
            adjacencyMatrix[i][j] = (T)Integer.valueOf(0);
            if(!directed) adjacencyMatrix[j][i] = (T)Integer.valueOf(0);
        }
    }

    public String printMatrix() {
        String result = "";
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < counter; j++) {
                result+= adjacencyMatrix[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|——————| |Adjacency Matrix Graph|——————| |\n");
        String graphType = directed ? "Directed" : "Undirected";
        sb.append("※※※※※※Graph Type: ").append(graphType).append("\n");
        //mostramos todos los vértices
        for (int i = 0; i < counter; i++) {
            sb.append("\nThe vertex in position [").append(i).append("] is: ")
                    .append(vertexList[i].data);
        }
        //mostramos todas las aristas de cada vértice
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < counter; j++) {
                if(!adjacencyMatrix[i][j].equals(0)){ //si existe una arista
                    sb.append("\nThere is edge between the vertexes: ")
                            .append(vertexList[i].data)
                            .append("..........").append(vertexList[j].data);
                    //valido que tenga pesos, si es el caso se muestran
                    if(!adjacencyMatrix[j][i].equals(1))
                        sb.append(". Weight: ").append(adjacencyMatrix[j][i]);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
        setVisited(false);//marca todos los vertices como no vistados
        // inicia en el vertice 0
        String info =vertexList[0].data+", ";
        vertexList[0].setVisited(true); // lo marca
        stack.clear();
        stack.push(0); //lo apila
        while( !stack.isEmpty() ){
            // obtiene un vertice adyacente no visitado,
            //el que esta en el tope de la pila
            int index = adjacentVertexNotVisited((int) stack.top());
            if(index==-1) // no lo encontro
                stack.pop();
            else{
                vertexList[index].setVisited(true); // lo marca
                info+=vertexList[index].data+", "; //lo muestra
                stack.push(index); //inserta la posicion
            }
        }
        return info;
    }

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false);//marca todos los vertices como no visitados
        // inicia en el vertice 0
        String info =vertexList[0].data+", ";
        vertexList[0].setVisited(true); // lo marca
        queue.clear();
        queue.enQueue(0); // encola el elemento
        int v2;
        while(!queue.isEmpty()){
            int v1 = (int) queue.deQueue(); // remueve el vertice de la cola
            // hasta que no tenga vecinos sin visitar
            while((v2=adjacentVertexNotVisited(v1)) != -1 ){
                // obtiene uno
                vertexList[v2].setVisited(true); // lo marca
                info+=vertexList[v2].data+", "; //lo muestra
                queue.enQueue(v2); // lo encola
            }
        }
        return info;
    }

    //setteamos el atributo visitado del vertice respectivo
    private void setVisited(boolean value) {
        for (int i = 0; i < counter; i++) {
            vertexList[i].setVisited(value); //value==true o false
        }//for
    }

    private int adjacentVertexNotVisited(int index) {
        for (int i = 0; i < counter; i++) {
            if(!adjacencyMatrix[index][i].equals(0)
                    && !vertexList[i].isVisited())
                return i;//retorna la posicion del vertice adyacente no visitado
        }//for i
        return -1;
    }

    //***********************HELPERS**************************
    public boolean equals(T a, T b)  {
        return a==null ? b==null : a.equals(b);
    }

    //método genérico de comparación
    public int compareElements(T a, T b) {
        return a.compareTo(b);
    }

    public Vertex<T> getVertexByIndex(int index) {
        for (int i = 0; i < counter; i++)
            if(i==index) return this.vertexList[i];
        return null; //no existe el vertice
    }
}
