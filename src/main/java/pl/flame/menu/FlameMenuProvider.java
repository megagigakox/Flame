package pl.flame.menu;

import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class FlameMenuProvider {

    public static FlameMenuProvider newInstance(Plugin plugin) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(new FlameMenuListener(), plugin);

        return new FlameMenuProvider();
    }

    public FlameMenuBuilder classic() {
        return new FlameMenuBuilder();
    }

    public FlamePaginatedMenu paginated(FlameMenu flameMenu, Consumer<FlameMenu> flameMenuConsumer) {
        flameMenuConsumer.accept(flameMenu);
        return new FlamePaginatedMenu(flameMenu);
    }

}
