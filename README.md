# Flame
Simple framework to creating inventories in a simple way.

#### Classic menu
```java
FlameMenu menu = Flame.classic()
    .title("Example menu")
    .rows(3)
    .disableAllInteractions()
    .build();
```

#### Paginated menu
```java
//First we need to create a template for our pages.
FlameMenu menu = Flame.classic()
    .title("Paginated menu {PAGE}/{MAX_PAGE}")
    .rows(6)
    .disableAllInteractions()
    .build();

//Now we can add border to this template menu.
menu.getFiller().fillBorder(new FlameItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));

//Create new Paginated menu
FlamePaginatedMenu paginatedMenu = Flame.paginated(menu);

//Lets set next and previous items!
paginatedMenu.nextPage(51, new ItemStack(Material.STONE_BUTTON));
paginatedMenu.previousPage(47, new ItemStack(Material.STONE_BUTTON));

//Now lets add items!
for (int i = 0; i < 35; i++) {
    paginatedMenu.addItem(new FlameItem(new ItemStack(Material.COBBLESTONE), event -> player.sendMessage("Cobblestone.")));
}

```
