package games.negative.punishments.core.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

/**
 * Represents a class who holds utility methods for arrays
 */
@UtilityClass
public class UtilArray {

    public <T> T safeGet(T[] array, int index) {
        if (array.length > index) {
            return array[index];
        }
        return null;
    }

    public <T> T safeGet(T[] array, int index, T def) {
        if (array.length > index) {
            return array[index];
        }
        return def;
    }

    public <T> T safeGet(List<T> list, int index, T def) {
        if (list.size() > index) {
            return list.get(index);
        }
        return def;
    }

    public <T> T safeGet(List<T> list, int index, boolean defCollectLast) {
        if (list.size() > index) {
            return list.get(index);
        }
        return list.get((defCollectLast ? (list.size() - 1) : 0));
    }

}
