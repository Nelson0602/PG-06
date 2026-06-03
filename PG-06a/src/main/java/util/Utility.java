package util;

import java.text.DecimalFormat;
import java.util.Random;

public class Utility {

    public static String format(long value) {
        return new DecimalFormat("#,###,###").format(value);
    }


    public static int[] generatedSorted(int size, int maxVal) {
        int[] arr = new Random().ints(size, 1, maxVal+1).distinct().limit(size).sorted().toArray();
        return arr;

    }
}
