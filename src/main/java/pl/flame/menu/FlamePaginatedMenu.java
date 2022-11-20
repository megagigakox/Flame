package pl.flame.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class FlamePaginatedMenu {

    private final FlameMenu templateMenu;

    @Nullable
    private FlameMenu currentPage;

    private final Map<Integer, FlameMenu> pagesByNumbers;
    private final Map<FlameMenu, Integer> numbersByPages;
    private final Map<UUID, Integer> pageViewers;

    public FlamePaginatedMenu(@NotNull FlameMenu templateMenu) {
        this.templateMenu = templateMenu;
        this.pagesByNumbers = new HashMap<>();
        this.numbersByPages = new HashMap<>();
        this.pageViewers = new HashMap<>();
        addPage(this.templateMenu.clone(), 0);
    }

    void addPage(@NotNull FlameMenu flameMenu, int page) {
        this.pagesByNumbers.put(page, flameMenu);
        this.numbersByPages.put(flameMenu, page);
    }

    public void nextPage(int slot) {
        this.templateMenu.addEventToSlot(slot, event -> {
            event.setCancelled(true);

            HumanEntity humanEntity = event.getWhoClicked();
            int nextPage = this.pageViewers.getOrDefault(humanEntity.getUniqueId(), 0) + 1;

            if (this.pagesByNumbers.size() <= nextPage) {
                humanEntity.sendMessage("&cNastępna strona nie istnieje!");
                return;
            }

            this.open(humanEntity, nextPage);
        });
    }

    public void previousPage(int slot) {
        this.templateMenu.addEventToSlot(slot, event -> {
            event.setCancelled(true);

            HumanEntity humanEntity = event.getWhoClicked();
            int previousPage = this.pageViewers.getOrDefault(humanEntity.getUniqueId(), 0) - 1;

            if (0 > previousPage) {
                humanEntity.sendMessage("&cPoprzednia strona nie istnieje!");
                return;
            }

            this.open(humanEntity, previousPage);
        });
    }

    @Nullable
    private FlameMenu getLeastFilled() {

        for (int i = 0; i < this.pagesByNumbers.size(); i++) {
            FlameMenu flameMenu = this.pagesByNumbers.get(i);
            if (flameMenu != null) {
                return flameMenu;
            }
        }

        return null;
    }

    public void refreshPagesTitle() {
        this.pagesByNumbers.values().forEach(flameMenu -> flameMenu.updateTitle(flameMenu.getTitle()
                .replace("{PAGE}", String.valueOf(this.numbersByPages.get(flameMenu) + 1))
                .replace("{MAX_PAGE}", String.valueOf(this.pagesByNumbers.size()))));
    }

    public void addItem(@NotNull ItemStack itemStack) {
        addItem(itemStack, event -> {});
    }

    public void addItem(@NotNull ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> eventConsumer) {
        FlameMenu flameMenu;

        if (this.currentPage == null) {
            flameMenu = getLeastFilled();
            this.currentPage = flameMenu;
        }
        else {
            flameMenu = this.currentPage;
        }

        if (flameMenu.getLastFreeSlot() == -1) {
            FlameMenu nextPage = this.templateMenu.clone();
            flameMenu = nextPage;
            this.currentPage = flameMenu;
            addPage(nextPage, this.pagesByNumbers.size());
        }

        flameMenu.setItem(flameMenu.getLastFreeSlot(), itemStack, eventConsumer);
    }

    public void open(@NotNull HumanEntity humanEntity, int page) {
        FlameMenu menu = this.pagesByNumbers.get(page);
        if (menu == null) {
            open(humanEntity);
            return;
        }

        refreshPagesTitle();
        this.pageViewers.put(humanEntity.getUniqueId(), page);
        menu.open(humanEntity);
    }

    public void open(@NotNull HumanEntity humanEntity) {
        FlameMenu firstPage = this.pagesByNumbers.get(0);
        this.pageViewers.put(humanEntity.getUniqueId(), 0);
        refreshPagesTitle();
        firstPage.open(humanEntity);
    }

}
