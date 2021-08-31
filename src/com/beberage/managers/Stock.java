package com.beberage.managers;

import com.beberage.domain.Item;
import com.beberage.domain.Menu;

public interface Stock {

  /*check stock for menus and single item like drinks,desserts etc*/
  boolean isMenuAvailable(Menu menu);

  /*check stock for menus and single item like drinks,desserts etc*/
  boolean isItemAvailable(Item item);

  /*refill stock for menus and single item like drinks,desserts etc*/
  void refillMenu(Menu menu);

  /*refill stock for single item like drinks,desserts etc..*/
  void refillItem(Item item);

  /* When an order is placed the item  must be consumed from stock*/
  void consume(Item item);

  /* When an order is placed the item  must be consumed from stock*/
  void consume(Menu menu);
}
