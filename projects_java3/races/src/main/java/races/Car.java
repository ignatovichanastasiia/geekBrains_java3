package races;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private long t;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    //МАШИНА(ТРАССА, СКОРОСТЬ)
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.t = 0;
    }

    @Override
    public void run() {
//        try {
//            System.out.println(this.name + " готовится");
//            Thread.sleep(500 + (int) (Math.random() * 800));
//            System.out.println(this.name + " готов");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < race.getStages().size(); i++) {
//            race.getStages().get(i).go(this);
//        }
    }
}
