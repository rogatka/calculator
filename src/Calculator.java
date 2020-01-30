import consolehelper.ConsoleHelper;
import exceptions.*;

public class Calculator {

    public static void main(String[] args) {
        String expression = "";
        while (true) {
            ConsoleHelper.writeMessage("Пожалуйста, введите выражение: ");
            try {
                expression = ConsoleHelper.readString().replaceAll(" ", "");
                if (InputExpressionCheck.check(expression)) {
                    ParseToPostfixNotation parseToPostfixNotation = new ParseToPostfixNotation(expression);
                    Calculations calculations = new Calculations(parseToPostfixNotation.parse());
                    ConsoleHelper.writeMessage("Результат вычисления: " + calculations.calculate());
                }
            } catch (ExpressionCheckException e) {
                ConsoleHelper.writeMessage(e.getMessage());
                ConsoleHelper.writeMessage("Повторите попытку!");
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage("Ошибка вычислений. Возможно, попытка вычислить корень от отрицательного числа. Повторите ввод.");
            } catch (ArithmeticException e) {
                ConsoleHelper.writeMessage("Ошибка в результате выполнения арифметической операции(Возможно, деление на ноль). " +
                        "Введите корректное выражение!");
            } catch (ExitException e) {
                ConsoleHelper.writeMessage("До свидания!");
                break;
            }
        }
    }

}
