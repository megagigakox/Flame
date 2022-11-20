package pl.flame.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

final class FlameText {

    private FlameText() {

    }

    public static final TextComponent RESET = Component.text()
            .decoration(TextDecoration.ITALIC, false)
            .build();

    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .build();

    public static Component parse(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return RESET.append(LEGACY_COMPONENT_SERIALIZER.deserialize(text));
    }

    public static List<Component> parse(List<String> text) {
        List<Component> list = new ArrayList<>();
        text.forEach(it -> list.add(parse(it)));
        return list;
    }

}
