package ru.clevertec.streamapipuzzles;

import ru.clevertec.streamapipuzzles.util.PersonToJsonConverter;

public class Main {

    public static void main(String[] args) {
        PersonToJsonConverter.fromJsonArray()
                .forEach(System.out::println);
    }

}
