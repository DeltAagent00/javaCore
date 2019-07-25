package Lesson_5;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private int countReady = 0;
    private int countFinish = 0;
    private boolean canStart = false;
    private int countTunnel = 0;

    private final int COUNT_FOR_TUNNEL;
    private final int COUNT_CARS;
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }

    public Race(int countCars, Stage... stages) {
        this.COUNT_CARS = countCars;
        this.COUNT_FOR_TUNNEL = countCars / 2;
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    synchronized void setReady() {
        countReady++;

        checkStartStage();
    }

    synchronized void checkStartStage() {
        if (isAllReady()) {
            notifyAll();
        }
    }

    synchronized boolean isCanStart() {
        return canStart;
    }

    synchronized void setCanStart() {
        canStart = true;
        notifyAll();
    }

    synchronized void setFinish() {
        countFinish++;

        if (isAllFinish()) {
            notifyAll();
        }
    }

    synchronized boolean isAllReady() {
        return countReady == COUNT_CARS;
    }

    synchronized boolean isAllFinish() {
        return countFinish == COUNT_CARS;
    }

    synchronized void setInTunnel() {
        if (canGoTunnel()) {
            countTunnel++;
        }
    }

    synchronized void outFromTunnel() {
        countTunnel--;
        notifyAll();
    }

    synchronized boolean canGoTunnel() {
        return countTunnel < COUNT_FOR_TUNNEL;
    }

    synchronized boolean isFirstFinish() {
        return countFinish == 0;
    }
}
