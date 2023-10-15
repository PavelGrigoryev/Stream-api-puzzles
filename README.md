# Задачки со Stream

## Автор решений и некоторых задач: [Grigoryev Pavel](https://pavelgrigoryev.github.io/GrigoryevPavel/)

### Описание:

1. Решение всех задач с описанием находится в [Main](src/main/java/ru/clevertec/streamapipuzzles/Main.java)
2. 17, 18, 19 задача придуманы мной
3. Реализован свой коллектор который кладёт в мапу первый элемент коллекции как ключ, а последний элемент - как значение
4. Его реализация находится
   [FirstAndLastElementCollector](src/main/java/ru/clevertec/streamapipuzzles/util/FirstAndLastElementCollector.java)
5. Применён в 14ой и в 19ой задаче
6. Так же есть свой [RandomPersonGenerator](src/main/java/ru/clevertec/streamapipuzzles/util/RandomPersonGenerator.java)
7. Через него можно сгенерировать json файл с любым количеством случайных Person запустив
   [JsonStringToJsonFileRunner](src/main/java/ru/clevertec/streamapipuzzles/util/JsonStringToJsonFileRunner.java),
   где X - нужное количество Person

````java
import ru.clevertec.streamapipuzzles.model.Person;

import java.util.List;
import java.util.stream.IntStream;

public class JsonStringToJsonFileRunner {

    public static void main(String[] args) {
        List<Person> persons = IntStream.range(0, X)
                .mapToObj(i -> RandomPersonGenerator.generateRandomPerson())
                .toList();
        PersonToJsonConverter.toJsonArray(persons);
    }

}
````
