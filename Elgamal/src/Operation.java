import java.util.HashMap;
import java.util.Map;

public enum Operation {
    GEN,
    SIGN,
    VERIFY,
    EXIT,
    NULL;

    public static Map<String, Operation> values;

    static {
        values = new HashMap<>();
        values.put("GEN", GEN);
        values.put("SIGN", SIGN);
        values.put("VERIFY", VERIFY);
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
