package ru.clevertec.streamapipuzzles.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.streamapipuzzles.model.Gender;
import ru.clevertec.streamapipuzzles.model.Operator;
import ru.clevertec.streamapipuzzles.model.Person;
import ru.clevertec.streamapipuzzles.model.Phone;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class RandomPersonGenerator {

    private final String[] NAMES = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Iris", "Jack",
            "George", "Alex", "Sam", "Walter", "Anastasia", "Britney", "Sara", "Jessica", "John", "Peter"};
    private final String[] OPERATOR_CODES = {"33", "29", "44", "25"};
    private final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Возраст от 18 до 65
     * Вес от 40.0 до 120.0
     */
    public Person generateRandomPerson() {
        return Person.builder()
                .name(NAMES[SECURE_RANDOM.nextInt(NAMES.length)])
                .age(SECURE_RANDOM.nextInt(48) + 18)
                .weight(Math.round((SECURE_RANDOM.nextDouble() * 80 + 40) * 10) / 10.0)
                .gender(Gender.values()[SECURE_RANDOM.nextInt(Gender.values().length)])
                .phones(generateRandomPhones(SECURE_RANDOM.nextInt(3) + 1))
                .build();
    }

    private List<Phone> generateRandomPhones(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> Phone.builder()
                        .operator(Operator.values()[SECURE_RANDOM.nextInt(Operator.values().length)])
                        .number(generateRandomPhoneNumber())
                        .build())
                .toList();
    }

    private String generateRandomPhoneNumber() {
        return "+375" + OPERATOR_CODES[SECURE_RANDOM.nextInt(OPERATOR_CODES.length)]
                .lines()
                .map(s -> s + IntStream.range(0, 7)
                        .mapToObj(i -> SECURE_RANDOM.nextInt(10))
                        .map(Object::toString)
                        .collect(Collectors.joining()))
                .collect(Collectors.joining());
    }

}
