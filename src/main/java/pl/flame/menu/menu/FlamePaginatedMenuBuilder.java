package pl.flame.menu.menu;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import pl.flame.menu.Flame;

public class FlamePaginatedMenuBuilder {
    private FlameMenu template;
    private Component nextPageDoesNotExist;
    private Component previousPageDoesNotExist;

    public FlamePaginatedMenuBuilder template(@NotNull FlameMenu template) {
        this.template = template;
        return this;
    }

    public FlamePaginatedMenuBuilder nextPageDoesNotExistMessage(@NotNull Component nextPageDoesNotExist) {
        this.nextPageDoesNotExist = nextPageDoesNotExist;
        return this;
    }

    public FlamePaginatedMenuBuilder previousPageDoesNotExistMessage(@NotNull Component previousPageDoesNotExist) {
        this.previousPageDoesNotExist = previousPageDoesNotExist;
        return this;
    }

    public FlamePaginatedMenuBuilder nextPageDoesNotExistMessage(@NotNull String nextPageDoesNotExist) {
        this.nextPageDoesNotExist = Flame.textFormatter().parse(nextPageDoesNotExist);
        return this;
    }

    public FlamePaginatedMenuBuilder previousPageDoesNotExistMessage(@NotNull String previousPageDoesNotExist) {
        this.previousPageDoesNotExist = Flame.textFormatter().parse(previousPageDoesNotExist);
        return this;
    }

    public FlamePaginatedMenu build() {
        return new FlamePaginatedMenu(template, nextPageDoesNotExist, previousPageDoesNotExist);
    }
}