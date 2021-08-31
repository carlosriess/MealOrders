package com.beberage.managers;

import com.beberage.domain.Item;
import com.beberage.domain.Menu;

public interface Order {

  /*Action for ordering a meal*/
  void orderMeal(Menu menu);

  /*Action for ordering various meals*/
  void orderMeals(Menu... menus);

  /*Action for ordering an item*/
  void orderItem(Item item);

  /*Action for ordering various meals*/
  void orderItems(Item... items);

  /*Action for paying the item*/
  void payItem(Item itemPaid);

  /*Action for paying the menu*/
  void payMenu(Menu menuToPaid);

  /*Bill generation for menus.Returns item a value*/
  Double generateMenuBill(Menu menu);

  /*Bill generation for single Items.Returns item a value*/
  Double generateItemBill(Item items);

  /*Bill generation for menus*/
  void generateMenuBill(Menu... menu);

  /*Bill generation for single items*/
  void generateItemBill(Item... items);
}
