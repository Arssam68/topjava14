package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 11, 30), "Завтрак2", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 45), "Завтрак1", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
        Map<LocalDate, Integer> sumCalForDay = new HashMap<>();

        Collections.sort(mealList, Comparator.comparing(UserMeal::getDateTime));

        mealList.forEach((meal) -> sumCalForDay.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        mealList.forEach((meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        sumCalForDay.get(meal.getDate()) > caloriesPerDay));
            }
        }));

        mealWithExceeds.forEach(System.out::println);

        return mealWithExceeds;
    }
}
