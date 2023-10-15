package ru.clevertec.streamapipuzzles.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FirstAndLastElementCollector<E> implements Collector<E, List<E>, Map<E, E>> {

    /**
     * Метод supplier возвращает функцию, которая создаёт новый пустой список для хранения элементов из потока.
     */
    @Override
    public Supplier<List<E>> supplier() {
        return ArrayList::new;
    }

    /**
     * Метод accumulator возвращает функцию, которая добавляет элемент из потока в список.
     */
    @Override
    public BiConsumer<List<E>, E> accumulator() {
        return List::add;
    }

    /**
     * Метод combiner возвращает функцию, которая объединяет два списка в один, если поток разбит на части для
     * параллельной обработки.
     */
    @Override
    public BinaryOperator<List<E>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * Метод finisher возвращает функцию, которая преобразует список в мапу, выбирая первый и последний элементы из списка.
     */
    @Override
    public Function<List<E>, Map<E, E>> finisher() {
        return list -> {
            E first = list.get(0);
            E last = list.get(list.size() - 1);
            return Map.of(first, last);
        };
    }

    /**
     * Метод characteristics возвращает набор характеристик, которые описывают поведение коллектора.
     * Коллектор не имеет особых свойств.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.noneOf(Characteristics.class);
    }

}
