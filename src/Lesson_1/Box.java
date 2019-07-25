package Lesson_1;

import Lesson_1.fruits.Fruit;
import Lesson_1.fruits.WeightFruits;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> implements Comparable<Box> {
    final private List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public void addAll(List<T> fruit) {
        fruits.addAll(fruit);
    }

    public void remove(T fruit) {
        fruits.remove(fruit);
    }

    public List<T> getAll() {
        return fruits;
    }

    public void toPour(Box<T> destBox) {
        if (destBox == null || fruits.isEmpty()) {
            return;
        }

        destBox.addAll(fruits);
        clear();
    }

    public void clear() {
        fruits.clear();
    }

    public float getWeight() {
        if (fruits.isEmpty()) {
            return 0f;
        }

        final T fruit = fruits.get(0);
        final WeightFruits weightFruits = fruit.getWeight();

        return fruits.size() * weightFruits.getWeight();
    }

    public boolean compare(Box o) {
        return compareTo(o) == 0;
    }

    @Override
    public int compareTo(Box o) {
        return Float.compare(this.getWeight(), o.getWeight());
    }

    @Override
    public String toString() {
        return "@Box: " +
                "listSize = " + fruits.size() + "; " +
                "weight = " + getWeight();
    }
}
