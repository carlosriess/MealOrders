package com.beberage.managers;

import com.beberage.domain.DailyMenu;
import com.beberage.domain.EMenu;
import com.beberage.domain.Menu;
import com.beberage.domain.WeeklyMenu;

import java.util.HashSet;

public class MenuFactory {

  public static Menu getNewMenu(EMenu menu) {

    if (menu == EMenu.DAILY) return new DailyMenu();
    if (menu == EMenu.WEEKLY) return new WeeklyMenu();
    return null;
  }

  public static Menu copy(Menu menu) {
    // Copy of  Daily Menu
    if (menu instanceof DailyMenu) {
      DailyMenu dailyMenu = new DailyMenu();
      DailyMenu source = (DailyMenu) menu;
      dailyMenu.setDay(source.getDay());
      dailyMenu.setWeekOfYear(source.getWeekOfYear());
      dailyMenu.setYear(source.getYear());
      dailyMenu.setName(source.getName());
      dailyMenu.setPrice(source.getPrice());
      dailyMenu.setPaid(source.isPaid());
      dailyMenu.setTax(source.getTax());
      dailyMenu.setItems(new HashSet<>(source.getItems()));
      return dailyMenu;
    }
    // Copy of Weekly Menu
    if (menu instanceof WeeklyMenu) {
      WeeklyMenu weeklyMenu = new WeeklyMenu();
      WeeklyMenu source = (WeeklyMenu) menu;

      weeklyMenu.setWeekOfYear(source.getWeekOfYear());
      weeklyMenu.setYear(source.getYear());
      weeklyMenu.setName(source.getName());
      weeklyMenu.setPrice(source.getPrice());
      weeklyMenu.setPaid(source.isPaid());
      weeklyMenu.setTax(source.getTax());
      weeklyMenu.setItems(new HashSet<>(source.getItems()));
      return weeklyMenu;
    }
    return null;
  }
}
