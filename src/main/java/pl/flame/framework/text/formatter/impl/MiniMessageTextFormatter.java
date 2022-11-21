package pl.flame.framework.text.formatter.impl;

import com.google.common.base.Strings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import pl.flame.framework.text.processor.FlameTextLegacyProcessor;
import pl.flame.framework.text.formatter.FlameTextFormatter;

import java.util.ArrayList;
import java.util.List;

public class MiniMessageTextFormatter implements FlameTextFormatter {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new FlameTextLegacyProcessor(new LegacyTextFormatter()))
            .build();

    @Override
    public Component parse(@NotNull String text) {

        if (Strings.isNullOrEmpty(text)) {
            return Component.empty();
        }

        return MINI_MESSAGE.deserialize(text);
    }

    @Override
    public List<Component> parse(@NotNull List<String> text) {
        List<Component> list = new ArrayList<>();
        text.forEach(it -> list.add(parse(it)));
        return list;
    }
}
