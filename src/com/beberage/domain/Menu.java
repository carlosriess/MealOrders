package com.beberage.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class representing what a Menu is. Menu has a unique identifier, a set of items for not
 * repeating the same item of the menu a name ,price and tax.The field paid=true means the menus has
 * been paid by the customer.To be paid the menu has to be ordered previously
 */
public abstract class Menu {

  protected Set<Item> items;
  protected String name;
  protected Double price;
  protected Double tax;
  protected boolean paid;
  private UUID id;

  protected Menu() {
    id = UUID.randomUUID();
  }

  protected Menu(String name) {
    super();
    items = new HashSet<>();
    this.name = name;
    price = 0D;
    paid = false;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public void addMenuItem(Item item) {
    this.items.add(item);
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Menu menu = (Menu) o;
    return id == menu.id
        && paid == menu.paid
        && items.equals(menu.items)
        && name.equals(menu.name)
        && price.equals(menu.price)
        && tax.equals(menu.tax);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, name, price, paid);
  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }

  public String getId() {
    return id.toString();
  }
}
