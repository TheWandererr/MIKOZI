import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Operation state = null;
        try (Scanner input = new Scanner(System.in)) {
            RSA rsa = new RSA();
            List<BigInteger> fileNumbers;
            while (state != Operation.EXIT) {
                System.out.println("Доступные операции:\n1) Gen\n2) Encr\n3) Decr\n4) Exit\n\nВаш выбор (введите название): ");
                state = Operation.safeDefine(input.nextLine());
                switch (state) {
                    case GEN: {
                        fileNumbers = Files.readAllLinesAsBigInteger("Gen.txt");
                        if (fileNumbers.size() == 3) {
                            BigInteger key = rsa.gen(fileNumbers.get(0), fileNumbers.get(1), fileNumbers.get(2));
                            Files.writeLine("Сгенерированный ключ: " + key.toString(), "Report.txt", true);
                        } else {
                            System.out.println("Неверные входные параметры для генерации. Необходимы три числа");
                        }
                        break;
                    }
                    case ENCRYPT: {
                        fileNumbers = Files.readAllLinesAsBigInteger("Encr.txt");
                        if (fileNumbers.size() == 3) {
                            BigInteger encrypted = rsa.encrypt(fileNumbers.get(0), fileNumbers.get(1), fileNumbers.get(2));
                            if (encrypted != null) {
                                Files.writeLine("Зашифрованное сообщение X1, Y1 = " + encrypted.toString(), "Report.txt", true);
                                BigInteger decrypted = rsa.decrypt(encrypted, rsa.getPrivateKey(), rsa.getModule());
                                Files.writeLine("Рашифрованное сообщение Y1, X1_DECRYPTED = " + decrypted.toString(), "Report.txt", true);
                                Files.writeLine("X1_DECRYPTED == X1 это " + Numbers.equals(decrypted, fileNumbers.get(0)), "Report.txt", true);
                            }
                        } else {
                            System.out.println("Неверные входные параметры для шифрования. Необходимы три числа");
                        }
                        break;
                    }
                    case DECRYPT: {
                        fileNumbers = Files.readAllLinesAsBigInteger("Decr.txt");
                        if (fileNumbers.size() == 3) {
                            BigInteger decrypted = rsa.decrypt(fileNumbers.get(0), fileNumbers.get(1), fileNumbers.get(2));
                            if (decrypted != null) {
                                Files.writeLine("Расшифрованное сообщение Y2, X2 = " + decrypted.toString(), "Report.txt", true);
                            }
                        } else {
                            System.out.println("Неверные входные параметры для расшифрования. Необходимы три числа");
                        }
                        break;
                    }
                    case NULL: {
                        System.out.println("Нет такой операции, попробуйте еще раз");
                        break;
                    }
                }
                // System.out.println(rsa.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
