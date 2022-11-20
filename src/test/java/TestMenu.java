import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.flame.menu.*;

public class TestMenu {

    private void classic(Player player) {
        FlameMenu flameMenu = Flame.classic()
                .rows(6)
                .title("Example gui")
                .disableAllInteractions()
                .build();

        flameMenu.addItem(FlameItemBuilder.of(Material.DIRT)
                .buildAsFlameItem(event -> player.sendMessage("dirt")));

        flameMenu.setItem(1, FlameItemBuilder.of(new ItemStack(Material.COBBLESTONE))
                .buildAsFlameItem(event -> player.sendMessage("cobblestone")));

        flameMenu.open(player);
    }

    private void paginated(Player player) {
        FlamePaginatedMenu flamePaginatedMenu = Flame.paginated(Flame.classic()
                .title("Page: {PAGE}/{MAX_PAGE}")
                .rows(6)
                .disableAllInteractions()
                .build());

        flamePaginatedMenu.getTemplate()
                .getFiller()
                .fillBorder(FlameItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
                        .buildAsFlameItem());

        flamePaginatedMenu.previousPage(47, new ItemStack(Material.STONE_BUTTON));
        flamePaginatedMenu.nextPage(51, new ItemStack(Material.STONE_BUTTON));

        for (int i = 0; i < 40; i++) {
            flamePaginatedMenu.addItem(FlameItemBuilder.of(Material.COBBLESTONE)
                    .buildAsFlameItem(event -> player.sendMessage("cobblestone")));
        }

        flamePaginatedMenu.open(player);
    }

}
