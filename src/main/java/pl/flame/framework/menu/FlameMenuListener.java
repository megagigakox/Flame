package pl.flame.framework.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

public class FlameMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof FlameMenu flameMenu) {

            if (flameMenu.isDisableInteractions()) {
                event.setCancelled(true);
            }

            int slot = event.getRawSlot();
            flameMenu.getItemBySlot(slot).ifPresentOrElse(flameItem -> {

                Consumer<InventoryClickEvent> eventConsumer = flameItem.getEventConsumer();
                if (eventConsumer == null) {
                    return;
                }

                eventConsumer.accept(event);

            }, () -> event.setCancelled(true));

        }
    }
}
