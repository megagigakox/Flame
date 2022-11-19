package pl.flame.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FlameMenu implements InventoryHolder {

    @Getter private final String title;
    @Getter private final int rows;
    @Getter private final boolean disableAllInteractions;
    @Getter private final FlameMenuFiller filler;
    private Inventory inventory;

    @Setter(value = AccessLevel.PRIVATE)
    private Map<Integer, Consumer<InventoryClickEvent>> eventBySlotMap;

    public FlameMenu(String title, int rows, boolean disableAllInteractions) {
        this.title = title;
        this.rows = rows;
        this.disableAllInteractions = disableAllInteractions;
        this.filler = new FlameMenuFiller(this);
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.eventBySlotMap = new HashMap<>();
    }

    public FlameMenu clone() {
        FlameMenu flameMenu = new FlameMenu(this.title, this.rows, this.disableAllInteractions);
        flameMenu.getInventory().setContents(this.inventory.getContents());
        flameMenu.setEventBySlotMap(this.eventBySlotMap);
        return flameMenu;
    }

    public void addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
    }

    public void addItem(ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        for (int slot = 0; slot < this.rows * 9 - 1; slot++) {
            if (this.inventory.getItem(slot) == null) {
                this.inventory.setItem(slot, itemStack);
                this.eventBySlotMap.put(slot, eventConsumer);
            }
        }
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int row, int column, ItemStack itemStack) {
        this.inventory.setItem((column + (row - 1) * 9) - 1, itemStack);
    }

    public void setItem(List<Integer> slots, ItemStack itemStack) {
        slots.forEach(slot -> setItem(slot, itemStack));
    }


    public void setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        this.inventory.setItem(slot, itemStack);
        this.eventBySlotMap.put(slot, eventConsumer);
    }

    public void setItem(int row, int column, ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        int slot = (column + (row - 1) * 9) - 1;
        this.inventory.setItem(slot, itemStack);
        this.eventBySlotMap.put(slot, eventConsumer);
    }

    public void setItem(List<Integer> slots, ItemStack itemStack, Consumer<InventoryClickEvent> eventConsumer) {
        slots.forEach(slot -> setItem(slot, itemStack, eventConsumer));
    }

    int getLastFreeSlot() {
        int freeSlot = -1;
        for (int i = 0; i < this.rows * 9 ; i++) {
            if (this.inventory.getItem(i) == null) {
                return i;
            }

        }

        return freeSlot;
    }

    public void updateTitle(String title) {
        Inventory updated = Bukkit.createInventory(this, this.rows * 9, title);
        updated.setContents(this.inventory.getContents());
        this.inventory = updated;
    }

    public void open(HumanEntity humanEntity) {
        humanEntity.openInventory(this.inventory);
    }

    Consumer<InventoryClickEvent> getEventBySlot(int slot) {
        return this.eventBySlotMap.getOrDefault(slot, event -> event.setCancelled(true));
    }

    void addEventToSlot(int slot, Consumer<InventoryClickEvent> eventConsumer) {
        this.eventBySlotMap.put(slot, eventConsumer);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
