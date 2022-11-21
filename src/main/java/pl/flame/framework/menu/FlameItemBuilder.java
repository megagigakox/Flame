package pl.flame.framework.menu;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.flame.framework.text.formatter.FlameTextFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class FlameItemBuilder {

    private FlameTextFormatter textFormatter;

    private final ItemStack itemStack;
    @Getter private final ItemMeta itemMeta;

    public FlameItemBuilder() {
        this.itemStack = null;
        this.itemMeta = null;
    }

    private FlameItemBuilder(@NotNull Material material, @NotNull int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    private FlameItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static FlameItemBuilder of(@NotNull Material material) {
        return new FlameItemBuilder(material, 1);
    }

    public static FlameItemBuilder of(@NotNull Material material, int amount) {
        return new FlameItemBuilder(material, amount);
    }

    public static FlameItemBuilder of(@NotNull ItemStack item) {
        return new FlameItemBuilder(item);
    }

    public FlameItemBuilder textFormatter(@NotNull FlameTextFormatter textFormatter) {
        this.textFormatter = textFormatter;
        return this;
    }

    public void refreshMeta() {
        this.itemStack.setItemMeta(itemMeta);
    }

    public FlameItemBuilder name(@NotNull String name) {
        this.itemMeta.displayName(this.textFormatter.parse(name));
        this.refreshMeta();

        return this;
    }

    public FlameItemBuilder lore(@NotNull List<String> lore) {
        this.itemMeta.lore(this.textFormatter.parse(lore));
        this.refreshMeta();

        return this;
    }

    public FlameItemBuilder lore(@NotNull String... lore) {
        return lore(Arrays.asList(lore));
    }

    public FlameItemBuilder appendLore(@NotNull List<String> lore) {
        ItemMeta itemMeta = this.itemMeta;
        if (!itemMeta.hasLore()) {
            itemMeta.lore(this.textFormatter.parse(lore));
        } else {
            List<Component> newLore = itemMeta.lore();
            newLore.addAll(this.textFormatter.parse(lore));
            itemMeta.lore(newLore);
        }

        refreshMeta();
        return this;
    }

    public FlameItemBuilder appendLore(@NotNull String lore) {
        return this.appendLore(Collections.singletonList(lore));
    }

    public FlameItemBuilder appendLore(@NotNull String... lore) {
        return this.appendLore(Arrays.asList(lore));
    }

    public FlameItemBuilder enchantment(@NotNull Enchantment enchant, int level) {
        this.itemMeta.addEnchant(enchant, level, true);
        this.refreshMeta();

        return this;
    }

    public FlameItemBuilder flag(@NotNull ItemFlag flag) {
        this.itemMeta.addItemFlags(flag);
        this.refreshMeta();

        return this;
    }

    public FlameItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public FlameItemBuilder glow() {
        return this.glow(true);
    }

    public FlameItemBuilder glow(boolean glow) {
        if (glow) {
            this.itemMeta.addEnchant(Enchantment.LURE, 1, false);
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {

            for (Enchantment enchantment : this.itemMeta.getEnchants().keySet()) {
                this.itemMeta.removeEnchant(enchantment);
            }

        }
        refreshMeta();
        return this;
    }

    public ItemStack buildAsItemStack() {
        return this.itemStack;
    }

    public FlameItem buildAsFlame() {
        return new FlameItem(this.itemStack);
    }

    public FlameItem buildAsFlame(@NotNull Consumer<InventoryClickEvent> eventConsumer) {
        return new FlameItem(this.itemStack, eventConsumer);

    }
}