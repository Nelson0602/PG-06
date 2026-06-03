package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BTreeTest {

    @Test
    void testRandomNumbers() {
        //arbol binario simple con 100 números aleatorios entre 200 y 500
        BTree<Integer> bTree = new BTree<>();
        Random random = new Random();

        System.out.println("=== BTree: 100 números aleatorios (200-500) ===");
        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(200, 501);
            bTree.add(value);
        }

        //recorridos
        System.out.println(bTree);

        try {
            //size(), min(), max()
            System.out.println("Tree size: " + bTree.size());
            System.out.println("Min value: " + bTree.min());
            System.out.println("Max value: " + bTree.max());

            //contains para al menos 5 elementos
            System.out.println("\n=== contains (5 verificaciones) ===");
            int verificados = 0;
            for (int i = 200; i <= 500 && verificados < 5; i += 60) {
                System.out.println(
                        bTree.contains(i)
                                ? "[" + i + "] exists . Height " + bTree.height(i)
                                : "[" + i + "] not exists"
                );
                verificados++;
            }

            //eliminar 5 elementos
            System.out.println("\n=== Eliminando 5 elementos ===");
            int eliminados = 0;
            int intentos = 0;
            while (eliminados < 5 && intentos < 100) {
                int value = random.nextInt(200, 501);
                if (bTree.contains(value)) {
                    bTree.remove(value);
                    System.out.println("Removing: " + value);
                    eliminados++;
                }
                intentos++;
            }

            //mostrar árbol tras eliminación
            System.out.println("\n=== Árbol tras eliminaciones ===");
            System.out.println(bTree);

            //altura de cada elemento
            System.out.println("=== Altura de cada nodo ===");
            System.out.println(bTree.nodeHeight());

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAlphabet() {
        //arbol de búsqueda binaria con letras del alfabeto
        BST<String> alphabetTree = new BST<>();

        System.out.println("=== BST: Letras del alfabeto ===");
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabetTree.add(String.valueOf(c));
        }
        System.out.println(alphabetTree);

        try {
            System.out.println("Tree size: " + alphabetTree.size());
            System.out.println("Min value: " + alphabetTree.min());
            System.out.println("Max value: " + alphabetTree.max());

            //contains para 5 letras
            System.out.println("\n=== contains (5 letras) ===");
            for (String letter : new String[]{"A", "G", "M", "T", "Z"}) {
                System.out.println(
                        alphabetTree.contains(letter)
                                ? "[" + letter + "] exists . Height " + alphabetTree.height(letter)
                                : "[" + letter + "] not exists"
                );
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNames() {
        //arbol binario simple con 10 nombres de personas
        BTree<String> nameTree = new BTree<>();
        String[] names = {
                "Ana", "Carlos", "Diana", "Eduardo", "Fernanda",
                "Gabriel", "Helena", "Ivan", "Julia", "Kevin"
        };

        System.out.println("=== BTree: 10 nombres de personas ===");
        for (String name : names) {
            nameTree.add(name);
        }
        System.out.println(nameTree);

        try {
            System.out.println("Tree size: " + nameTree.size());
            System.out.println("Min value: " + nameTree.min());
            System.out.println("Max value: " + nameTree.max());

            //contains para 5 nombres
            System.out.println("\n=== contains (5 nombres) ===");
            for (String name : new String[]{"Ana", "Diana", "Gabriel", "Julia", "Kevin"}) {
                System.out.println(
                        nameTree.contains(name)
                                ? "[" + name + "] exists . Height " + nameTree.height(name)
                                : "[" + name + "] not exists"
                );
            }

            //eliminar 5 elementos (uno de los árboles almacenados → nombre)
            System.out.println("\n=== Eliminando 5 nombres ===");
            for (String name : new String[]{"Ana", "Carlos", "Fernanda", "Ivan", "Kevin"}) {
                if (nameTree.contains(name)) {
                    nameTree.remove(name);
                    System.out.println("Removing: " + name);
                }
            }

            //mostrar árbol
            System.out.println(nameTree);

            //altura de cada nodo
            System.out.println("=== Altura de cada nodo ===");
            System.out.println(nameTree.nodeHeight());

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}