package pl.flame.menu;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FlameMenuProvider {

    public static FlameMenuProvider newInstance(@NotNull Plugin plugin) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(new FlameMenuListener(), plugin);

        return new FlameMenuProvider();
    }

    public FlameMenuBuilder classic() {
        return new FlameMenuBuilder();
    }

    public FlamePaginatedMenu paginated(@NotNull FlameMenu flameMenu, @NotNull Consumer<FlameMenu> flameMenuConsumer) {
        flameMenuConsumer.accept(flameMenu);
        return new FlamePaginatedMenu(flameMenu);
    }

}
