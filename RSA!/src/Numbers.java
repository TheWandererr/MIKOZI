import java.math.BigDecimal;
import java.math.BigInteger;

public class Numbers {

    public static Tuple gcdWide(BigInteger a, BigInteger b) {
        Tuple tuple = new Tuple(a, BigInteger.ONE, BigInteger.ZERO);
        Tuple tuple2;

        if (b.equals(BigInteger.ZERO)) {
            return tuple;
        }

        tuple2 = gcdWide(b, a.mod(b));

        tuple = new Tuple();
        tuple.d = tuple2.d;
        tuple.x = tuple2.y;
        tuple.y = tuple2.x.subtract(a.divide(b).multiply(tuple2.y));

        return tuple;
    }

    public static boolean lessOrEqualsThen(BigInteger left, BigInteger right) {
        return left.compareTo(right) <= 0;
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
    }
}
