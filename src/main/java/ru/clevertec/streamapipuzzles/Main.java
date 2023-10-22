package ru.clevertec.streamapipuzzles;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.streamapipuzzles.model.Gender;
import ru.clevertec.streamapipuzzles.model.Operator;
import ru.clevertec.streamapipuzzles.model.Person;
import ru.clevertec.streamapipuzzles.model.Phone;
import ru.clevertec.streamapipuzzles.util.FirstAndLastElementCollector;
import ru.clevertec.streamapipuzzles.util.PersonToJsonConverter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Main {

    public static void main(String[] args) {
        List<Person> persons = PersonToJsonConverter.fromJsonArray();

        task1(persons);
        task2(persons);
        task3(persons);
        task4(persons);
        task5(persons);
        task6(persons);
        task7(persons);
        task8(persons);
        task9(persons);
        task10(persons);
        task11(persons);
        task12(persons);
        task13();
        task14();
        task15();
        task16();
        mineTask17(persons);
        mineTask18(persons);
        mineTask19(persons);
    }

    /**
     * 1.  Получите список Person и отфильтруйте только те, у которых age > n и выведите в консоль.
     */
    private static void task1(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getAge() > 35)
                .map(Person::toString)
                .forEach(log::info);
    }

    /**
     * 2.  Получите список Person, отфильтруйте только те, у кого weight > n, преобразуйте в name и выведите в консоль.
     */
    private static void task2(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getWeight() > 94.9)
                .map(Person::getName)
                .forEach(log::info);
    }

    /**
     * 3.  Получите список Person, отфильтруйте только те, у кого кол-во телефонов > n, преобразуйте в номера телефонов
     * и выведите в консоль.
     */
    private static void task3(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getPhones().size() > 2)
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getNumber)
                .forEach(log::info);
    }

    /**
     * 4.  Получите список Person, преобразуйте в name и затем преобразуйте в строку, что бы имена были через запятую.
     */
    private static void task4(List<Person> persons) {
        String names = persons.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "))
                .trim();
        log.info(names);
    }

    /**
     * 5.  Получите список Person и отсортируйте их по возрасту в порядке убывания, если возраст равен, то по именам и
     * выведите в консоль.
     */
    private static void task5(List<Person> persons) {
        persons.stream()
                .sorted(Comparator.comparing(Person::getAge)
                        .reversed()
                        .thenComparing(Person::getName))
                .map(Person::toString)
                .forEach(log::info);
    }

    /**
     * 6.  Получите список Person и сгруппируйте их по полу.
     */
    private static void task6(List<Person> persons) {
        persons.stream()
                .collect(Collectors.groupingBy(Person::getGender))
                .forEach((k, v) -> log.info("{}:\n {}", k, v));
    }

    /**
     * 7.  Получите список Person и проверьте есть ли в этом списке человек, у которого номер телефона N значению.
     */
    private static void task7(List<Person> persons) {
        boolean anyMatch = persons.stream()
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getNumber)
                .anyMatch(number -> number.equals("+375292764735"));
        log.info(String.valueOf(anyMatch));
    }

    /**
     * 8.  Получите список Person, получите n по порядку человека и получите операторов его телефонов исключая
     * дублирование.
     */
    private static void task8(List<Person> persons) {
        persons.stream()
                .skip(2)
                .limit(1)
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getOperator)
                .distinct()
                .map(Operator::toString)
                .forEach(log::info);
    }

    /**
     * 9.  Получите список Person и получите их средний вес.
     */
    private static void task9(List<Person> persons) {
        persons.stream()
                .mapToDouble(Person::getWeight)
                .average()
                .ifPresent(avg -> log.info("Average weight of persons = {}", avg));
    }

    /**
     * 10. Получите список Person и найдите самого младшего по возрасту.
     */
    private static void task10(List<Person> persons) {
        persons.stream()
                .min(Comparator.comparing(Person::getAge))
                .ifPresent(person -> log.info("Youngest by age is {}", person));
    }

    /**
     * 11. Получите список Person, получите их телефоны, сгруппируйте по оператору и результатом группировки должны быть
     * только номера телефонов.
     */
    private static void task11(List<Person> persons) {
        persons.stream()
                .flatMap(person -> person.getPhones().stream())
                .collect(Collectors.groupingBy(Phone::getOperator,
                        Collectors.mapping(Phone::getNumber, Collectors.toList())))
                .forEach((k, v) -> log.info("{}:\n {}", k, v));
    }

    /**
     * 12. Получите список Person, сгруппируйте их по полу и результатом группировки должно быть их кол-во.
     */
    private static void task12(List<Person> persons) {
        persons.stream()
                .collect(Collectors.groupingBy(Person::getGender, Collectors.counting()))
                .forEach((k, v) -> log.info("{}: {}", k, v));
    }

    /**
     * 13. Прочтите содержимое текстового файла и сделайте из него частотный словарик.
     * (слово -> и какое кол-во раз это слово встречается в нём)
     */
    @SneakyThrows
    private static void task13() {
        Stream<String> lines = Files.lines(Paths.get("src/main/resources/text.txt").toAbsolutePath());
        lines.flatMap(s -> Arrays.stream(s.split("\\P{L}+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((k, v) -> log.info("{}: {}", k, v));
        lines.close();
    }

    /**
     * 14. Получите список дат и найдите количество дней между первой и последней датой.
     */
    private static void task14() {
        List<LocalDate> localDates = List.of(LocalDate.of(2023, Month.JANUARY, 15),
                LocalDate.of(2023, Month.MARCH, 10),
                LocalDate.of(2023, Month.FEBRUARY, 20),
                LocalDate.of(2023, Month.SEPTEMBER, 17),
                LocalDate.of(2023, Month.JULY, 5));
        localDates.stream()
                .sorted(Comparator.naturalOrder())
                .collect(new FirstAndLastElementCollector<>())
                .entrySet()
                .stream()
                .map(entry -> ChronoUnit.DAYS.between(entry.getKey(), entry.getValue()))
                .findFirst()
                .ifPresent(daysBetween -> log.info("Number of days between = {}", daysBetween));
    }

    /**
     * 15. Получите список строк, преобразуйте их в числа, и посчитайте среднее значение
     * (не забудьте отфильтровать не валидные строки)
     */
    private static void task15() {
        List<String> strings = List.of("4", "5.6", "3", "1", "2", "fire", "6", "ice", "8", "23.5", "cold2.5");
        strings.stream()
                .filter(s -> s.matches("\\d+(\\.\\d+)?"))
                .mapToDouble(Double::parseDouble)
                .average()
                .ifPresent(avg -> log.info("Average value = {}", avg));
    }

    /**
     * 16. Сгенерируйте миллион рандомных чисел и посчитайте их сумму используя parallelStream с двумя потоками.
     */
    @SneakyThrows
    private static void task16() {
        Integer sum = new ForkJoinPool(2)
                .submit(() -> ThreadLocalRandom.current().ints(1_000_000)
                        .parallel()
                        .unordered()
                        .sum()).get();
        log.info("Sum = {}", sum);
    }

    /**
     * 17. Найти средний вес женщин, чьи имена начинаются на букву A и которые имеют номер телефона оператора MTS.
     */
    private static void mineTask17(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getGender() == Gender.FEMALE)
                .filter(person -> person.getName().startsWith("A"))
                .filter(findPersonThatAnyMatchByOperator(Operator.MTS))
                .mapToDouble(Person::getWeight)
                .average()
                .ifPresent(avg -> log.info("Average weight of women: {}", avg));
    }

    /**
     * 18. Найти самого тяжёлого человека среди тех, кто старше 25 лет и имеет номер телефона оператора LIFE,
     * и вывести его имя и вес.
     */
    private static void mineTask18(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getAge() > 25)
                .filter(findPersonThatAnyMatchByOperator(Operator.LIFE))
                .max(Comparator.comparingDouble(Person::getWeight))
                .ifPresent(person -> log.info("The heaviest person: {}", person));
    }

    private static Predicate<Person> findPersonThatAnyMatchByOperator(Operator operator) {
        return person -> person.getPhones()
                .stream()
                .anyMatch(phone -> phone.getOperator() == operator);
    }

    /**
     * 19. Найти самое длинное и самое короткое имя среди людей, чей вес больше 70 кг, и вывести их в консоль.
     */
    private static void mineTask19(List<Person> persons) {
        persons.stream()
                .filter(person -> person.getWeight() > 70)
                .map(Person::getName)
                .sorted(Comparator.comparingInt(String::length))
                .collect(new FirstAndLastElementCollector<>())
                .forEach((k, v) -> log.info("The shortest name: {}\nThe longest name: {}", k, v));
    }

}
