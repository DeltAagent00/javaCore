package Lesson_3;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1
        System.out.println("1)");
        final String filename = "src/Lesson_3/test.txt";

        try (FileInputStream in = new FileInputStream(filename)){
            byte[] buff = new byte[4096];
            int x;

            while ((x = in.read(buff)) > 0) {
                System.out.print(new String(buff, 0 , x));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();


        // 2
        System.out.println("2)");
        final ArrayList<InputStream> ali = new ArrayList<>();
        ali.add(new FileInputStream("src/Lesson_3/test.txt"));
        ali.add(new FileInputStream("src/Lesson_3/test2.txt"));
        SequenceInputStream in = new SequenceInputStream(Collections.enumeration(ali));

        final List<byte[]> allData = new ArrayList<>();

        byte[] buff = new byte[4096];
        int length;
        while ((length = in.read(buff)) != -1) {
            byte[] data = new byte[length];
            System.arraycopy(buff, 0, data, 0, length);
            allData.add(data);
        }

        final String fileFoSave = "src/Lesson_3/testAll.txt";
        final File fileSave = new File(fileFoSave);

        if (fileSave.exists()) {
            fileSave.delete();
        }
        fileSave.createNewFile();

        try(FileOutputStream fos = new FileOutputStream(fileFoSave)) {
            for (byte[] data : allData) {
                fos.write(data);
            }
        }

        // 3
        final int EXIT_NUMBER = -1;
        int page = EXIT_NUMBER;
        final String fileName1 = "src/Lesson_3/test.txt";
        final int PAGE_SIZE = 1800;

        try(RandomAccessFile raf = new RandomAccessFile(fileName1, "r")) {
            final long MAX_PAGE = raf.length() / PAGE_SIZE;
            byte[] buffer = new byte[PAGE_SIZE];
            do {
                System.out.println("введите -1 для выхода");
                System.out.println("введите номер старницы [1 - " + (MAX_PAGE + 1) + "]:");
                final Scanner scanner = new Scanner(System.in);

                page = scanner.nextInt();

                if (page > 0 && page <= MAX_PAGE + 1) {
                    final int seekPosition = (page - 1) * PAGE_SIZE;
                    raf.seek(seekPosition);
                    final int readCount = raf.read(buffer);

                    byte[] data = new byte[readCount];

                    if (page == MAX_PAGE + 1) {
                        System.arraycopy(buffer, 0, data, 0, readCount);
                    } else {
                        data = buffer;
                    }

                    System.out.println("page = " + page);
                    System.out.println(new String(data));
                    System.out.println("----------------------");
                }
            } while (page > EXIT_NUMBER);
        }
    }
}
