package races;

public class Road extends Stage {
    long t;
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    //ПРОХОЖДЕНИЕ ДОРОГИ(ЧАСТЬ ТРАССЫ)
    @Override
    public long go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(t = time(c));
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    public long time(Car c){
        return (length / c.getSpeed() * 1000);
    }
}
