import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SP {
    private int[] message;
    private int[] k;
    private Map<Integer, Integer[]> iterationsKeys;
    private int[] S1;
    private int[] S2;
    private int[] P;

    public SP(int N, int q, int r, int[] S1, int[] S2, int[] P) {
        initMessage(N, 0);
        this.k = Integer.toBinaryString(4096 - 11 * q * r).chars().map(x -> x - '0').toArray();
        iterationsKeys = new HashMap<>();
        this.S1 = S1;
        this.S2 = S2;
        this.P = P;
    }

    public void run(int[][] rules) throws Exception {
        verifyLength();
        for (int i = 0; i < rules.length; ++i) {
            obtainKey(rules[i], i + 1);
        }
        int iterations = this.iterationsKeys.size();
        int iteration = 1;
        int[] T1 = new int[Main.PART_LENGTH];
        int[] T2 = new int[Main.PART_LENGTH];
        int[] added, P, Y;
        while (iteration <= iterations) {
            added = addition(iteration);

            for (int i = 0, j = added.length / 2; j < added.length; i++, j++) {
                T1[i] = added[i];
                T2[i] = added[j];
            }

            int[] N1 = applySOperation(T1, Number.FIRST);
            if (N1.length < Main.PART_LENGTH) {
                N1 = addZeroBits(N1, Main.PART_LENGTH);
            }
            int[] N2 = applySOperation(T2, Number.SECOND);
            if (N2.length < Main.PART_LENGTH) {
                N2 = addZeroBits(N2, Main.PART_LENGTH);
            }

            P = Main.mergeArrays(N1, N2);
            Y = Arrays.stream(processBits(this.P, P)).mapToInt(Integer::intValue).toArray();

            printIterationResult(Y, iteration);

            System.arraycopy(Y, 0, this.message, 0, Y.length);
            ++iteration;
        }
    }

    public void updateMessage(int N, int increaseOn) {
        this.initMessage(N, increaseOn);
    }

    private void initMessage(int N, int increaseOn) {
        this.message = Integer.toBinaryString(7 * N + increaseOn).chars().map(x -> x - '0').toArray();
    }

    private void verifyLength() throws Exception {
        if (message.length > Main.MESSAGE_LENGTH) {
            throw new Exception("Длина сообщения не равна 8 битам");
        } else if (message.length < Main.MESSAGE_LENGTH) {
            this.message = addZeroBits(this.message, Main.MESSAGE_LENGTH);
        }
        if (k.length > Main.KEY_LENGTH) {
            throw new Exception("Длина ключа не равна 12 битам");
        } else if (k.length < Main.KEY_LENGTH) {
            this.k = addZeroBits(this.k, Main.PART_LENGTH);
        }
    }

    private void obtainKey(int[] rule, int iteration) throws Exception {
        Integer[] key = processBits(rule, this.k);
        iterationsKeys.put(iteration, key);
    }

    private Integer[] processBits(int[] bitsRule, int[] from) throws Exception {
        Integer[] res = new Integer[bitsRule.length];
        int bit;
        for (int i = 0; i < bitsRule.length; ++i) {
            bit = bitsRule[i] - 1;
            if (bit > from.length) {
                throw new Exception("Неверная обработка битов, значение индекса выходит за пределы массива");
            }
            res[i] = from[bit];
        }
        return res;
    }

    private int[] addition(int iteration) {
        Integer[] iterationKey = this.iterationsKeys.get(iteration);
        int[] res = new int[iterationKey.length];
        for (int i = 0; i < res.length; ++i) {
            res[i] = iterationKey[i] ^ message[i];
        }
        return res;
    }

    private int[] applySOperation(int[] arr, Number num) throws Exception {
        String value = Arrays.stream(arr).mapToObj(String::valueOf).reduce(String::concat).orElseThrow(() -> new Exception("Невозможно применить S операцию"));
        int index = Integer.parseInt(value, 2);
        int sValue;
        if (num == Number.FIRST) {
            sValue = this.S1[index];
        } else {
            sValue = this.S2[index];
        }
        String binary = Integer.toBinaryString(sValue);
        return binary.chars().map(x -> x - '0').toArray();
    }

    private void printIterationResult(final int[] arr, final int iteration) throws Exception {
        System.out.print("Результаты " + iteration + "-й итерации: ");
        String answer = Arrays.stream(arr).mapToObj(String::valueOf).reduce(String::concat).orElseThrow(() -> new Exception("Не удалось запарсить результат в число"));
        System.out.println(Main.binToDec(answer));
        System.out.println(Arrays.toString(this.message));
    }

    private int[] addZeroBits(final int[] arr, final int expectedSize) {
        int toAdd = expectedSize - arr.length;
        int[] res = new int[expectedSize];
        for (int i = toAdd, index = 0; i < res.length; ++i, ++index) {
            res[i] = arr[index];
        }
        return res;
    }
}
