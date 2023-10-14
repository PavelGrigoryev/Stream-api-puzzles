package ru.clevertec.streamapipuzzles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;
    private Integer age;
    private Double weight;
    private Gender gender;
    private List<Phone> phones;

}
