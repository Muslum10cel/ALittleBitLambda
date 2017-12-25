/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 *
 * @author SirOncel
 */
public class LambdaAndStream {

    public static void main(String[] args) {

        int[] values = new int[]{3, 10, 6, 1, 4, 8, 2, 5, 9, 7};

        IntStream.of(values).forEach(element -> {
            System.out.println(element);
        });

        System.out.println("Count = " + IntStream.of(values).count());

        System.out.println("Min = " + IntStream.of(values).min().getAsInt());

        System.out.println("Max = " + IntStream.of(values).max().getAsInt());

        System.out.println("Average = " + IntStream.of(values).average().getAsDouble());

        System.out.println("Sum with reduce = " + IntStream.of(values).reduce(0, (x, y) -> x + y));

        System.out.println("Sum of squares = " + IntStream.of(values).reduce(0, (x, y) -> x + y * y));

        System.out.println("Product = " + IntStream.of(values).reduce(1, (x, y) -> x * y));

        System.out.println("Even numbers = ");
        IntStream
                .of(values)
                .filter(e -> e % 2 == 0)
                .sorted()
                .forEach(element -> {
                    System.out.println(element);
                });

        System.out.println("Odd numbers = ");
        IntStream
                .of(values)
                .filter(e -> e % 2 != 0)
                .sorted()
                .forEach(element -> {
                    System.out.println(element);
                });

        System.out.println("Sum of 1-9 = " + IntStream.range(1, 10).sum());
        System.out.println("Sum of 1-10 = " + IntStream.rangeClosed(1, 10).sum());

        System.out.println(IntStream.of(values).summaryStatistics());

        IntStream.of(values).filter(predicate -> predicate % 2 == 0).map(mapper -> mapper * 10).forEach(action -> {
            System.out.println(action);
        });

        IntStream.of(values).map(mapper -> mapper * 2).forEach(action -> {
            System.out.println(action);
        });

        /*######################################################################*/
        Integer[] vals = {2, 9, 5, 0, 3, 7, 1, 4, 8, 6};

        System.out.printf("Power of two : %s%n", Arrays.stream(vals).map(Math::sqrt));

        System.out.printf("Original Values %s%n", Arrays.asList(vals));

        System.out.printf("Sorted elements %s%n", Arrays.stream(vals).sorted().collect(Collectors.toList()));

        List<Integer> greaterThan4 = Arrays.stream(vals).filter(f -> f > 4).collect(Collectors.toList());

        System.out.printf("Values greater than 4 : %s%n", greaterThan4);

        System.out.printf("Sorted values greater than 4 : %s%n", greaterThan4.stream().sorted().collect(Collectors.toList()));

        System.out.printf("Original Values %s%n", Arrays.asList(vals));
        
        /*######################################################################*/
        String[] strings = {"Red", "orange", "Yellow", "green", "Blue", "indigo", "Violet"};

        System.out.printf("Original Values %s%n", Arrays.asList(strings));

        System.out.printf("Strings in uppercase : %s%n ", Arrays.stream(strings).map(String::toUpperCase).collect(Collectors.toList()));

        System.out.printf("Strings greater than m sorted : %s%n",
                Arrays.stream(strings).filter(predicate -> predicate.compareToIgnoreCase("m") < 0)
                        .sorted(String.CASE_INSENSITIVE_ORDER)
                        .collect(Collectors.toList()));

        /*######################################################################*/
        Employee[] employees = {
            new Employee("Jason", "Red", 5000, "IT"),
            new Employee("Ashley", "Green", 7600, "IT"),
            new Employee("Matthew", "Indigo", 3587.5, "Sales"),
            new Employee("James", "Indigo", 4700.77, "Marketing"),
            new Employee("Luke", "Indigo", 6200, "IT"),
            new Employee("Jason", "Blue", 3200, "Sales"),
            new Employee("Wendy", "Brown", 4236.4, "Marketing")};

        List<Employee> emps = Arrays.asList(employees);

        System.out.println("All Employees : ");
        emps.stream().forEach(System.out::println);

        Predicate<Employee> fourToSixThousand = emp -> (4000 <= emp.getSalary() && emp.getSalary() <= 6000);

        System.out.printf(
                "%nEmployees earning $4000-$6000 per month sorted by salary:%n");

        emps.stream().filter(fourToSixThousand).sorted(Comparator.comparing(Employee::getSalary)).forEach(System.out::println);

        System.out.printf("%nFirst employee who earns $4000-$6000:%n%s%n",
                emps.stream().filter(fourToSixThousand).findFirst().get());

        Function<Employee, String> byFirstName = Employee::getFirstName;
        Function<Employee, String> byLastName = Employee::getLastName;

        Comparator<Employee> lastThenFirst = Comparator.comparing(byLastName).thenComparing(byFirstName);

        System.out.printf(
                "%nEmployees in ascending order by last name then first:%n");
        emps.stream().sorted(lastThenFirst).forEach(System.out::println);

        System.out.printf("%nEmployees in descending order by last name then first:%n");
        emps.stream().sorted(lastThenFirst.reversed()).forEach(System.out::println);

        System.out.printf("%nUnique employee last names:%n");
        emps.stream().map(Employee::getLastName).distinct().sorted().forEach(System.out::println);

        System.out.printf("%nEmployee names in order by last name then first name:%n");
        emps.stream().sorted(lastThenFirst).map(Employee::getName).forEach(System.out::println);

        System.out.printf("%nEmployees by department:%n");
        Map<String, List<Employee>> groupByDepartment = emps.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        groupByDepartment.forEach(
                (department, employeesInDepartment) -> {
                    System.out.println(department);
                    employeesInDepartment.stream().forEach(emp -> {
                        System.out.printf("   %s%n", emp);
                    });
                });

        System.out.printf("%nCount of Employees by department:%n");
        Map<String, Long> employeeCountByDepartment = emps.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        employeeCountByDepartment.forEach((department, count) -> {
            System.out.printf("%s has %d employee(s)%n", department, count);
        });

        System.out.printf("%nSum of Employees' salaries (via sum method): %.2f%n",
                emps.stream().mapToDouble(Employee::getSalary).sum()
        );

        System.out.printf("Sum of Employees' salaries (via reduce method): %.2f%n",
                emps.stream().mapToDouble(Employee::getSalary).reduce(0, (salary1, salary2) -> salary1 + salary2)
        );

        System.out.printf("Average of Employees' salaries: %.2f%n",
                emps.stream().mapToDouble(Employee::getSalary).average().getAsDouble()
        );

    }
}
