import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;

public class Elgamal {

    private BigInteger privateKey;
    private BigInteger publicKey;


    public Elgamal() {

    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public Numbers.Tuple gen(BigInteger q) {
        if (Numbers.isPrime(q)) {
            BigInteger tmp = q.add(BigInteger.ONE);
            BigInteger border = BigInteger.valueOf(4L).multiply(tmp);
            try {
                BigInteger R = rValue(border);
                BigInteger p = pValue(q, R);
                BigInteger powRes1 = Numbers.modPow(BigInteger.TWO, q.multiply(R), p);
                BigInteger powRes2 = Numbers.modPow(BigInteger.TWO, R, p);
                while (!Numbers.equals(powRes1, BigInteger.ONE) || Numbers.equals(powRes2, BigInteger.ONE)) {
                    border = R;
                    R = rValue(border);
                    p = pValue(q, R);
                    powRes1 = Numbers.modPow(BigInteger.TWO, q.multiply(R), p);
                    powRes2 = Numbers.modPow(BigInteger.TWO, R, p);
                }
                BigInteger x = xValue(p);
                BigInteger g = Numbers.modPow(x, R, p);
                BigInteger pCopy = new BigInteger(p.toString());
                while (Numbers.equals(g, BigInteger.ONE)) {
                    pCopy = pCopy.subtract(BigInteger.ONE);
                    x = xValue(pCopy);
                    g = Numbers.modPow(x, R, pCopy);
                }
                p = pCopy;

                this.privateKey = Numbers.getProbablyRandInRange(BigInteger.ZERO, q);
                this.publicKey = Numbers.modPow(g, this.privateKey, p);
                return new Numbers.Tuple(p, q, g);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
                System.out.println("Попробуйте запустить алгоритм еще раз для получения других случайных чисел");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            // System.out.println(Numbers.isPrime(p));
        } else {
            System.out.println("q должно быть простым для генерации");
        }
        return null;
    }

    private BigInteger rValue(BigInteger border) {
        return Numbers.getEvenLessThen(border);
    }

    private BigInteger pValue(BigInteger q, BigInteger R) {
        return q.multiply(R).add(BigInteger.ONE);
    }

    private BigInteger xValue(BigInteger border) {
        if (Numbers.equals(border, BigInteger.ZERO)) {
            System.out.println("Невозможо найти x для таких входных параметров");
            return null;
        }
        return Numbers.getProbablyRandInRange(BigInteger.ZERO, border);
    }

    private BigInteger hashSHA256(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(message.getBytes(), 0, message.length());
        return new BigInteger(1, digest.digest());
    }

    public AbstractMap.SimpleEntry<BigInteger, BigInteger> sign(Numbers.Tuple pqg, BigInteger privateKey, String message) throws NoSuchAlgorithmException {
        BigInteger p = pqg.getD();
        BigInteger q = pqg.getX();
        BigInteger g = pqg.getY();
        if (Numbers.inRange(privateKey, BigInteger.ZERO, q)) {
            BigInteger k = Numbers.getProbablyRandInRange(BigInteger.ONE, q);
            BigInteger r = Numbers.modPow(g, k, p);

            BigInteger m = hashSHA256(message);

            BigInteger tmp = m.subtract(privateKey.multiply(r));
            BigInteger s = Numbers.gcdWide(k, q).getX().multiply(tmp).mod(q);
            return new AbstractMap.SimpleEntry<>(r, s);
        } else {
            System.out.println("d не лежит Zq для подписи");
            return null;
        }
    }

    public boolean verify(Numbers.Tuple pqg, BigInteger publicKey, AbstractMap.SimpleEntry<BigInteger, BigInteger> rs, String message) throws NoSuchAlgorithmException {
        BigInteger p = pqg.getD();
        BigInteger q = pqg.getX();
        BigInteger g = pqg.getY();
        BigInteger r = rs.getKey();
        BigInteger s = rs.getValue();
        if (!Numbers.inRange(r, BigInteger.ONE, p) || !Numbers.inRange(s, BigInteger.ZERO, q)) {
            return false;
        }
        BigInteger m = hashSHA256(message);
        BigInteger first = Numbers.modPow(publicKey, r, p);
        BigInteger second = Numbers.modPow(r, s, p);
        BigInteger left = first.multiply(second).mod(p);
        BigInteger right = Numbers.modPow(g, m, p);
        return Numbers.equals(left, right);
    }
}
