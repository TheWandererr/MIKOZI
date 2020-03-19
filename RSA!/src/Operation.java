import java.util.HashMap;
import java.util.Map;

public enum Operation {
    GEN,
    ENCRYPT,
    DECRYPT,
    EXIT,
    NULL;

    public static Map<String, Operation> values;

    static {
        values = new HashMap<>();
        values.put("GEN", GEN);
        values.put("DECR", DECRYPT);
        values.put("ENCR", ENCRYPT);
        values.put("EXIT", EXIT);
        values.put("NULL", NULL);
    }

    public static Operation safeDefine(String operation) throws RuntimeException {
        String op = operation.toUpperCase();
        if (Operation.values.containsKey(op)) {
            return values.get(op);
        }
        return values.get("NULL");
    }
}
