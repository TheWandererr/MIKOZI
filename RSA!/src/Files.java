import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Files {
    public static List<BigInteger> readAllLinesAsBigInteger(String path) throws IOException {
        List<BigInteger> out = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                out.add(sc.nextBigInteger());
            }
        }
        return out;
    }

    public static void writeLine(String str, String path, boolean append) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path, append))) {
            if (str != null) {
                br.write(str);
                br.newLine();
            }
        }
    }
}
