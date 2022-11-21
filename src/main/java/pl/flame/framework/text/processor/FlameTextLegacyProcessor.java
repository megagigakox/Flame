package pl.flame.framework.text.processor;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import pl.flame.framework.text.formatter.impl.LegacyTextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public final class FlameTextLegacyProcessor implements UnaryOperator<Component> {

    private final LegacyTextFormatter legacyTextFormatter;

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
                .replacement((matchResult, builder1) -> this.legacyTextFormatter.parse(matchResult.group())));
    }

}