import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String resultPath = "Report.txt";
    public static final String message = "I, Artsiom Konashchenko, love MiKOZI";

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Operation state = null;
        Elgamal elgamal = new Elgamal();
        try (Scanner input = new Scanner(System.in)) {
            while (state != Operation.EXIT) {
                System.out.println("Доступные операции:\n1) Gen\n2) Sign\n3) Verify\n4) Exit\n\nВаш выбор (введите название): ");
                state = Operation.safeDefine(input.nextLine());
                String res = processSwitchOn(state, elgamal);
                Files.writeLine(res, resultPath, true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String processSwitchOn(Operation state, Elgamal elgamal) throws IOException, NoSuchAlgorithmException {
        String output = null;
        List<BigInteger> fileNumbers;
        switch (state) {
            case GEN: {
                fileNumbers = Files.readAllLinesAsBigInteger("Gen.txt");
                if (fileNumbers.size() == 1) {
                    Numbers.Tuple out = elgamal.gen(fileNumbers.get(0));
                    if (out != null) {
                        output = "p = " + out.getD() + "\nq = " + out.getX() + "\ng = " + out.getY() + "\nоткрытый ключ = " + elgamal.getPublicKey() + "\nличный ключ = " + elgamal.getPrivateKey();

                        String pqg = out.getD() + "\n" + out.getX() + "\n" + out.getY();
                        Files.writeLine(pqg, "Sign.txt", false);
                        Files.writeLine(elgamal.getPrivateKey().toString(), "Sign.txt", true);

                        Files.writeLine(pqg, "Verify.txt", false);
                        Files.writeLine(elgamal.getPublicKey().toString(), "Verify.txt", true);
                    }
                } else {
                    System.out.println("Неверные входные параметры для генерации. Необходим параметр q");
                }
                break;
            }
            case SIGN: {
                fileNumbers = Files.readAllLinesAsBigInteger("Sign.txt");
                if (fileNumbers.size() == 4) {
                    AbstractMap.SimpleEntry<BigInteger, BigInteger> sign = elgamal.sign(new Numbers.Tuple(fileNumbers.get(0), fileNumbers.get(1), fileNumbers.get(2)), fileNumbers.get(3), message);
                    if (sign != null) {
                        output = "r = " + sign.getKey() + "\ns = " + sign.getValue();

                        Files.writeLine(sign.getKey() + "\n" + sign.getValue(), "Verify.txt", true);
                    }
                } else {
                    System.out.println("Неверные входные параметры для подписи. Необходимы четыре числа");
                }
                break;
            }
            case VERIFY: {
                fileNumbers = Files.readAllLinesAsBigInteger("Verify.txt");
                if (fileNumbers.size() == 6) {
                    Numbers.Tuple pqg = new Numbers.Tuple(fileNumbers.get(0), fileNumbers.get(1), fileNumbers.get(2));
                    BigInteger e = fileNumbers.get(3);
                    AbstractMap.SimpleEntry<BigInteger, BigInteger> rs = new AbstractMap.SimpleEntry<>(fileNumbers.get(4), fileNumbers.get(5));
                    boolean verify = elgamal.verify(pqg, e, rs, message);
                    output = "результат проверки - " + verify;
                } else {
                    System.out.println("Неверные входные параметры для проверки. Необходимы шесть чисел");
                }
                break;
            }
            case NULL: {
                System.out.println("Нет такой операции, попробуйте еще раз");
                break;
            }
        }
        return output;
    }
}
