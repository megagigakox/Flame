package pl.flame.framework;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import pl.flame.framework.menu.*;
import pl.flame.framework.text.FlameTextBuilder;
import pl.flame.framework.text.formatter.FlameTextFormatter;

public class Flame {

    private static FlameTextFormatter FLAME_TEXT_FORMATTER;

    public static void register(@NotNull Plugin plugin, @NotNull FlameTextFormatter flameTextFormatter) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new FlameMenuListener(), plugin);

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
