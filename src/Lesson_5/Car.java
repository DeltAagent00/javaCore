package Lesson_5;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private final Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " готов");

        try {
            synchronized (race) {
                race.setReady();

                if (!race.isCanStart()) {
                    race.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            synchronized (race) {
                while (!race.isCanStart()) {
                    race.wait();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            final Stage stage = race.getStages().get(i);
            final boolean isTunnel = stage instanceof Tunnel;
            if (isTunnel) {
                try {
                    synchronized (race) {
                        while(!race.canGoTunnel()) {
                            race.wait();
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

                race.setInTunnel();
            }

            stage.go(this);
            if (isTunnel) {
                race.outFromTunnel();
            }
        }


        try {
            synchronized (race) {
                if (race.isFirstFinish()) {
                    System.out.println(this.name + " WIN");
                }
                race.setFinish();

                if (!race.isAllFinish()) {
                    race.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
