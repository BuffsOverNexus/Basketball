package com.buffsovernexus.console.logging;

import com.buffsovernexus.GameSettings;

public class ConsoleLog {

    public static void inline(String message) {
        System.out.print(message);
    }

    public static void message(String message) {
        System.out.println(message);
    }

    public static void event(String message) {
        System.out.println( String.format("[EVENT] %s", message) );
    }

    public static void error(String message) {
        System.out.println( String.format("[ERROR] %s", message) );
    }

    public static void format(String format, Object ... args) {
        try {
            System.out.println(String.format(format, args));
            Thread.sleep(GameSettings.TOTAL_SECONDS_PER_MESSAGE * 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void formatEvent(String format, Object ... args) {
        format("[%s] " + format, args);
    }

    public static void formatError(String format, Object ... args) {
        format("[ERROR] " + format, args);
    }

    public static void emptyLine() { System.out.println(""); }
}
