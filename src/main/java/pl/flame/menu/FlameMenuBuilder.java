package pl.flame.menu;

import net.kyori.adventure.text.Component;

public final class FlameMenuBuilder {
    private String title;
    private int rows;
    private boolean disableAllInteractions = false;

    public FlameMenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public FlameMenuBuilder rows(int rows) {
        this.rows = rows;
        return this;
    }

    public FlameMenuBuilder disableAllInteractions() {
        this.disableAllInteractions = true;
        return this;
    }

    public FlameMenu build() {
        return new FlameMenu(title, rows, disableAllInteractions);
    }
}