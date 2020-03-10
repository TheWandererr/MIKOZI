import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String res = null;
        try {
            /*Map<InputType, String[]> params = Files.readFile("afin.txt");
            Afin afin = new Afin();
            Operation operation = Operation.safeDefine(params.get(InputType.OPERATION)[0]);
            if (operation == Operation.ENCRYPT) {
                res = afin.encrypt(params.get(InputType.TEXT), params.get(InputType.KEY));
            } else if (operation == Operation.DECRYPT) {
                res = afin.decrypt(params.get(InputType.TEXT), params.get(InputType.KEY));
            }
            Files.writeLine("Report.txt", res, false);*/

            Map<InputType, String[]> params1 = Files.readFile("hill.txt");
            Hill hill = new Hill();
            Operation operation1 = Operation.safeDefine(params1.get(InputType.OPERATION)[0]);
            if (operation1 == Operation.ENCRYPT) {
                res = hill.encrypt(params1.get(InputType.TEXT), params1.get(InputType.KEY));
            } else if (operation1 == Operation.DECRYPT) {
                res = hill.decrypt(params1.get(InputType.TEXT), params1.get(InputType.KEY));
            }
            Files.writeLine("Report.txt", res, true);
        } catch (Exception io) {
            System.out.println(io.getMessage());
        }


    }
}