package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class AVLTest {

    @Test
    void testAdd() {
        AVL<Integer> avl = new AVL<>();

        //Insertar 30 números aleatorios entre 20 y 200
        System.out.println("=== AVL: Inserción de 30 números aleatorios (20-200) ===");
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int value = random.nextInt(20, 201);
            avl.add(value);
        }

        //mostrar contenido del árbol
        System.out.println(avl);

        try {
            //probar size(), min(), max()
            System.out.println("Tree size: " + avl.size());
            System.out.println("Min value: " + avl.min());
            System.out.println("Max value: " + avl.max());

            //verificar si el árbol está balanceado
            System.out.println("\n=== Balance antes de eliminar ===");
            System.out.println("¿Está balanceado? " + avl.isBalanced());

            //eliminar 5 elementos del árbol
            System.out.println("\n=== Eliminando 5 elementos ===");
            int eliminados = 0;
            int intentos = 0;
            while (eliminados < 5 && intentos < 50) {
                int value = random.nextInt(20, 201);
                if (avl.contains(value)) {
                    avl.remove(value);
                    System.out.println("Removing: " + value);
                    eliminados++;
                }
                intentos++;
            }

            //mostrar contenido tras eliminación
            System.out.println("\n=== Árbol tras eliminaciones ===");
            System.out.println(avl);

            //verificar si sigue balanceado
            System.out.println("=== Balance después de eliminar ===");
            System.out.println("¿Está balanceado? " + avl.isBalanced());

            //el AVL ya rebalancea automáticamente en remove(),
            //      pero mostramos el estado final para comprobar
            System.out.println("\n=== Estado final del árbol AVL ===");
            System.out.println(avl);
            System.out.println("¿Árbol balanceado (verificación final)? " + avl.isBalanced());

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRemove() {
        AVL<Integer> avl = new AVL<>();
        Random random = new Random();

        System.out.println("=== AVL: Prueba de eliminación ===");
        for (int i = 0; i < 30; i++) {
            int value = random.nextInt(20, 201);
            avl.add(value);
        }
        System.out.println(avl);

        try {
            System.out.println("¿Balanceado antes? " + avl.isBalanced());

            //eliminar hasta 5 elementos encontrados
            int eliminados = 0;
            for (int i = 20; i <= 200 && eliminados < 5; i += 10) {
                if (avl.contains(i)) {
                    avl.remove(i);
                    System.out.println("Removing: " + i);
                    eliminados++;
                }
            }

            System.out.println(avl);
            System.out.println("¿Balanceado después? " + avl.isBalanced());
            System.out.println("Tree size: " + avl.size());
            System.out.println("Min value: " + avl.min());
            System.out.println("Max value: " + avl.max());

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNodeHeight() {
        AVL<Integer> avl = new AVL<>();

        System.out.println("=== AVL: Altura de cada nodo en preOrder ===");
        for (int v : new int[]{100, 50, 150, 25, 75, 125, 175, 10, 30}) {
            avl.add(v);
        }
        System.out.println(avl);

        try {
            System.out.println(avl.nodeHeight());
            System.out.println("¿Balanceado? " + avl.isBalanced());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}