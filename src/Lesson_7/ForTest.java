package Lesson_7;

import Lesson_7.annotations.AfterSuite;
import Lesson_7.annotations.BeforeSuite;
import Lesson_7.annotations.Test;

public class ForTest {
    @Test(priority = 1)
    public static void start(Class clazz) {
        System.out.println("start clazz");
    }

    @Test(priority = 1)
    public static void start(String className) {
        System.out.println("start className");
    }

    @Test(priority = 2)
    public static void start1(String className) {
        System.out.println("start1 className");
    }

    @BeforeSuite
    public static void before() {
        System.out.println("before");
    }

    @AfterSuite
    public static void after() {
        System.out.println("after");
    }
}
