package model;

import java.math.BigInteger;
import java.util.Random;

public class Probabilistic {
    public int [] randomSearch(int[] arr, int value, int attempts){
        int[] result = new int[2];
        //Random random = new Random();
        int count = 0;
        while(true){
            int index = new Random().nextInt(arr.length-1);//valor a buscar
            result[0] = index;
            result[1] = ++count;

            //continuar con el desarollo del metodo
            if(arr[index] == value) return result;//retorna indice y numero de intentos realizados
            if(count >= attempts){
                result[0] = -1;//significa que no se encontro el valor
                return result;
            }

        }
    }

    public String millerRabin(String bigNumber) {
        String result="";
        bigNumber = bigNumber.replaceAll("\\s+", "");
        //BigInteger number = new BigInteger("104729"); // Ejemplo de número primo
        BigInteger number = new BigInteger(bigNumber);
        int k = 5; // Número de repeticiones del test de Miller-Rabin
        if (isProbablePrime(number, k))
            result += "The big number: " + number + " is probably prime.";
        else
            result += "The big number: " + number + " is not prime.";
        return result;
    }

    private boolean isProbablePrime(BigInteger n, int k) {
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger THREE = BigInteger.valueOf(3);
        BigInteger ONE = BigInteger.ONE;
        BigInteger ZERO = BigInteger.ZERO;

        // Casos base
        if (n.compareTo(TWO) < 0) return false;      // 0, 1 y negativos
        if (n.equals(TWO) || n.equals(THREE)) return true;
        if (n.mod(TWO).equals(ZERO)) return false;   // pares > 2

        // Escribir n - 1 como d * 2^s
        BigInteger d = n.subtract(ONE);
        int s = 0;
        while (d.mod(TWO).equals(ZERO)) {
            d = d.divide(TWO);
            s++;
        }

        Random random = new Random();

        for (int i = 0; i < k; i++) {
            BigInteger a;

            // Elegir un testigo aleatorio en [2, n - 2]
            do {
                a = new BigInteger(n.bitLength(), random);
            } while (a.compareTo(TWO) < 0 || a.compareTo(n.subtract(TWO)) > 0);

            BigInteger x = a.modPow(d, n);

            if (x.equals(ONE) || x.equals(n.subtract(ONE))) {
                continue;
            }

            boolean passed = false;
            for (int r = 1; r < s; r++) {
                x = x.multiply(x).mod(n);

                if (x.equals(n.subtract(ONE))) {
                    passed = true;
                    break;
                }

                if (x.equals(ONE)) {
                    return false;
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }

}