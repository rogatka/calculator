import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Calculations {
    private String inputInPostfixFormat;
    private static List<String> operators = Arrays.asList("+", "-", "*", "/", ")", "(", "^");

    public Calculations(String inputInPostfixFormat) {
        this.inputInPostfixFormat = inputInPostfixFormat;
    }

    public BigDecimal calculate() {
        Deque<String> deque = new ArrayDeque<>();
        BigDecimal result = new BigDecimal(0);
        for (String item : inputInPostfixFormat.split(" ")) {
            if (!operators.contains(item)) {
                deque.push(item);
            } else {
                BigDecimal b = new BigDecimal(deque.pop());
                BigDecimal a = new BigDecimal(deque.pop());
                switch (item) {
                    case "+": {
                        result = a.add(b, MathContext.DECIMAL32);
                        break;
                    }
                    case "-": {
                        result = a.subtract(b, MathContext.DECIMAL32);
                        break;
                    }
                    case "*": {
                        result = a.multiply(b, MathContext.DECIMAL32);
                        break;
                    }
                    case "/": {
                        result = a.divide(b, MathContext.DECIMAL32);
                        break;
                    }
                    case "^": {
//                      если степень является вещественным числом, то используется метод класса Math:
                        if (b.remainder(BigDecimal.valueOf(1)).compareTo(BigDecimal.ZERO) != 0) {
                            result = BigDecimal.valueOf(Math.pow(a.doubleValue(), b.doubleValue()));
                        }
//                      иначе - метод класса BigDecimal, с более высокой точностью:
                        else {
                            result = a.pow(b.intValue(), MathContext.DECIMAL32);
                        }
                        break;
                    }
                }
                deque.push(String.valueOf(result));
            }
        }
        return result;
    }
}
