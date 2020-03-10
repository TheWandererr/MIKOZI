import java.math.BigInteger;
import java.util.Optional;

public class Afin extends Algorithm {

    private Integer a;
    private Integer b;
    private Integer reverseA;
    private BigInteger mod;

    private void initParams(String[] key) throws Exception {
        this.mod = BigInteger.valueOf(Alphabet.forward.size());
        int[] parsedKey = parseKey(key);
        validateKey(parsedKey);
        this.a = parsedKey[0];
        this.b = parsedKey[1];
        this.reverseA = BigInteger.valueOf(a).modInverse(mod).intValue();
    }

    private int[] parseKey(String[] key) throws NumberFormatException {
        int[] parsedKey = new int[2];
        for (int i = 0; i < 2; i++) {
            parsedKey[i] = Integer.parseInt(key[i]);
        }
        return parsedKey;
    }

    private void validateKey(int[] parsedKey) throws Exception {
        BigInteger gcd = this.mod.gcd(BigInteger.valueOf(parsedKey[0]));
        if (!gcd.equals(BigInteger.ONE)) {
            throw new Exception("Ключ для алгоритма \"Афинный\" введен неверно");
        }
    }

    @Override
    public String encrypt(String[] words, String[] key) throws Exception {
        initParams(key);
        StringBuilder res = new StringBuilder();
        for (String word : words) {
            for (char symbol : word.toCharArray())
                res.append(encryptValue(symbol));
            res.append(" ");
        }
        return res.toString();
    }

    @Override
    public String decrypt(String[] words, String[] key) throws Exception {
        initParams(key);
        StringBuilder res = new StringBuilder();
        for (String word : words) {
            for (char symbol : word.toCharArray())
                res.append(decryptValue(symbol));
            res.append(" ");
        }
        return res.toString();
    }

    private char encryptValue(char value) throws Exception {
        int key = Optional.ofNullable(Alphabet.forward.getOrDefault(value, null)).orElseThrow(() -> new Exception("Не найден символ в алфавите для " + value));
        int res = Math.floorMod(a * key + b, mod.intValue());
        return Optional.ofNullable(Alphabet.backward.getOrDefault(res, null)).orElseThrow(() -> new Exception("Не найден символ в алфавите для " + res));
    }

    private char decryptValue(char value) throws Exception {
        int key = Optional.ofNullable(Alphabet.forward.getOrDefault(value, null)).orElseThrow(() -> new Exception("Не найден символ в алфавите для " + value));
        int res = Math.floorMod(reverseA * (key - b), mod.intValue());
        return Optional.ofNullable(Alphabet.backward.getOrDefault(res, null)).orElseThrow(() -> new Exception("Не найден символ в алфавите для" + res));
    }
} 