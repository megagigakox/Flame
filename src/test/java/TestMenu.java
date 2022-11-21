import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.flame.framework.*;
import pl.flame.framework.menu.FlameItem;
import pl.flame.framework.menu.FlameItemBuilder;
import pl.flame.framework.menu.FlameMenu;
import pl.flame.framework.menu.FlamePaginatedMenu;

import java.util.List;

public class TestMenu {

    private void classic(Player player) {
        FlameMenu flameMenu = Flame.classic()
                .rows(6)
                .title("&cExample Menu")
                .disableInteractions()
                .build();

        flameMenu.addItem(new FlameItem(new ItemStack(Material.DIRT)));

        //with flamebuilder
        flameMenu.addItem(FlameItemBuilder.of(Material.DIRT)
                .buildAsFlame(event -> player.sendMessage("dirt")));

        flameMenu.setItem(1, FlameItemBuilder.of(new ItemStack(Material.COBBLESTONE))
                .buildAsFlame(event -> player.sendMessage("Click")));

        flameMenu.setItem(1, 5, FlameItemBuilder.of(new ItemStack(Material.COBBLESTONE))
                .buildAsFlame(event -> player.sendMessage("Click")));

        flameMenu.open(player);
    }

    private void paginated(Player player) {
        FlamePaginatedMenu flamePaginatedMenu = Flame.paginated()
                .template(Flame.classic()
                    .title("&cPage: &7{PAGE}&8/&7{MAX_PAGE}")
                    .rows(6)
                    .disableInteractions()
                    .build())
                .nextPageDoesNotExistMessage("&cNext page doesn't exist!")
                .previousPageDoesNotExistMessage("&cPrevious page doesn't exist!")
                .build();

        flamePaginatedMenu.getTemplate()
                .getFiller()
                .fillBorder(FlameItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
                        .buildAsFlame());

        flamePaginatedMenu.previousPage(47, new ItemStack(Material.STONE_BUTTON));
        flamePaginatedMenu.nextPage(51, new ItemStack(Material.STONE_BUTTON));


        List<String> formattedLore = Flame.textBuilder()
                .text(
                        "&cAmazing text builder!",
                        "&aYour placeholder here: &b{EXAMPLE_PLACEHOLDER}"
                )
                .placeholder("{EXAMPLE_PLACEHOLDER}", "Nice placeholder!")
                .build();

        for (int i = 0; i < 40; i++) {
            flamePaginatedMenu.addItem(FlameItemBuilder.of(Material.COBBLESTONE)
                    .lore(formattedLore)
                    .buildAsFlame(event -> player.sendMessage(Flame.textFormatter().parse("&7Cobblestone"))));
        }

        flamePaginatedMenu.open(player);
    }

}
