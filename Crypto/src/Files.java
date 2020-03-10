import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Files {
    public static Map<InputType, String[]> readFile(String path) throws IOException {
        Map<InputType, String[]> out = new HashMap<>();
        try (Scanner sc = new Scanner(new File(path))) {
            String[] line;
            int index = 0;
            while (sc.hasNext()) {
                line = readLine(sc);
                if (line.length > 0) {
                    out.put(InputType.values.get(index), line);
                    index++;
                }
            }
            if (out.size() < 3) {
                throw new IOException("Некорректные входные данные в " + path);
            }
        }
        return out;
    }

    private static String[] readLine(Scanner sc) {
        return Arrays.stream(sc.nextLine().split("[,.; ]+")).filter(str -> !str.isEmpty()).map(String::toUpperCase).toArray(String[]::new);
    }

    public static void writeLine(String path, String data, boolean append) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path, append))) {
            br.write(data);
        }
    }
}
