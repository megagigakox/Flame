# Flame
Simple framework to creating inventories in a simple way.

## Register flame

With MiniMessage:
```java
//put this code inside onEnable method.
Flame.register(yourPluginInstance, new MiniMessageTextFormatter());
```

With legacy:
```java
//put this code inside onEnable method.
Flame.register(yourPluginInstance, new LegacyTextFormatter());
```

#### Classic menu
```java
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
```

#### Paginated menu
```java
//First we need to create a template for our pages.
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

        flamePaginatedMenu.previousPage(47, FlameItemBuilder.of(Material.STONE_BUTTON)
                .name("&cPrevious page")
                .buildAsFlame());
        flamePaginatedMenu.nextPage(51, FlameItemBuilder.of(Material.STONE_BUTTON)
                .name("&aNext page")
                .buildAsFlame());


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

```
