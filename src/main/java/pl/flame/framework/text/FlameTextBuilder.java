package pl.flame.framework.text;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.flame.framework.text.formatter.FlameTextFormatter;

import java.util.*;

public final class FlameTextBuilder {

    private FlameTextFormatter textFormatter;

    private final List<String> text = new ArrayList<>();
    private final Map<String, String> placeholders = new HashMap<>();

    public FlameTextBuilder textFormatter(@NotNull FlameTextFormatter textFormatter) {
        this.textFormatter = textFormatter;
        return this;
    }

    public FlameTextBuilder text(@NotNull String message) {
        this.text.add(message);
        return this;
    }

    public FlameTextBuilder text(@NotNull List<String> message) {
        this.text.addAll(message);
        return this;
    }

    public FlameTextBuilder text(@NotNull String... message) {
        this.text.addAll(List.of(message));
        return this;
    }

    public FlameTextBuilder placeholder(@NotNull String from, @NotNull String to) {
        this.placeholders.put(from, to);
        return this;
    }

    public List<String> build() {
        if (!this.placeholders.isEmpty()) {
            List<String> replacedMessages = new ArrayList<>();

            for (String message : this.text) {

                String messageToReplace = message;
                for (Map.Entry<String, String> entry : this.placeholders.entrySet()) {
                    messageToReplace = messageToReplace.replace(entry.getKey(), entry.getValue());
                }

                replacedMessages.add(messageToReplace);

            }

            return replacedMessages;
        }

        return this.text;
    }

    public void send(@NotNull CommandSender commandSender) {
        send(Collections.singletonList(commandSender));
    }

    public void send(@NotNull List<CommandSender> receivers){
        List<String> messages = build();
        if (receivers.isEmpty() || messages.isEmpty()) {
            return;
        }

        for (CommandSender commandSender : receivers) {

            for (String message : messages) {
                commandSender.sendMessage(this.textFormatter.parse(message));
            }

        }
    }
}
