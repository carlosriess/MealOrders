package com.beberage.domain;

import java.util.Objects;
import java.util.UUID;

public class Item {

  private UUID id;
  private Double price;
  private String description;
  private Double tax;
  private boolean paid;

  public Item() {
    id = UUID.randomUUID();
  }

  public Item(String description, Double price, Double tax) {
    this();
    this.price = price;
    this.description = description;
    this.tax = tax;
  }

  // Getters and setters
  public String getId() {
    return id.toString();
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  @Override
  public Item clone() {
    Item item = new Item();
    item.description = this.description;
    item.tax = this.tax;
    item.price = this.price;
    return item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return id == item.id && description.equals(item.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, price, description, tax, paid);
  }
}
