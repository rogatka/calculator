import exceptions.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputExpressionCheck {
    private static List<String> operators = Arrays.asList("+", "-", "*", "/", ")", "(", "^");

    public static boolean check(String inputExpression) throws ExpressionCheckException {
        Pattern pattern1 = Pattern.compile("[-+*/^(]+[+*/^.,]");
        Pattern pattern2 = Pattern.compile("[-+*/^.,][-+*/^)]+");
        Pattern pattern3 = Pattern.compile("[-+*/^.,]{2,}");
        Pattern pattern4 = Pattern.compile("\\d\\(");
        long numberOfLeftBrackets = inputExpression.chars().filter(symbol -> symbol == '(').count();
        long numberOfRightBrackets = inputExpression.chars().filter(symbol -> symbol == ')').count();
        if (inputExpression.isEmpty()) {
            throw new EmptyExpressionException("Выражение не должно быть пустым!");
        }
        if (inputExpression.matches(".*[^-0-9.,/^*+)(].*")) {
            throw new UnaccaptableSymbolsException("Вы ввели недопустимые символы!");
        }
        if (numberOfLeftBrackets != numberOfRightBrackets) {
            throw new NumberOfBracketsNotEqualsException("Количество открывающихся и закрывающихся скобок в выражении должно быть одинаковым!");
        }
        if (pattern1.matcher(inputExpression).find() || pattern2.matcher(inputExpression).find() || inputExpression.matches("[^-+(\\d\\s].*")) {
            throw new UnaccapteableSequenceException("В выражении найдена недопустимая последовательность операндов!");
        }
        if (pattern3.matcher(inputExpression).find()) {
            throw new RepeatOperandException("В выражении найден повторяющийся операнд!");
        }
        if (pattern4.matcher(inputExpression).find()) {
            throw new UnaccapteableSequenceException("Вы забыли указать операнд перед скобкой!");
        }
        for (String operator : operators) {
            if (!operator.equals(")") && inputExpression.endsWith(operator)) {
                throw new UnexpectedExpressionFinishException("Выражение не должно заканчиваться операндом!");
            }
        }
        return true;
    }

}
