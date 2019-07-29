package Lesson_1;

import Lesson_1.fruits.Apple;
import Lesson_1.fruits.Orange;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String[] arr = new String[] {"1", "2", "3", "4"};
        printArray(arr);
        toSwap(arr, 2, 3);
        printArray(arr);
        toSwap(arr, 2, 1);
        printArray(arr);
        final List<String> list = toTransform(arr);
        System.out.println(list);

        final Box<Apple> boxApple1 = new Box<>();
        final Box<Orange> boxOrange1 = new Box<>();
        final Box<Apple> boxApple2 = new Box<>();
        final Box<Orange> boxOrange2 = new Box<>();

        boxApple1.add(new Apple());
        boxApple1.add(new Apple());
        boxApple1.add(new Apple());

        boxApple2.add(new Apple());

        boxOrange1.add(new Orange());
        boxOrange1.add(new Orange());

        boxOrange2.add(new Orange());

        System.out.println(boxApple1);
        System.out.println(boxApple2);
        System.out.println(boxOrange1);
        System.out.println(boxOrange2);

        System.out.println(boxApple1.compare(boxOrange1));
        System.out.println(boxApple1.compare(boxOrange2));

        boxApple1.toPour(boxApple2);

        System.out.println(boxApple1);
        System.out.println(boxApple2);
    }

    private static <T> void toSwap(T[] array, int srcPos, int destPos) {
        if (array != null && srcPos != destPos &&
                srcPos >= 0 && srcPos < array.length &&
                destPos >= 0 && destPos < array.length) {
            final T srcVal = array[srcPos];
            array[srcPos] = array[destPos];
            array[destPos] = srcVal;
        }
    }

    private static <T> void printArray(T[] array) {
        System.out.print("[ ");
        for (T value : array) {
            System.out.print(value + " ");
        }
        System.out.println("]");
    }

    private static <T> List<T> toTransform(T[] arr) {
        return Arrays.asList(arr);
    }
}
