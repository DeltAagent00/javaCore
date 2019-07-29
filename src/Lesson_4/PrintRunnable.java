package Lesson_4;

public class PrintRunnable implements Runnable {
    final private int COUNT_PRINT = 5;

    final private String msg;
    final private PrintMonitor pm;
    final private StatusEnum status;

    public PrintRunnable(String msg, StatusEnum status, PrintMonitor pm) {
        this.msg = msg;
        this.pm = pm;
        this.status = status;
    }

    @Override
    public void run() {
        synchronized (pm) {
            for (int i = 0; i < COUNT_PRINT; ++i) {
                while (pm.getStatus() != status) {
                    try {
                        pm.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(msg);
                pm.setStatus(StatusEnum.getNextStatus(status));
                pm.notifyAll();
            }
        }
    }
}
