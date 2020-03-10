import java.util.HashMap;
import java.util.Map;

public enum InputType {
    TEXT,
    KEY,
    OPERATION;

    public static Map<Integer, InputType> values;

    static {
        values = new HashMap<>();
        values.put(0, TEXT);
        values.put(1, KEY);
        values.put(2, OPERATION);
    }
}
