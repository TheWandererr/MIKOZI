import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Numbers {

    public static Tuple gcdWide(BigInteger a, BigInteger b) {
        Tuple tuple = new Tuple(a, BigInteger.ONE, BigInteger.ZERO);
        Tuple tuple2;

        if (b.equals(BigInteger.ZERO)) {
            return tuple;
        }

        tuple2 = gcdWide(b, a.mod(b));

        tuple = new Tuple();
        tuple.setD(tuple2.getD());
        tuple.setX(tuple2.getY());
        tuple.setY(tuple2.getX().subtract(a.divide(b).multiply(tuple2.getY())));

        return tuple;
    }

    public static <T extends Number> boolean lessOrEqualsThen(T left, T right) throws IllegalArgumentException {
        Class clazz = left.getClass();
        if (clazz == Integer.class) {
            return left.intValue() <= right.intValue();
        } else if (clazz == Double.class) {
            return left.doubleValue() <= right.doubleValue();
        } else if (clazz == Float.class) {
            return left.floatValue() <= right.floatValue();
        } else if (clazz == BigInteger.class) {
            return ((BigInteger) left).compareTo((BigInteger) right) <= 0;
        } else if (clazz == BigDecimal.class) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) <= 0;
        } else if (clazz == Byte.class) {
            return left.byteValue() <= right.byteValue();
        } else if (clazz == Short.class) {
            return left.shortValue() <= right.shortValue();
        } else {
            throw new IllegalArgumentException("Incompatible types to compare");
        }
    }

    public static <T extends Number> boolean lessThen(T left, T right) throws IllegalArgumentException {
        Class clazz = left.getClass();
        if (clazz == Integer.class) {
            return left.intValue() < right.intValue();
        } else if (clazz == Double.class) {
            return left.doubleValue() < right.doubleValue();
        } else if (clazz == Float.class) {
            return left.floatValue() < right.floatValue();
        } else if (clazz == BigInteger.class) {
            return ((BigInteger) left).compareTo((BigInteger) right) < 0;
        } else if (clazz == BigDecimal.class) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) < 0;
        } else if (clazz == Byte.class) {
            return left.byteValue() < right.byteValue();
        } else if (clazz == Short.class) {
            return left.shortValue() < right.shortValue();
        } else {
            throw new IllegalArgumentException("Incompatible types to compare");
        }
    }

    public static <T extends Number> boolean equals(T left, T right) throws IllegalArgumentException {
        Class clazz = left.getClass();
        if (clazz == Integer.class) {
            return left.intValue() == right.intValue();
        } else if (clazz == Double.class) {
            return left.doubleValue() == right.doubleValue();
        } else if (clazz == Float.class) {
            return left.floatValue() == right.floatValue();
        } else if (clazz == BigInteger.class) {
            return ((BigInteger) left).compareTo((BigInteger) right) == 0;
        } else if (clazz == BigDecimal.class) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) == 0;
        } else if (clazz == Byte.class) {
            return left.byteValue() == right.byteValue();
        } else if (clazz == Short.class) {
            return left.shortValue() == right.shortValue();
        } else {
            throw new IllegalArgumentException("Incompatible types to compare");
        }
    }

    public static boolean isPrime(int number) {
        return isPrime(BigInteger.valueOf(number));
    }

    public static boolean isPrime(long number) {
        return isPrime(BigInteger.valueOf(number));
    }

    public static boolean isPrime(BigInteger number) {
        return number.isProbablePrime(1);
    }

    public static BigInteger modPow(BigInteger num, BigInteger degree, BigInteger module) {
        BigInteger rez = BigInteger.ONE;
        while (!equals(degree, BigInteger.ZERO)) {
            if (!equals(degree.mod(BigInteger.TWO), BigInteger.ZERO)) {
                rez = (rez.multiply(num)).mod(module);
                degree = degree.subtract(BigInteger.ONE);
            }
            degree = degree.divide(BigInteger.TWO);
            num = (num.multiply(num)).mod(module);
        }
        return rez;
    }

    public static BigInteger binPow(BigInteger num, BigInteger degree) {
        BigInteger res = BigInteger.ONE;
        while (!equals(degree, BigInteger.ZERO)) {
            if (!equals(degree.and(BigInteger.ONE), BigInteger.ZERO))
                res = res.multiply(num);
            num = num.multiply(num);
            degree = degree.shiftRight(1);
        }
        return res;
    }

    public static int getEvenLessThen(int border) {
        Random r = new Random();
        int val = r.nextInt(border);
        return val % 2 == 0 ? val : val - 1;
    }

    public static long getEvenLessThen(long border) {
        long val = ThreadLocalRandom.current().nextLong(border);
        return val % 2L == 0 ? val : val - 1L;
    }

    public static BigInteger getEvenLessThen(BigInteger border) {
        BigInteger val = getProbablyRandInRange(BigInteger.ZERO, border);
        BigInteger residue = val.mod(BigInteger.TWO);
        return equals(residue, BigInteger.ZERO) ? val : val.subtract(BigInteger.ONE);
    }

    public static BigInteger getProbablyRandInRange(BigInteger left, BigInteger right) throws ArithmeticException {
        BigInteger value = right.subtract(left);
        Random randNum = new Random();
        int len = right.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (lessThen(res, left))
            res = res.add(left);
        if (lessOrEqualsThen(value, res))
            res = res.mod(value).add(left);
        return res;
    }

    public static <T extends Number> boolean inRange(T val, T left, T right) {
        return lessOrEqualsThen(val, right) && lessOrEqualsThen(left, val);
    }

    public static class Tuple {

        private BigInteger d;
        private BigInteger x;
        private BigInteger y;

        Tuple(BigInteger one, BigInteger two, BigInteger three) {
            d = one;
            x = two;
            y = three;
        }

        public Tuple() {
        }

        public BigInteger getD() {
            return d;
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }

        public void setD(BigInteger d) {
            this.d = d;
        }

        public void setX(BigInteger x) {
            this.x = x;
        }

        public void setY(BigInteger y) {
            this.y = y;
        }
    }
}
