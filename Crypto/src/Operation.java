import java.util.HashMap;
import java.util.Map;

public enum Operation {
    ENCRYPT,
    DECRYPT;

    public static Map<String, Operation> values;

    static {
        values = new HashMap<>();
        values.put("ЗАШИФРОВАНИЕ", ENCRYPT);
        values.put("РАСШИФРОВАНИЕ", DECRYPT);
    }

    public static Operation safeDefine(String operation) throws Exception {
        if (Operation.values.containsKey(operation.toUpperCase())) {
            return values.get(operation.toUpperCase());
        }
        throw new Exception("Нет такой операции");
    }
}