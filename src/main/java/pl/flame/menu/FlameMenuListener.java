package pl.flame.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

public class FlameMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof FlameMenu bukkitMenu) {

            if (bukkitMenu.isDisableAllInteractions()) {
                event.setCancelled(true);
            }

            int slot = event.getRawSlot();
            if (event.getCurrentItem() == null) {
                return;
            }

            Consumer<InventoryClickEvent> eventConsumer = bukkitMenu.getEventBySlot(slot);
            eventConsumer.accept(event);

        }

    }

}
