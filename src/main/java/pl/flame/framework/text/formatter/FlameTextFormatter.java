package pl.flame.framework.text.formatter;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FlameTextFormatter {

    Component parse(@NotNull String text);

    List<Component> parse(@NotNull List<String> text);

}
