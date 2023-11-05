package xyz.johanmans10.simplecommandspy.utilities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.johanmans10.simplecommandspy.SimpleCommandSpy;
import xyz.johanmans10.simplecommandspy.constants.Paths;

import java.util.UUID;

public class PlaceholderUtils {

    private String value;
    private String prefix;
    private boolean color;

    @Contract(pure = true)
    private PlaceholderUtils(String value) {
        this.value = value;
        this.prefix = "";
        this.color = false;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static PlaceholderUtils of(@NotNull String original) {
        return new PlaceholderUtils(original);
    }

    public PlaceholderUtils prefix() {
        return prefix(false);
    }

    public PlaceholderUtils shortPrefix() {
        return prefix(true);
    }

    private PlaceholderUtils prefix(boolean shortPrefix) {
        prefix = SimpleCommandSpy
                .getInstance()
                .getConfiguration()
                .getConfig()
                .getString(
                        shortPrefix
                                ? Paths.CONFIG_FORMAT_PREFIX_SHORT
                                : Paths.CONFIG_FORMAT_PREFIX,
                        "");
        return this;
    }

    public PlaceholderUtils color() {
        return color(true);
    }

    public PlaceholderUtils noColor() {
        return color(false);
    }

    private PlaceholderUtils color(boolean color) {
        this.color = color;
        return this;
    }

    public PlaceholderUtils name(@NotNull String name) {
        value = value.replace("{NAME}", name);
        return this;
    }

    public PlaceholderUtils uuid(@NotNull UUID uuid) {
        value = value.replace("{UUID}", uuid.toString());
        return this;
    }

    public PlaceholderUtils command(@NotNull String command) {
        value = value.replace("{COMMAND}", command);
        return this;
    }

    @Override
    public String toString() {
        value = prefix + value;
        value = color ? ColorUtils.format(value) : value;
        return value;
    }
}
