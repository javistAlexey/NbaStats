package home.tutorial.nbastats.config;

import java.util.Optional;

public class Config {
    public static String get(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key)).orElse(defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(System.getenv(key));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    public static float getFloat(String key, float defaultValue) {
        try {
            return Float.parseFloat(System.getenv(key));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }
}
