public class Main {

    public static final String NAME = "Артем";
    public static final String SURNAME = "Конащенко";
    public static final int MESSAGE_LENGTH = 8;
    public static final int PART_LENGTH = MESSAGE_LENGTH / 2;
    public static final int KEY_LENGTH = 12;
    public static final int N = 21;
    private static final int[][] roundKeys = {
            {1, 2, 3, 4, 5, 6, 7, 8},
            {1, 2, 3, 4, 9, 10, 11, 12},
            {5, 6, 7, 8, 12, 11, 10, 9}
    };
    private static final int[] S1 = {11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4};
    private static final int[] S2 = {11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6};
    private static final int[] P = {1, 3, 5, 7, 2, 4, 6, 8};


    public static void main(String[] args) {
        SP sp = new SP(N, NAME.length(), SURNAME.length(), S1, S2, P);
        try {
            sp.run(roundKeys);
            sp.updateMessage(N, 1);
            sp.run(roundKeys);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int[] mergeArrays(int[] a, int[] b) {
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static int pow(int a, int b) {
        int result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }

    public static int binToDec(String bin) {
        int res = 0, a = 0, mult = 0;
        char[] symbols = bin.toCharArray();
        for (int len = symbols.length - 1; len >= 0; len--) {
            int temp = 0;
            a = Character.getNumericValue(symbols[len]);
            temp = a * pow(2, mult);
            mult++;
            res += temp;
        }
        return res;
    }
}
