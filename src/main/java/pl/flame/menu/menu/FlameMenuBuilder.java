package pl.flame.menu.menu;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import pl.flame.menu.Flame;


public final class FlameMenuBuilder {
    private Component title;
    private int rows;
    private boolean disableInteractions = false;

    public FlameMenuBuilder title(@NotNull Component title) {
        this.title = title;
        return this;
    }

    public FlameMenuBuilder title(@NotNull String title) {
        this.title = Flame.textFormatter().parse(title);
        return this;
    }

    public FlameMenuBuilder rows(int rows) {
        this.rows = rows;
        return this;
    }

    public FlameMenuBuilder disableInteractions() {
        this.disableInteractions = true;
        return this;
    }

    public FlameMenu build() {
        return new FlameMenu(title, rows, disableInteractions);
    }
}