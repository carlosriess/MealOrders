package com.beberage.managers;

import com.beberage.domain.Item;
import com.beberage.domain.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for managing orders at the restaurant like
 *
 * <p>ordering meals, ordering single items like water,wine etc , pay meals and items and generate
 * bill for the already payed menus or items.Payment and billing is idempotent meaning if menu or
 * item not found in the orders databse or is not payed nothing happens..
 */
public class OrderManager implements Order {

  // orders database
  private List<Menu> orderedMenus;
  // single intems database
  private List<Item> orderedItems;
  // handler for stock manager
  private Stock stock;

  public OrderManager() {
    orderedMenus = new ArrayList<>();
    orderedItems = new ArrayList<>();
    stock = new StockManager();
  }

  /**
   * When ordering a menu first we check if there's available stock otherwise refill with 5 new
   * menus of that type.Aftwerwards the menu is consumed from the stock and added to the orders
   * database
   */
  @Override
  public void orderMeal(Menu menu) {
    if (orderedMenus.contains(menu)) {
      System.out.println("Menu:" + menu.getName() + "is already ordered\n");
      return;
    }

    if (!stock.isMenuAvailable(menu)) StockManager.addToMenuStock(menu);

    orderedMenus.add(menu);
    System.out.println("Ordered menu: " + menu.getName() + "\n");
    stock.consume(menu);

    stock.refillMenu(menu);
  }

  @Override
  public void orderMeals(Menu... menus) {
    Arrays.stream(menus).forEach(this::orderMeal);
  }

  /**
   * When ordering a single item first we check if there's available stock otherwise add it to stock
   * refill with 5 new items of that type.Afterwards the item is consumed from the stock and added
   * to the orders database
   */
  @Override
  public void orderItem(Item item) {
    if (orderedItems.contains(item)) {
      System.out.println("item:" + item.getDescription() + "is already ordered\n");
      return;
    }

    if (!stock.isItemAvailable(item)) StockManager.addToItemStock(item);

    orderedItems.add(item);
    System.out.println("Ordered item:" + item.getDescription() + "\n");
    stock.consume(item);

    stock.refillItem(item);
  }

  @Override
  public void orderItems(Item... items) {

    Arrays.stream(items).forEach(this::orderItem);
  }

  /**
   * When paying an item we check that has been previously ordered otherwise we inform that the item
   * cannot be payed as it has not been ordered yet.If the item is ordered we mark it as payed and
   * inform the user
   */
  @Override
  public void payItem(Item itemToPay) {

    orderedItems.stream()
        .filter(item -> item.equals(itemToPay))
        .findFirst()
        .ifPresentOrElse(
            item -> {
              item.setPaid(true);
              System.out.println(
                  "Item "
                      + item.getDescription()
                      + " with price "
                      + item.getPrice()
                      + "€ is paid\n");
            },
            () ->
                System.out.println(
                    "Item "
                        + itemToPay.getDescription()
                        + " cannot be paid as it has not been ordered yet.\n"));
  }
  /**
   * When paying an menu we check that has been previously ordered otherwise we inform that the menu
   * cannot be payed as it has not been ordered yet.If the menu is ordered we mark it as payed and
   * inform the user
   */
  @Override
  public void payMenu(Menu menuToPay) {
    orderedMenus.stream()
        .filter(menu -> menu.equals(menuToPay))
        .findFirst()
        .ifPresentOrElse(
            menu -> {
              menu.setPaid(true);
              System.out.println(
                  "Menu " + menu.getName() + " with price " + menu.getPrice() + "€ is paid\n");
            },
            () ->
                System.out.println(
                    "Menu "
                        + menuToPay.getName()
                        + " cannot be paid as it has not been ordered yet.\n"));
  }

  /** Given an ordered menu if it's payed we generate the bill and return the price */
  @Override
  public Double generateMenuBill(Menu menu) {
    return orderedMenus.stream()
        .filter(stockMenu -> compareMenu(menu, stockMenu) && menu.isPaid())
        .peek(
            stockMenu ->
                System.out.println(
                    "Generating bill for menu: "
                        + menu.getName()
                        + " with price "
                        + menu.getPrice()
                        + "€\n"))
        .map(Menu::getPrice)
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  private boolean compareMenu(Menu menu, Menu stockMenu) {
    return stockMenu.getClass() == menu.getClass() && stockMenu.equals(menu);
  }

  /** Given an ordered item if it's payed we generate the bill and return the price */
  @Override
  public Double generateItemBill(Item item) {

    return orderedItems.stream()
        .filter(stockItem -> stockItem.equals(item) && item.isPaid())
        .peek(
            stockMenu ->
                System.out.println(
                    "Generating bill for Item: "
                        + stockMenu.getDescription()
                        + " with price "
                        + stockMenu.getPrice()
                        + "€\n"))
        .map(Item::getPrice)
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  /** Given a list of items a total bill is generated with the sum of all items */
  @Override
  public void generateMenuBill(Menu... menus) {
    double sum =
        Arrays.stream(menus).map(this::generateMenuBill).mapToDouble(Double::doubleValue).sum();
    System.out.println("Total menus: " + sum + "€\n");
  }

  /** Given a list of items a total bill is generated with the sum of all items */
  @Override
  public void generateItemBill(Item... items) {
    double sum =
        Arrays.stream(items).map(this::generateItemBill).mapToDouble(Double::doubleValue).sum();
    System.out.println("Total items: " + sum + "€\n");
  }
}
