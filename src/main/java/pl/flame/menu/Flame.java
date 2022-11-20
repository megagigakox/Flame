package pl.flame.menu;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Flame {

    public static void register(@NotNull Plugin plugin) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(new FlameMenuListener(), plugin);
    }

    public static FlameMenuBuilder classic() {
        return new FlameMenuBuilder();
    }

    public static FlamePaginatedMenu paginated(@NotNull FlameMenu flameMenu, @NotNull Consumer<FlameMenu> flameMenuConsumer) {
        flameMenuConsumer.accept(flameMenu);
        return new FlamePaginatedMenu(flameMenu);
    }

}
