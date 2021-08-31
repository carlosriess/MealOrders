package com.beberage.managers;

import com.beberage.domain.Item;
import com.beberage.domain.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/***
 * There are two stocks, one for menus and another for single items
 *
 *
 */
public class StockManager implements Stock {
  private static List<Menu> menusStock;
  private static List<Item> itemsStock;

  public StockManager() {
    menusStock = new ArrayList<>();
    itemsStock = new ArrayList<>();
  }
  /**
   * Adds list of menus to the stock.This operation is transactional.In case any of the menus
   * already exist in the stock non of the others will be added
   */
  public static void addToMenuStock(Menu... menus) {

    // Build a supplier to open stream several times
    Supplier<Stream<Menu>> streamSupplier =
        () -> Stream.concat(Arrays.stream(menus), menusStock.stream());

    if (streamSupplier.get().distinct().count() == streamSupplier.get().count()) {
      addStock(menus);
      // Print current stock for the items
      printStockForMenus(menus);
    }
  }

  private static void addStock(Menu[] menus) {
    Arrays.stream(menus)
        .peek(
            menu ->
                System.out.println(
                    "Menu "
                        + menu.getName()
                        + " with ID="
                        + menu.getId()
                        + " has been added to stock\n"))
        .forEach(menusStock::add);
  }

  /**
   * Adds list of items to the stock.This operation is transactional.In case any of the menus
   * already exist in the stock non of the others will be added
   */
  public static void addToItemStock(Item... items) {
    Supplier<Stream<Item>> streamSupplier =
        () -> Stream.concat(Arrays.stream(items), itemsStock.stream());

    if (streamSupplier.get().distinct().count() == streamSupplier.get().count()) {
      addStock(items);
      // Print current stock for the items
      printStockForItems(items);
    }
  }

  private static void addStock(Item[] items) {
    Arrays.stream(items)
        .peek(
            item ->
                System.out.println(
                    "Item "
                        + item.getDescription()
                        + " with ID="
                        + item.getId()
                        + " has been added to stock.\n"))
        .forEach(itemsStock::add);
  }

  private static void printStockForItems(Item[] items) {
    Arrays.stream(items)
        .forEach(
            item ->
                System.out.println(
                    "Stock for item "
                        + item.getDescription()
                        + " is "
                        + itemsStock.stream()
                            .filter(
                                stockItem ->
                                    stockItem.getDescription().equals(item.getDescription()))
                            .count()
                        + "\n"));
  }

  private static void printStockForMenus(Menu[] menus) {
    Arrays.stream(menus).forEach(menu -> printStockForMenu(menu));
  }

  private static void printStockForMenu(Menu menu) {
    System.out.println(
        "Stock for Menu "
            + menu.getName()
            + " is "
            + menusStock.stream()
                .filter(stockItem -> stockItem.getName().equals(menu.getName()))
                .count()
            + "\n");
  }

  private static void printStockForItem(Item item) {
    System.out.println(
        "Stock of item "
            + item.getDescription()
            + " is "
            + itemsStock.stream()
                .filter(stockItem -> stockItem.getDescription().equals(item.getDescription()))
                .count()
            + "\n");
  }

  /** Checks if a menu exists in the stock.It looks for a menu of the same ID. */
  @Override
  public boolean isMenuAvailable(Menu menu) {
    return menusStock.stream().anyMatch(stockMenu -> stockMenu.equals(menu));
  }

  /** Checks if a single item exists in the stock.It looks for an item of the same ID. */
  @Override
  public boolean isItemAvailable(Item item) {
    return itemsStock.stream().anyMatch(stockItem -> stockItem.equals(item));
  }

  /**
   * If stock is below 3 menus of the same type it will need to be refilled so the items of the same
   * description will be refilled with 5 new more items
   */
  @Override
  public void refillMenu(Menu menu) {
    // Check the stock for the incoming menu.It has to be
    // the same type of menu with the same name
    if (menusStock.stream().filter(stockMenu -> compareMenu(menu, stockMenu)).count() < 3) {

      Menu menu1 = MenuFactory.copy(menu);
      Menu menu2 = MenuFactory.copy(menu);
      Menu menu3 = MenuFactory.copy(menu);
      Menu menu4 = MenuFactory.copy(menu);
      Menu menu5 = MenuFactory.copy(menu);

      System.out.println("Refilling 5 new menus(" + menu.getName() + ") in stock.\n");

      menusStock.addAll(List.of(menu1, menu2, menu3, menu4, menu5));
    }
  }

  private boolean compareMenu(Menu menu, Menu stockMenu) {
    return stockMenu.getClass() == menu.getClass() && stockMenu.getName().equals(menu.getName());
  }

  /**
   * If stock is below 3 items of the same type, it will need to be refilled so the items of the
   * same description will be refilled with 5 new more items
   */
  @Override
  public void refillItem(Item item) {
    if (itemsStock.stream()
            .filter(stockItem -> stockItem.getDescription().equals(item.getDescription()))
            .count()
        < 3) {
      Item item1 = item.clone();
      Item item2 = item.clone();
      Item item3 = item.clone();
      Item item4 = item.clone();
      Item item5 = item.clone();
      System.out.println("Refilling 5 new items (" + item.getDescription() + ") in stock.\n");
      itemsStock.addAll(List.of(item1, item2, item3, item4, item5));
    }
  }

  /**
   * When an item is served to the customer it is consumed from the stock so we should delete it
   * from database
   */
  @Override
  public void consume(Item item) {
    itemsStock.remove(item);
    printStockForItem(item);
  }

  /**
   * When a menu is served to the customer it is consumed from the stock so we should delete it from
   * database
   */
  @Override
  public void consume(Menu menu) {
    menusStock.remove(menu);
    printStockForMenu(menu);
  }
}
