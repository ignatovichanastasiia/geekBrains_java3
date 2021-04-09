package races;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    long t;
    long waitTime;
    long startTime;
    Semaphore smp = new Semaphore(MainCode.CARS_COUNT / 2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    //ПРОХОЖДЕНИЕ ТОННЕЛЯ(ЧАСТЬ ТРАССЫ)
    @Override
    public long go(Car c) {
        try {
            try {
                startTime = System.currentTimeMillis();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                smp.acquire();
                waitTime = System.currentTimeMillis() - startTime;
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(t = time(c));
                smp.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (t + waitTime);
    }

    public long time(Car c) {
        return (length / c.getSpeed() * 1000);
    }
}
