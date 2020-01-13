import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseToPostfixNotation {
    private String input;
    private static List<String> operators = Arrays.asList("+","-","*","/",")","(","^");

    public ParseToPostfixNotation(String input) {
//      работа с отрицательными числами: Замена выражения вида (-x) на выражение вида (0-x).
        Pattern pattern = Pattern.compile("\\(-\\d+([.,]\\d+)?\\)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String replacement = matcher.group().replaceAll("\\(","(0");
            input = input.replaceAll("\\(" + matcher.group() + "\\)",replacement);
        }
        this.input = input;
    }

    public String parse() {
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String symbol = String.valueOf(input.charAt(i));
            if (operators.contains(symbol)) {
                if (stack.size() > 0 && !symbol.equals("(")) {
                    if (symbol.equals(")")) {
                        String symbolInsideBrackets = stack.pop();
                        while (!symbolInsideBrackets.equals("(")) {
                            output.append(" ").append(symbolInsideBrackets);
                            symbolInsideBrackets = stack.pop();
                        }
                    } else if (getPriority(symbol) > getPriority(stack.peek())) {
                        stack.push(symbol);
                        output.append(" ");
                    } else {
                        while (stack.size() > 0 && getPriority(symbol) <= getPriority(stack.peek())) {
                            output.append(" ").append(stack.pop());
                        }
                        stack.push(symbol);
                        output.append(" ");
                    }
                } else {
                    stack.push(symbol);
//      если строка output уже заканчивается пробелом, то дополнительный пробел добавлять не надо
                    if (output.length() >= 1 && output.charAt(output.length()-1) != ' ') {
                        output.append(" ");
                    }
                }
            } else {
                    output.append(symbol);
            }
        }
        if (stack.size() > 0) {
            while (!stack.isEmpty()) {
                    output.append(" ").append(stack.pop());
            }
        }
        System.out.println(output);
        return output.toString();
    }

    private byte getPriority(String s) {
        switch (s) {
            case "(":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 4;
        }
    }

}
