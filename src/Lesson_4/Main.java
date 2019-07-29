package Lesson_4;

public class Main {
    public static void main(String[] args) {

        final PrintMonitor pm = new PrintMonitor(StatusEnum.One);

        final Thread t1 = new Thread(new PrintRunnable("A", StatusEnum.One, pm));
        final Thread t2 = new Thread(new PrintRunnable("B", StatusEnum.Two, pm));
        final Thread t3 = new Thread(new PrintRunnable("C", StatusEnum.Three, pm));

        t1.start();
        t2.start();
        t3.start();
    }
}
