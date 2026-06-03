package model.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BSTTest {

    @Test
    void testAdd() {
        BST<Integer> bst = new BST<>();
        Random random = new Random();

        //valores numéricos NO repetidos entre 0 y 50
        System.out.println("=== BST: 30 valores no repetidos (0-50) ===");
        List<Integer> inserted = new ArrayList<>();
        int intentos = 0;
        while (inserted.size() < 30 && intentos < 200) {
            int value = random.nextInt(0, 51);
            if (!inserted.contains(value)) {
                bst.add(value);
                inserted.add(value);
            }
            intentos++;
        }
        System.out.println(bst);

        try {
            //size(), height(), height(element), min, max
            System.out.println("Tree size: " + bst.size());
            System.out.println("Tree height: " + bst.height());
            System.out.println("Min value: " + bst.min());
            System.out.println("Max value: " + bst.max());

            //height(element) para los primeros 5 insertados
            System.out.println("\n=== Altura por elemento (primeros 5 insertados) ===");
            for (int i = 0; i < 5 && i < inserted.size(); i++) {
                int val = inserted.get(i);
                System.out.println(
                        bst.contains(val)
                                ? "[" + val + "] exists . Height " + bst.height(val)
                                : "[" + val + "] not exists"
                );
            }

            //valores aleatorios entre 0 y 50 → contains y remove si existe
            System.out.println("\n=== 20 valores aleatorios: contains + remove si existe ===");
            for (int i = 0; i < 20; i++) {
                int value = random.nextInt(0, 51);
                if (bst.contains(value)) {
                    bst.remove(value);
                    System.out.println("[" + value + "] exists → Removing");
                } else {
                    System.out.println("[" + value + "] not exists");
                }
            }

            System.out.println(bst);

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPreOrderWithHeight() {
        BST<Integer> bst = new BST<>();
        Random random = new Random();

        System.out.println("=== BST: PreOrder con altura de cada nodo ===");
        List<Integer> inserted = new ArrayList<>();
        int intentos = 0;
        while (inserted.size() < 30 && intentos < 200) {
            int value = random.nextInt(0, 51);
            if (!inserted.contains(value)) {
                bst.add(value);
                inserted.add(value);
            }
            intentos++;
        }

        //preOrder indicando altura de cada elemento
        try {
            System.out.println(bst.nodeHeight());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRemove() {
        BST<Integer> bst = new BST<>();
        Random random = new Random();

        System.out.println("=== BST: Prueba de eliminación ===");
        List<Integer> inserted = new ArrayList<>();
        int intentos = 0;
        while (inserted.size() < 30 && intentos < 200) {
            int value = random.nextInt(0, 51);
            if (!inserted.contains(value)) {
                bst.add(value);
                inserted.add(value);
            }
            intentos++;
        }
        System.out.println(bst);

        try {
            for (int i = 0; i < 20; i++) {
                int value = random.nextInt(0, 51);
                if (bst.contains(value)) {
                    bst.remove(value);
                    System.out.println("Removing: " + value);
                }
            }
            System.out.println(bst);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}