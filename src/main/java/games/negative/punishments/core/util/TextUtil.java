package games.negative.punishments.core.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a utility for general text.
 */
@UtilityClass
public class TextUtil {

    /**
     * Checks if a string is equal (ignoring case sensitivity) to any of the given strings.
     * @param input the string to check
     * @param against the strings to check against
     * @return true if the string is equal to any of the given strings, false otherwise
     */
    public boolean multiEqualsIgnoreCase(@NotNull String input, @NotNull String... against) {
        for (String value : against) {
            if (input.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
