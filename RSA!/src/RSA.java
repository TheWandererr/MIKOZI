import java.math.BigInteger;

public class RSA {
    private final static BigInteger one = BigInteger.ONE;
    private final static int LEFT_LESS = 1;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger module;
    private BigInteger message;
    private BigInteger phi;

    public RSA() {

    }

    public BigInteger gen(BigInteger p, BigInteger q, BigInteger publicKey) {
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
        if (phi.gcd(publicKey).equals(one)) {
            this.module = p.multiply(q);
            this.publicKey = publicKey;
            //TODO this.privateKey = Numbers.gcdWide(this.publicKey, phi).getX();
            this.privateKey = this.publicKey.modInverse(phi);
            this.phi = phi;
            return this.privateKey;
        } else {
            System.out.println("Неподходящий публичный ключ, попробуйте еще раз");
            return null;
        }
    }

    public BigInteger encrypt(BigInteger message, BigInteger key, BigInteger module) {
        if (isCorrectMessage(message)) {
            this.message = message;
            this.publicKey = key;
            this.module = module;
            return modPow(this.message, this.publicKey, this.module);
        } else {
            System.out.println("Сообщение не принадлежит Z");
            return null;
        }
    }

    public BigInteger decrypt(BigInteger message, BigInteger key, BigInteger module) {
        if (isCorrectMessage(message)) {
            this.message = message;
            this.module = module;
            this.privateKey = key;
            return modPow(this.message, this.privateKey, this.module);
        } else {
            System.out.println("Сообщение не принадлежит Z");
            return null;
        }
    }

    private boolean isCorrectMessage(BigInteger message) {
        if (this.phi != null) {
            BigInteger tmp = this.phi.subtract(one);
            return Numbers.lessOrEqualsThen(message, tmp) && Numbers.lessOrEqualsThen(BigInteger.ZERO, message);
        } else {
            System.out.println("Необходимо инициализировать поле Z, сгенерируйте ключ");
            return false;
        }
    }

    private BigInteger modPow(BigInteger num, BigInteger degree, BigInteger module) {
        BigInteger rez = BigInteger.ONE;
        while (!degree.equals(BigInteger.ZERO)) {
            if (!(degree.mod(BigInteger.TWO)).equals(BigInteger.ZERO)) {
                rez = (rez.multiply(num)).mod(module);
                degree = degree.subtract(BigInteger.ONE);
            }
            degree = degree.divide(BigInteger.TWO);
            num = (num.multiply(num)).mod(module);
        }
        return rez;
    }

    @Override
    public String toString() {
        return "RSA{" +
                "privateKey=" + privateKey +
                ", publicKey=" + publicKey +
                ", module=" + module +
                ", message=" + message +
                ", phi=" + phi +
                '}';
    }

    public BigInteger getModule() {
        return module;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }
}
