package ru.clevertec.streamapipuzzles.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.clevertec.streamapipuzzles.model.Person;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@UtilityClass
public class PersonToJsonConverter {

    private final Gson GSON = new Gson();

    @SneakyThrows
    public void toJsonArray(List<Person> persons) {
        String jsonArray = GSON.toJson(persons);
        Files.write(getPath(), jsonArray.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @SneakyThrows
    public List<Person> fromJsonArray() {
        Type listType = new TypeToken<List<Person>>() {
        }.getType();
        return GSON.fromJson(Files.readString(getPath()), listType);
    }

    private Path getPath() {
        return Paths.get("src/main/resources/persons.json").toAbsolutePath();
    }

}
