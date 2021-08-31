package com.beberage.domain;

import java.util.Objects;

/***
 * A Weekly menu is active for a certain week of the year
 *
 */

public class WeeklyMenu extends Menu {
  protected int weekOfYear;
  protected int year;

  public WeeklyMenu(int weekOfYear, int year, String name) {
    super(name);
    this.weekOfYear = weekOfYear;
    this.year = year;
  }

  public WeeklyMenu() {
    super();
  }

  public int getWeekOfYear() {
    return weekOfYear;
  }

  public void setWeekOfYear(int weekOfYear) {
    this.weekOfYear = weekOfYear;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(weekOfYear, year);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    WeeklyMenu menu = (WeeklyMenu) obj;
    if (!super.equals(obj)) return false;
    if (weekOfYear == menu.weekOfYear && year == menu.year) return true;
    return false;
  }
}
