package test;

import Lesson_6.ForTest;
import org.junit.*;

public class Test1 {

    ForTest classFoTest;

    @Before
    public void init() {
        classFoTest = new ForTest();
    }

    @Test(expected = RuntimeException.class)
    public void test1() {
        final int[] src = new int[] {1, 2, 3};
        classFoTest.cutAfterFour(src);
    }

    @Test
    public void test2() {
        final int[] src = new int[] {1, 2, 4, 4, 2, 3, 4, 1, 7};
        final int[] result = new int[] {1, 7};

        Assert.assertArrayEquals(result, classFoTest.cutAfterFour(src));
    }

    @Test
    public void test3() {
        final int[] src = new int[] {4, 3, 2, 1};
        final int[] result = new int[] {3, 2, 1};

        Assert.assertArrayEquals(result, classFoTest.cutAfterFour(src));
    }

    @Test
    public void test4() {
        final int[] src = new int[] {1, 2, 3, 4};
        final int[] result = new int[] {};

        Assert.assertArrayEquals(result, classFoTest.cutAfterFour(src));
    }

    @Test
    public void test5() {
        final int[] src = new int[] {1, 2, 3, 4};

        Assert.assertFalse(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test6() {
        final int[] src = new int[] {1, 1, 4, 4};

        Assert.assertTrue(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test7() {
        final int[] src = new int[] {1, 1, 1, 4};

        Assert.assertTrue(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test8() {
        final int[] src = new int[] {1, 4, 4, 4};

        Assert.assertTrue(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test9() {
        final int[] src = new int[] {1, 1, 1, 1};

        Assert.assertFalse(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test10() {
        final int[] src = new int[] {4, 4, 4, 4};

        Assert.assertFalse(classFoTest.checkArrayInOneAndFour(src));
    }

    @Test
    public void test11() {
        final int[] src = new int[] {};

        Assert.assertFalse(classFoTest.checkArrayInOneAndFour(src));
    }

    @After
    public void shutdown() {
        classFoTest = null;
    }
}
