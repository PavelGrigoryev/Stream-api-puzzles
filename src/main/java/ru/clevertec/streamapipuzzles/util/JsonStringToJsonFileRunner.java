package ru.clevertec.streamapipuzzles.util;

import ru.clevertec.streamapipuzzles.model.Person;

import java.util.List;
import java.util.stream.IntStream;

public class JsonStringToJsonFileRunner {

    public static void main(String[] args) {
        List<Person> persons = IntStream.range(0, 100)
                .mapToObj(i -> RandomPersonGenerator.generateRandomPerson())
                .toList();
        PersonToJsonConverter.toJsonArray(persons);
    }

}
