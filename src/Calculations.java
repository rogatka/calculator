import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Calculations {
    private String inputInPostfixFormat;
    private static List<String> operators = Arrays.asList("+","-","*","/",")","(","^");

    public Calculations(String inputInPostfixFormat) {
        this.inputInPostfixFormat = inputInPostfixFormat;
    }

    public BigDecimal calculate() {
        Stack<String> stack = new Stack<>();
        BigDecimal result = new BigDecimal(0);
        for (String item : inputInPostfixFormat.split(" ")) {
            if (!operators.contains(item)) {
                stack.push(item);
            }
            else {
                BigDecimal b = new BigDecimal(stack.pop());
                BigDecimal a = new BigDecimal(stack.pop());
                switch (item) {
                    case "+": {
                        result = a.add(b);
                        break;
                    }
                    case "-": {
                        result = a.subtract(b);
                        break;
                    }
                    case "*": {
                        result = a.multiply(b);
                        break;
                    }
                    case "/": {
                        try {
                            System.out.println(a);
                            System.out.println(b);
                            result = a.divide(b);
                        }
                        catch (ArithmeticException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "^": {
                        result = a.pow(b.intValue());
                        break;
                    }
                }
                System.out.println(result);
                stack.push(String.valueOf(result));
            }
        }
        return result;
    }
}
