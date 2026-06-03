package model;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public static List<String> steps = new ArrayList<>();

    //BÚSQUEDA BINARIA RECURSIVA  –  O(log n)
    public static int binarySearch(int[] sortedArray, int value, int low, int high) {
        // Caso base: rango agotado
        if (low > high) {
            steps.add("No encontrado");
            return -1;
        }

        int mid = low + (high - low) / 2;

        steps.add("Rango [" + low + "," + high + "] -->mid=" + mid
                + " (sortedArray[mid]=" + sortedArray[mid] + ")");

        if (value == sortedArray[mid]) {
            steps.add("Encontrado en índice " + mid);
            return mid;

        } else if (value < sortedArray[mid]) {
            // Backtracking: mitad izquierda
            return binarySearch(sortedArray, value, low, mid - 1);

        } else {
            // Backtracking: mitad derecha
            return binarySearch(sortedArray, value, mid + 1, high);
        }
    }


    //BÚSQUEDA BINARIA ITERATIVA  –  O(log n)
    public static int binarySearchIterative(int[] sortedArray, int value) {
        int low = 0;
        int high = sortedArray.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            steps.add("Rango [" + low + "," + high + "] -->mid=" + mid
                    + " (sortedArray[mid]=" + sortedArray[mid] + ")");

            if (value == sortedArray[mid]) {
                steps.add("Encontrado en índice " + mid);
                return mid;

            } else if (value < sortedArray[mid]) {
                high = mid - 1;

            } else {
                low = mid + 1;
            }
        }

        steps.add("No encontrado");
        return -1;
    }

    public static class MinMax {
        public int min;
        public int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

    }
    public static MinMax findMinMax(int[] arr, int low, int high) {
        //Case base: solo hay un elemento
        if(low==high){
            steps.add("Caso base 1 elemento: min=="+arr[low]+" max=="+arr[high]);
            return new MinMax(arr[low], arr[low]);
        }
        //Caso base: dos elementos
        if(high == low + 1){
            steps.add("Caso base 2 elementos: min=="+Math.min(arr[low], arr[high])+
                    ", max=="+Math.max(arr[low], arr[high]));
            return new MinMax(Math.min(arr[low], arr[high]), Math.max(arr[low], arr[high]));
        }

        //En otro caso, debemos dividir el arreglo en dos mitades
        int mid = (low+high)/2;
        steps.add("Rango ["+low+","+high+"] -->mid="+mid+", arr[mid]=="+arr[mid]);
        steps.add("leftResult = findMinMax(arr,"+low+","+mid+") -->low=="+low
                +", arr[low]=="+arr[low]+", high=="+mid+", arr[mid]=="+arr[mid]);
        MinMax leftResult = findMinMax(arr, low, mid); //la mitad a la izq
        steps.add("rightResult = findMinMax(arr,"+mid+1+","+high+") -->low=="+mid+1
                +", arr[mid+1]=="+arr[mid+1]+", high=="+high+", arr[high]=="+arr[high]);
        MinMax rightResult = findMinMax(arr, mid+1, high); //la mitad a la der

        //Podemos combinar los resultados
        int min = Math.min(leftResult.getMin(), rightResult.getMin());
        steps.add("valor de la variable min =="+min);
        int max = Math.max(leftResult.getMax(), rightResult.getMax());
        steps.add("valor de la variable max =="+max);

        return new MinMax(min, max);
    }


}