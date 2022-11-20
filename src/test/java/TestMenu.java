import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.flame.menu.Flame;
import pl.flame.menu.FlameMenu;
import pl.flame.menu.FlamePaginatedMenu;

public class TestMenu {

    private void classic(Player player) {
        FlameMenu flameMenu = Flame.classic()
                .rows(6)
                .title("Example gui")
                .disableAllInteractions()
                .build();

        flameMenu.addItem(new ItemStack(Material.DIRT), event -> player.sendMessage("dirt"));
        flameMenu.setItem(1, new ItemStack(Material.COBBLESTONE));

        flameMenu.open(player);
    }

    private void paginated(Player player) {
        FlamePaginatedMenu flamePaginatedMenu = Flame.paginated(Flame.classic()
                .title("Page: {PAGE}/{MAX_PAGE}")
                .rows(6)
                .disableAllInteractions()
                .build());

        flamePaginatedMenu.getTemplate().getFiller().fillBorder(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));

        flamePaginatedMenu.previousPage(47, new ItemStack(Material.STONE_BUTTON));
        flamePaginatedMenu.nextPage(51, new ItemStack(Material.STONE_BUTTON));

        for (int i = 0; i < 40; i++) {
            flamePaginatedMenu.addItem(new ItemStack(Material.DIRT), event -> player.sendMessage("clicked item"));
        }

        flamePaginatedMenu.open(player);
    }

}
