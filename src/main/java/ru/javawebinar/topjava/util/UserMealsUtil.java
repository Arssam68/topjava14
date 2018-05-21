package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак3", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 11, 30), "Завтрак2", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 45), "Завтрак1", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510)
        );

        getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(System.out::println);

        System.out.println();

        getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(System.out::println);

    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
        Map<LocalDate, Integer> sumCalForDay = new HashMap<>();

        Collections.sort(mealList, Comparator.comparing(UserMeal::getDateTime));

        mealList.forEach((meal) -> sumCalForDay.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        mealList.forEach((meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealWithExceeds.add(createUserMealWithExceed(meal, sumCalForDay.get(meal.getDate()) > caloriesPerDay));
            }
        }));

        return mealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCalForDay = mealList.stream()
                .collect(Collectors.groupingBy((UserMeal::getDate), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .map(meal -> createUserMealWithExceed(meal,sumCalForDay.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMealWithExceed createUserMealWithExceed(UserMeal meal, boolean exceed) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }
}
