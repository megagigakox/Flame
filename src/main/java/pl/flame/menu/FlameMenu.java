package pl.flame.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FlameMenu implements InventoryHolder {

    @Getter private final String title;
    @Getter private final int rows;
    @Getter private final boolean disableAllInteractions;
    @Getter private final FlameMenuFiller filler;
    private Inventory inventory;

    @Setter
    @Getter
    private Map<Integer, FlameItem> menuItems;

    public FlameMenu(@NotNull String title, int rows, boolean disableAllInteractions) {
        this.title = title;
        this.rows = rows;
        this.disableAllInteractions = disableAllInteractions;
        this.filler = new FlameMenuFiller(this);
        this.inventory = Bukkit.createInventory(this, this.getRows() * 9, title);
        this.menuItems = new HashMap<>();
    }

    public FlameMenu clone() {
        FlameMenu flameMenu = new FlameMenu(this.title, this.rows, this.disableAllInteractions);
        this.menuItems.forEach((key, value) -> flameMenu.getMenuItems().put(key, value));
        return flameMenu;
    }

    public void addItem(@NotNull FlameItem flameItem) {
        int maxIndex = this.getRows() * 9;
        for (int i = 0; i < maxIndex; i++) {
            this.menuItems.putIfAbsent(i, flameItem);
        }
    }

    public void setItem(int slot, @NotNull FlameItem flameItem) {
        if (slot < 0) {
            return;
        }

        this.menuItems.put(slot, flameItem);
    }

    public void setItem(int row, int column, @NotNull FlameItem flameItem) {
        int slot = (column + (row - 1) * 9) - 1;
        if (slot < 0) {
            return;
        }
        this.menuItems.put(slot, flameItem);
    }

    public void setItem(@NotNull List<Integer> slots, @NotNull FlameItem flameItem) {
        slots.forEach(slot -> setItem(slot, flameItem));
    }

    public void crowdInventory() {
        for (Map.Entry<Integer, FlameItem> entry : this.menuItems.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }

    int getLastFreeSlot() {
        int freeSlot = -1;
        for (int i = 0; i < this.getRows() * 9; i++) {
            if (this.menuItems.get(i) == null) {
                return i;
            }
        }

        return freeSlot;
    }

    public void update() {
        this.inventory.clear();
        crowdInventory();
        for (HumanEntity viewer : this.inventory.getViewers()) {
            ((Player) viewer).updateInventory();
        }
    }

    public void updateTitle(@NotNull String title) {
        Inventory updated = Bukkit.createInventory(this, this.getRows() * 9, title);
        updated.setContents(this.inventory.getContents());
        this.inventory = updated;
    }

    public void open(@NotNull HumanEntity humanEntity) {
        crowdInventory();
        humanEntity.openInventory(this.inventory);
    }

    Optional<FlameItem> getItemBySlot(int slot) {
        return Optional.ofNullable(this.menuItems.get(slot));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
