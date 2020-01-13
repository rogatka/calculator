import consolehelper.ConsoleHelper;
import exceptions.*;

public class Calculator {

    public static void main(String[] args) {
        String expression = ConsoleHelper.readString();
        while (true) {
            try {
                if (InputExpressionCheck.check(expression)) break;
            } catch (NumberOfBracketsNotEqualsException | UnaccapteableSequenceException |
                    RepeatOperandException | UnexpectedExpressionFinishException |
                    UnaccaptableSpacesException | EmptyExpressionException e) {
                ConsoleHelper.writeMessage(e.getMessage());
                ConsoleHelper.writeMessage("Повторите попытку!");
            }
            expression = ConsoleHelper.readString();
        }
        ParseToPostfixNotation parseToPostfixNotation = new ParseToPostfixNotation(expression);
        Calculations calculations = new Calculations(parseToPostfixNotation.parse());
        System.out.println(calculations.calculate());
    }

}
