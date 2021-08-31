package com.beberage;

import com.beberage.domain.DailyMenu;
import com.beberage.domain.EMenu;
import com.beberage.domain.Item;
import com.beberage.domain.WeeklyMenu;
import com.beberage.managers.MenuFactory;
import com.beberage.managers.OrderManager;
import com.beberage.managers.StockManager;

import java.util.Set;

public class Main {

  public static void main(String[] args) {
    OrderManager orderManager = new OrderManager();

    // Create stock for single items

    Item cocacola = getNewItem("COCA-COLA", 2D, 21D);
    Item cocacola1 = cocacola.clone();
    Item agua = getNewItem("AGUA", 1D, 10D);
    Item vino = getNewItem("VINO TINTO 0,5L", 3D, 10D);
    Item greenSalad = getNewItem("ENSALADA VERDE", 3D, 21D);
    Item pollo = getNewItem("POLLO CON ALMENDRAS", 4D, 21D);
    Item pescado = getNewItem("DORADA A LA BRASA", 5D, 21D);
    Item flanConNata = getNewItem("FLAN CON NATA", 3D, 21D);
    Item manzana = getNewItem("MANZANA", 3D, 21D);
    Item manzana1 = manzana.clone();

    // Add cloned items to stock
    StockManager.addToItemStock(cocacola1, manzana1);
    // Try to add them again but nothing is added as they already exist in the stock
    StockManager.addToItemStock(cocacola1, manzana1);

    // Order items in stock separatedly
    orderManager.orderItem(cocacola1);
    orderManager.orderItem(cocacola);
    orderManager.orderItem(manzana);
    orderManager.orderItems(agua, greenSalad);
    orderManager.orderItems(pollo, pescado, flanConNata);
    orderManager.orderItem(flanConNata);

    // pay items
    orderManager.payItem(cocacola);
    orderManager.payItem(manzana);
    orderManager.payItem(manzana1);
    orderManager.payItem(cocacola1);
    orderManager.payItem(flanConNata);

    // Create stock for two daily menus and one weekly menu
    WeeklyMenu menu_vegetariano_post_navidad =
        getWeekMenu(1, 2022, "MENU VEGETARIANO POST NAVIDAD", 20D, 10D, greenSalad, agua, manzana);
    DailyMenu menu_martes =
        getDailyMenu(4, 2022, 1, "MENU MARTES", 12D, 10D, greenSalad, pescado, manzana);
    DailyMenu menu_lunes =
        getDailyMenu(3, 2022, 1, "MENU LUNES", 12D, 10D, greenSalad, pollo, vino, flanConNata);

    // order meals
    orderManager.orderMeal(menu_vegetariano_post_navidad);
    orderManager.orderMeal(menu_lunes);
    orderManager.orderMeal(menu_martes);
    orderManager.orderMeals(
        MenuFactory.copy(menu_martes),
        MenuFactory.copy(menu_martes),
        MenuFactory.copy(menu_martes),
        MenuFactory.copy(menu_martes),
        MenuFactory.copy(menu_martes));

    // pay meals
    orderManager.payMenu(menu_vegetariano_post_navidad);
    orderManager.payMenu(menu_martes);

    // Generate bills for paid items
    // Some of them are not already paid so no bill should be generated
    orderManager.generateItemBill(
        cocacola, cocacola1, flanConNata, agua, vino, greenSalad, pollo, pescado);
    // Generate bills for paid menus
    // Some of them are not already paid so no bill should be generated
    orderManager.generateMenuBill(menu_vegetariano_post_navidad, menu_martes, menu_lunes);
  }

  // Creates new items and add them to stock
  private static Item getNewItem(String description, Double price, Double tax) {
    Item item = new Item(description, price, tax);
    // Add new item to stock
    StockManager.addToItemStock(item);
    return item;
  }

  // Creates new Daily Menus and add them to stock
  private static DailyMenu getDailyMenu(
      int dayOfMonth,
      int year,
      int weekOfYear,
      String name,
      Double price,
      Double tax,
      Item... menuItems) {

    DailyMenu dailyMenu = (DailyMenu) MenuFactory.getNewMenu(EMenu.DAILY);
    dailyMenu.setName(name);
    dailyMenu.setYear(year);
    dailyMenu.setDay(dayOfMonth);
    dailyMenu.setPrice(price);
    dailyMenu.setTax(tax);
    dailyMenu.setWeekOfYear(weekOfYear);

    dailyMenu.setItems(Set.of(menuItems));
    // Add new menu to stock
    StockManager.addToMenuStock(dailyMenu);
    return dailyMenu;
  }

  // Creates new Weekly Menus and add them to stock
  private static WeeklyMenu getWeekMenu(
      int weekOfYear, int year, String name, Double price, Double tax, Item... menuItems) {

    WeeklyMenu newMenu = (WeeklyMenu) MenuFactory.getNewMenu(EMenu.WEEKLY);
    newMenu.setName(name);
    newMenu.setPrice(price);
    newMenu.setTax(tax);
    newMenu.setYear(year);
    newMenu.setWeekOfYear(weekOfYear);

    newMenu.setItems(Set.of(menuItems));
    return newMenu;
  }
}
