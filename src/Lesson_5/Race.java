package Lesson_5;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private int countReady = 0;
    private final int COUNT_CARS;
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(int countCars, Stage... stages) {
        this.COUNT_CARS = countCars;
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    synchronized void setReady() {
        countReady++;

        if (countReady == COUNT_CARS) {
            notifyAll();
        }
    }
}
