package consolehelper;

import exceptions.ExitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readString() throws ExitException {
        String line = "";
        try {
            line = reader.readLine();
            if (line.equalsIgnoreCase("exit")) {
                throw new ExitException();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка при чтении выражения из консоли.");
            e.printStackTrace();
        }
        return line;
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }
}
