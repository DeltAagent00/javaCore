package Lesson_4;

public class PrintMonitor {
    private volatile StatusEnum status;

    public PrintMonitor(StatusEnum startStatus) {
        this.status = startStatus;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
