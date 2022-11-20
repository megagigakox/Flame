package pl.flame.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class FlameItem {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> eventConsumer;

    public FlameItem(@NotNull ItemStack itemStack) {
        this(itemStack, null);
    }

}
