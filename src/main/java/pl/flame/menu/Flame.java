package pl.flame.menu;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.flame.menu.menu.*;
import pl.flame.menu.text.FlameTextBuilder;
import pl.flame.menu.text.formatter.FlameTextFormatter;

public class Flame {

    private static FlameTextFormatter FLAME_TEXT_FORMATTER;

    public static void register(@NotNull Plugin plugin, @NotNull FlameTextFormatter flameTextFormatter) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(new FlameMenuListener(), plugin);

        FLAME_TEXT_FORMATTER = flameTextFormatter;
    }

    public static FlameMenuBuilder classic() {
        return new FlameMenuBuilder();
    }

    public static FlamePaginatedMenuBuilder paginated() {
        return new FlamePaginatedMenuBuilder();
    }

    public static FlameItemBuilder itemBuilder() {
        return new FlameItemBuilder().textFormatter(FLAME_TEXT_FORMATTER);
    }

    public static FlameTextBuilder textBuilder() {
        return new FlameTextBuilder().textFormatter(FLAME_TEXT_FORMATTER);
    }

    public static FlameTextFormatter textFormatter() {
        return FLAME_TEXT_FORMATTER;
    }


}
