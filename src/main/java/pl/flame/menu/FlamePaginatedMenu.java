package pl.flame.menu;

import lombok.Getter;
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

    @Getter private final FlameMenu template;

    @Nullable
    private FlameMenu currentPage;

    private final Map<Integer, FlameMenu> menusByPages;
    private final Map<FlameMenu, Integer> pagesByMenus;
    private final Map<UUID, Integer> pageViewers;

    public FlamePaginatedMenu(@NotNull FlameMenu template) {
        this.template = template;
        this.menusByPages = new HashMap<>();
        this.pagesByMenus = new HashMap<>();
        this.pageViewers = new HashMap<>();
    }

    void addPage(@NotNull FlameMenu flameMenu, int page) {
        this.menusByPages.put(page, flameMenu);
        this.pagesByMenus.put(flameMenu, page);
    }

    public void nextPage(int slot, ItemStack itemStack) {
        this.template.setItem(slot, itemStack, event -> {
            event.setCancelled(true);

            HumanEntity humanEntity = event.getWhoClicked();
            int nextPage = this.pageViewers.getOrDefault(humanEntity.getUniqueId(), 0) + 1;

            if (this.menusByPages.size() <= nextPage) {
                humanEntity.sendMessage("&cNastępna strona nie istnieje!");
                return;
            }

            this.open(humanEntity, nextPage);
        });
    }

    public void previousPage(int slot, ItemStack itemStack) {
        this.template.setItem(slot, itemStack, event -> {
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

        for (int i = 0; i < this.menusByPages.size(); i++) {
            FlameMenu flameMenu = this.menusByPages.get(i);
            if (flameMenu != null) {
                return flameMenu;
            }
        }

        return null;
    }

    public void refreshPagesTitle() {
        this.menusByPages.values().forEach(flameMenu -> flameMenu.updateTitle(flameMenu.getTitle()
                .replace("{PAGE}", String.valueOf(this.pagesByMenus.get(flameMenu) + 1))
                .replace("{MAX_PAGE}", String.valueOf(this.menusByPages.size()))));
    }

    public void addItem(@NotNull ItemStack itemStack) {
        addItem(itemStack, event -> {});
    }

    public void addItem(@NotNull ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> eventConsumer) {
        if (this.menusByPages.isEmpty()) {
            addPage(this.template.clone(), 0);
        }

        FlameMenu flameMenu;
        if (this.currentPage == null) {
            flameMenu = getLeastFilled();
            this.currentPage = flameMenu;
        }
        else {
            flameMenu = this.currentPage;
        }

        if (flameMenu.getLastFreeSlot() == -1) {
            FlameMenu nextPage = this.template.clone();
            flameMenu = nextPage;
            this.currentPage = flameMenu;
            addPage(nextPage, this.menusByPages.size());
        }

        flameMenu.setItem(flameMenu.getLastFreeSlot(), itemStack, eventConsumer);
    }

    public void open(@NotNull HumanEntity humanEntity, int page) {
        FlameMenu menu = this.menusByPages.get(page);
        if (menu == null) {
            open(humanEntity);
            return;
        }

        refreshPagesTitle();
        this.pageViewers.put(humanEntity.getUniqueId(), page);
        menu.open(humanEntity);
    }

    public void open(@NotNull HumanEntity humanEntity) {
        FlameMenu firstPage = this.menusByPages.get(0);
        this.pageViewers.put(humanEntity.getUniqueId(), 0);
        refreshPagesTitle();
        firstPage.open(humanEntity);
    }

}
