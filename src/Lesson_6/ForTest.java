package Lesson_6;

import java.util.ArrayList;
import java.util.List;

public class ForTest {
    public int[] cutAfterFour(int[] src) {
        final int BARER = 4;
        final List<Integer> out = new ArrayList<>();
        boolean needAdd = false;

        for (int i : src) {
            if (needAdd) {
                out.add(i);
            }

            if (i == BARER) {
                out.clear();
                needAdd = true;
            }
        }

        if (!needAdd) {
            throw new RuntimeException("В массиве должна быть хоть одна 4");
        }

        final int[] outArray = new int[out.size()];

        for (int i = 0; i < out.size(); ++i) {
            outArray[i] = out.get(i);
        }

        return outArray;
    }

    public boolean checkArrayInOneAndFour(int[] src) {
        boolean findOne = false;
        boolean findFour = false;
        boolean findOtherNumber = false;

        for (int i = 0; i < src.length; ++i) {
            switch (src[i]) {
                case 1:
                    findOne = true;
                    break;
                case 4:
                    findFour = true;
                    break;
                default:
                    findOtherNumber = true;
            }

            if (findOtherNumber || (findOne && findFour) ) {
                break;
            }
        }

        return findOne && findFour && !findOtherNumber;
    }
}
