import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseToPostfixNotation {
    private String input;
    private static List<String> operators = Arrays.asList("+", "-", "*", "/", ")", "(", "^");

    public ParseToPostfixNotation(String input) {
//      замена выражения вида "-X.X+..." на выражение вида "(-X.X)+..."
        Pattern pattern1 = Pattern.compile("^-\\d+([.,]\\d+)?");
        Matcher matcher1 = pattern1.matcher(input);
        while (matcher1.find()) {
            String replacement = "(" + matcher1.group() + ")";
            input = input.replaceAll(matcher1.group(), replacement);
        }
//      замена выражения вида "(-X.X+...)" на выражение вида "((-X.X)+...)"
        Pattern pattern2 = Pattern.compile("(?<group>\\(-\\d+([.,]\\d+)?+)[^)]");
        Matcher matcher2 = pattern2.matcher(input);
        if (matcher2.find()) {
            String replacement = matcher2.group("group").replaceAll("\\(", "((") + ")";
            input = input.replace(matcher2.group("group"), replacement);
        }

//      работа с отрицательными числами: Замена выражения вида "(-X.X)" на выражение вида "(0-X.X)".
        Pattern pattern3 = Pattern.compile("\\(-\\d+([.,]\\d+)?\\)");
        Matcher matcher3 = pattern3.matcher(input);
        while (matcher3.find()) {
            String replacement = matcher3.group().replaceAll("\\(", "(0");
            input = input.replaceAll("\\(" + matcher3.group() + "\\)", replacement);
        }
        this.input = input;
    }

    public String parse() {
        Deque<String> deque = new ArrayDeque<>();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String symbol = String.valueOf(input.charAt(i));
            if (operators.contains(symbol)) {
                if (deque.size() > 0 && !symbol.equals("(")) {
                    if (symbol.equals(")")) {
                        String symbolInsideBrackets = deque.pop();
                        while (!symbolInsideBrackets.equals("(")) {
                            output.append(" ").append(symbolInsideBrackets);
                            symbolInsideBrackets = deque.pop();
                        }
                    } else if (getPriority(symbol) > getPriority(deque.peek())) {
                        deque.push(symbol);
                        output.append(" ");
                    } else {
                        while (deque.size() > 0 && getPriority(symbol) <= getPriority(deque.peek())) {
                            output.append(" ").append(deque.pop());
                        }
                        deque.push(symbol);
                        output.append(" ");
                    }
                } else {
                    deque.push(symbol);
//      если строка output уже заканчивается пробелом, то дополнительный пробел добавлять не надо
                    if (output.length() >= 1 && output.charAt(output.length() - 1) != ' ') {
                        output.append(" ");
                    }
                }
            } else {
                output.append(symbol);
            }
        }
        if (deque.size() > 0) {
            while (!deque.isEmpty()) {
                output.append(" ").append(deque.pop());
            }
        }
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
