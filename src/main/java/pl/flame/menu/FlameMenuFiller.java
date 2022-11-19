package pl.flame.menu;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class FlameMenuFiller {

    private final FlameMenu menu;

    public void fill(@NotNull ItemStack itemStack) {
        int size = this.menu.getRows() * 9;
        for (int i = 0; i < size - 1; i++) {
            this.menu.setItem(i, itemStack);
        }

    }

    public void fillBorder(@NotNull ItemStack itemStack) {
        int rows = this.menu.getRows();
        if (rows <= 2) {
            return;
        }

        for (int i = 0; i < rows * 9; i++) {
            if ((i <= 8) || (i >= (rows * 9) - 8) && (i <= (rows * 9) - 2) || i % 9 == 0 || i % 9 == 8) {
                this.menu.setItem(i, itemStack);
            }

        }
    }

}
