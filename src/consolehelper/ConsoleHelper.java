package consolehelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readString() {
        ConsoleHelper.writeMessage("Пожалуйста, введите выражение: ");
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }
}
