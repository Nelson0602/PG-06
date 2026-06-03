package model.graph;

import model.Node;

public class Vertex<T> {
    public T data;
    public Node<T> headnode; //sirve para crear la lista enlazada de aristas y pesos
    private boolean visited;//sirve para los recorridos de DFS Y DBFS

    public Vertex(T data) {
       this.data = data;
       this.headnode = null;
       this.visited = false;
    }
}
