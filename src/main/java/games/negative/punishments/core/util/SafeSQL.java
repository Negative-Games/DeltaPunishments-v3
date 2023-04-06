package games.negative.punishments.core.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@UtilityClass
public class SafeSQL {

    private <T> T get(ResultSet result, Function<ResultSet, T> function) {
        try {
            return function.apply(result);
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(@NotNull ResultSet result, @NotNull String column) {
        return get(result, resultSet -> {
            try {
                return resultSet.getString(column);
            } catch (SQLException e) {
                return null;
            }
        });
    }
}
