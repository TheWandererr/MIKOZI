import java.math.BigInteger;

public class Hill extends Algorithm {

    private Integer[][] key;
    private Integer[][] reverseKey;
    private BigInteger determinant;
    private BigInteger mod;

    private void initParams(String[] key) throws Exception {
        this.mod = BigInteger.valueOf(Alphabet.forward.size());
        parseAndValidateKey(key);
    }

    private void parseAndValidateKey(String[] key) throws Exception {
        int length = key.length;
        double value = Math.sqrt(length);
        int size = (int) value;
        if (value != size) {
            throw new Exception("Ключ для алгоритма \"Хилла\" задан некорректно");
        }
        this.key = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.key[i][j] = Integer.parseInt(key[size * i + j]);
            }
        }
        this.determinant = ArrayUtils.determinant(this.key, Integer.class).toBigInteger();
        if (this.determinant.mod(mod).equals(BigInteger.ZERO) || !this.determinant.gcd(mod).equals(BigInteger.ONE)) {
            throw new Exception("Ключ для алгоритма \"Хилла\" задан некорректно");
        }
    }

    @Override
    public String encrypt(String[] words, String[] key) throws Exception {
        initParams(key);
        StringBuilder res = new StringBuilder();
        for (String word : words) {
            Character[][] wordMatrix = parseWord(word);
            for (int j = 0; j < wordMatrix.length; j++) {
                Character[] row = ArrayUtils.getCopyOfRow(wordMatrix, j, Character.class);
                Integer[] converted = convert(row);
                Integer[] beforeEncrypting = ArrayUtils.multiplicationIntegerVectorOnMatrix(converted, this.key, this.mod.intValue());
                Character[] encrypted = convert(beforeEncrypting);
                res.append(ArrayUtils.toPrimitive(encrypted));
            }
            res.append(" ");
        }
        return res.toString();
    }

    @Override
    public String decrypt(String[] words, String[] key) throws Exception {
        initParams(key);
        initReverseKey();
        StringBuilder res = new StringBuilder();
        for (String word : words) {
            Character[][] wordMatrix = parseWord(word);
            for (int j = 0; j < wordMatrix.length; j++) {
                Character[] row = ArrayUtils.getCopyOfRow(wordMatrix, j, Character.class);
                Integer[] converted = convert(row);
                Integer[] beforeDecrypting = ArrayUtils.multiplicationIntegerVectorOnMatrix(converted, this.reverseKey, this.mod.intValue());
                Character[] encrypted = convert(beforeDecrypting);
                res.append(ArrayUtils.toPrimitive(encrypted));
            }
            res.append(" ");
        }
        return res.toString();
    }


    private void initReverseKey() throws Exception {
        this.reverseKey = ArrayUtils.reverse(this.key, this.mod, this.determinant);
    }

    private Character[][] parseWord(String word) throws Exception {
        int size = this.key.length;
        int dif = Math.floorMod(word.length(), size);
        if (dif != 0) {
            throw new Exception("Невозможно разбить слово \"" + word + "\" на блоки длиной " + size);
        }
        int vectors = word.length() / size;
        Character[][] matrix = new Character[vectors][size];
        char[] array = word.toCharArray();
        for (int i = 0; i < vectors; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = array[size * i + j];
            }
        }
        return matrix;
    }

    private Integer[] convert(Character[] arr) throws Exception {
        Integer[] converted = new Integer[arr.length];
        Character symbol;
        Integer res;
        for (int i = 0; i < arr.length; i++) {
            symbol = arr[i];
            res = Alphabet.forward.getOrDefault(symbol, null);
            if (res != null) {
                converted[i] = res;
            } else {
                throw new Exception("Не найден символ в алфавите для " + symbol);
            }

        }
        return converted;
    }

    private Character[] convert(Integer[] arr) throws Exception {
        Character[] converted = new Character[arr.length];
        int digit;
        Character symbol;
        for (int i = 0; i < arr.length; i++) {
            digit = arr[i];
            symbol = Alphabet.backward.getOrDefault(digit, null);
            if (symbol != null) {
                converted[i] = symbol;
            } else {
                throw new Exception("Не найден символ в алфавите для " + digit);
            }
        }
        return converted;
    }
}
