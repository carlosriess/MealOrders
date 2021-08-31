package com.beberage.domain;

import java.util.Objects;

/** A DailyMenu is for each day of the year */
public class DailyMenu extends WeeklyMenu {
  private int day;

  public DailyMenu(int weekOfYear, int year, int day, String name) {
    super(weekOfYear, year, name);
    this.day = day;
  }

  public DailyMenu() {
    super();
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  @Override
  public int hashCode() {
    return Objects.hash(day);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DailyMenu menu = (DailyMenu) o;
    if (!super.equals(menu)) return false;
    if (menu.day == this.day) return true;
    return false;
  }
}
