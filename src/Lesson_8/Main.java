package Lesson_8;

import java.util.Arrays;

public class Main {
    private final static int EMPTY_VALUE = 0;
    private final static int MATRIX_SIZE_WIDTH = 10;
    private final static int MATRIX_SIZE_HEIGHT = 10;

    public static void main(String[] args) {

        final int[][] matrix = new int[MATRIX_SIZE_HEIGHT][MATRIX_SIZE_WIDTH];

        for (int[] ints : matrix) {
            Arrays.fill(ints, EMPTY_VALUE);
        }

        printMatrix(matrix);

        int x = 0,
            y = 0,
            fillValue = 1;

        Vector vector = Vector.RIGHT;

        do {
            matrix[y][x] = fillValue;

            vector = getNextVector(vector, y, x, matrix);

            if (vector != null) {
                switch (vector) {
                    case TOP: y--; break;
                    case RIGHT: x++; break;
                    case BOTTOM: y++; break;
                    case LEFT: x--; break;
                }
            }

            fillValue++;
        } while (vector != null);

        System.out.println();
        printMatrix(matrix);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                String empty;
                int intOfStringLength = String.valueOf(anInt).length();
                switch (intOfStringLength) {
                    default:
                        empty = "";
                        break;
                    case 1:
                        empty = "   ";
                        break;
                    case 2:
                        empty = "  ";
                        break;
                    case 3:
                        empty = " ";
                        break;
                }

                System.out.print(" " + empty + anInt);
            }
            System.out.println();
        }
    }

    private static Vector getNextVector(Vector vector, int y, int x, int[][] matrix) {
        switch (vector) {
            default:
            case RIGHT:
                if (!isLimitMatrix(y, x + 1, matrix) && isEmptyCell(matrix[y][x + 1])) { // right
                    return Vector.RIGHT;
                } else if (!isLimitMatrix(y + 1, x, matrix) && isEmptyCell(matrix[y + 1][x])) { // bottom
                    return Vector.BOTTOM;
                } else if (!isLimitMatrix(y,x - 1, matrix) && isEmptyCell(matrix[y][x - 1])) { // left
                    return Vector.LEFT;
                } else if (!isLimitMatrix(y - 1, x, matrix) && isEmptyCell(matrix[y - 1][x])) { // top
                    return Vector.TOP;
                } else {
                    return null;
                }

            case BOTTOM:
                if (!isLimitMatrix(y + 1, x, matrix) && isEmptyCell(matrix[y + 1][x])) { // bottom
                    return Vector.BOTTOM;
                } else if (!isLimitMatrix(y,x - 1, matrix) && isEmptyCell(matrix[y][x - 1])) { // left
                    return Vector.LEFT;
                } else if (!isLimitMatrix(y - 1, x, matrix) && isEmptyCell(matrix[y - 1][x])) { // top
                    return Vector.TOP;
                } else if (!isLimitMatrix(y, x + 1, matrix) && isEmptyCell(matrix[y][x + 1])) { // right
                    return Vector.RIGHT;
                } else {
                    return null;
                }

            case LEFT:
                if (!isLimitMatrix(y,x - 1, matrix) && isEmptyCell(matrix[y][x - 1])) { // left
                    return Vector.LEFT;
                } else if (!isLimitMatrix(y - 1, x, matrix) && isEmptyCell(matrix[y - 1][x])) { // top
                    return Vector.TOP;
                } else if (!isLimitMatrix(y, x + 1, matrix) && isEmptyCell(matrix[y][x + 1])) { // right
                    return Vector.RIGHT;
                } else if (!isLimitMatrix(y + 1, x, matrix) && isEmptyCell(matrix[y + 1][x])) { // bottom
                    return Vector.BOTTOM;
                } else {
                    return null;
                }
            case TOP:
                if (!isLimitMatrix(y - 1, x, matrix) && isEmptyCell(matrix[y - 1][x])) { // top
                    return Vector.TOP;
                } else if (!isLimitMatrix(y, x + 1, matrix) && isEmptyCell(matrix[y][x + 1])) { // right
                    return Vector.RIGHT;
                } else if (!isLimitMatrix(y + 1, x, matrix) && isEmptyCell(matrix[y + 1][x])) { // bottom
                    return Vector.BOTTOM;
                } else if (!isLimitMatrix(y,x - 1, matrix) && isEmptyCell(matrix[y][x - 1])) { // left
                    return Vector.LEFT;
                } else {
                    return null;
                }

        }
    }

    private static boolean isLimitMatrix(int y, int x, int[][] matrix) {
        return  x < 0 || y < 0 || y >= matrix.length || x >= matrix[0].length;
    }

    private static boolean isEmptyCell(int cell) {
        return cell == EMPTY_VALUE;
    }
}

