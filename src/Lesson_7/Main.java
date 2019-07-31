package Lesson_7;

import Lesson_7.annotations.AfterSuite;
import Lesson_7.annotations.BeforeSuite;
import Lesson_7.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Method beforeMethod = null;
        Method afterMethod = null;
        final Map<Integer, List<Method>> testMap = new TreeMap<>();

        final Class clazz = ForTest.class;

        final Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            boolean find = false;

            final Test test = method.getAnnotation(Test.class);
            if (test != null) {
                List<Method> list = testMap.computeIfAbsent(test.priority(), k -> new ArrayList<>());
                list.add(method);
                find = true;
            }

            if (!find) {
                final BeforeSuite beforeSuite = method.getAnnotation(BeforeSuite.class);
                if (beforeSuite != null) {
                    if (beforeMethod != null) {
                        throw new RuntimeException("before Method more 1");
                    }
                    beforeMethod = method;
                    find = true;
                }
            }

            if (!find) {
                final AfterSuite afterSuite = method.getAnnotation(AfterSuite.class);
                if (afterSuite != null) {
                    if (afterMethod != null) {
                        throw new RuntimeException("after Method more 1");
                    }
                    afterMethod = method;
                }
            }
        }

        try {
            final Object createClass = clazz.getConstructor().newInstance();

            for (Map.Entry<Integer, List<Method>> entry : testMap.entrySet()) {
                for(Method method: entry.getValue()) {
                    if (beforeMethod != null) {
                        beforeMethod.invoke(createClass);
                    }

                    final Parameter[] parameters = method.getParameters();

                    for (Parameter parameter : parameters) {
                        if (parameter.getType() == String.class) {
                            method.invoke(createClass, Main.class.getName());
                        } else if (parameter.getType() == Class.class) {
                            method.invoke(createClass, Main.class);
                        }
                    }

                    if (afterMethod != null) {
                        afterMethod.invoke(createClass);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
